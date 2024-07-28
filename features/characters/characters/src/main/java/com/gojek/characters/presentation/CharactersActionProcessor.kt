package com.gojek.characters.presentation

import com.gojek.base.presentation.MviAction
import com.gojek.base.presentation.MviResult
import com.gojek.characters.presentation.CharactersAction.LoadCharactersAction
import com.gojek.characters.presentation.CharactersAction.ShowDetailedCharacterAction
import com.gojek.characters.presentation.CharactersResult.LoadCharactersResult
import com.gojek.characters.presentation.CharactersResult.ShowDetailedCharacterResult
import com.gojek.characters.usecase.GetCharactersUseCase
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CharactersActionProcessor @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ObservableTransformer<MviAction, MviResult> {

    override fun apply(upstream: Observable<MviAction>): ObservableSource<MviResult> {
        return upstream.share().let { sharedUpstream ->
            Observable.mergeArray(
                sharedUpstream.ofType(LoadCharactersAction::class.java)
                    .compose(loadCharactersSubProcessor),
                sharedUpstream.ofType(ShowDetailedCharacterAction::class.java)
                    .compose(showDetailedCharacterSubProcessor)
            )
        }
    }

    private val loadCharactersSubProcessor =
        ObservableTransformer<LoadCharactersAction, LoadCharactersResult> { upstream ->
            upstream.switchMap {
                getCharactersUseCase.execute(Unit)
                    .map { characters -> LoadCharactersResult.Success(characters) as LoadCharactersResult }
                    .onErrorReturn { cause -> LoadCharactersResult.Failure(cause) }
                    .flatMapObservable { Observable.just(it) }
                    .delay(1, TimeUnit.SECONDS)
            }
        }

    private val showDetailedCharacterSubProcessor =
        ObservableTransformer<ShowDetailedCharacterAction, ShowDetailedCharacterResult> { upstream ->
            upstream.map { action -> ShowDetailedCharacterResult.Success(action.id) }
        }
}