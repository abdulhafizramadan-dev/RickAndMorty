package com.gojek.character.detail.presentation

import com.gojek.base.presentation.MviAction
import com.gojek.base.presentation.MviResult
import com.gojek.character.detail.usecase.GetCharacterDetailUseCase
import com.gojek.character.detail.presentation.CharacterDetailAction.BackPressedAction
import com.gojek.character.detail.presentation.CharacterDetailAction.LoadCharacterDetailAction
import com.gojek.character.detail.presentation.CharacterDetailResult.BackPressedResult
import com.gojek.character.detail.presentation.CharacterDetailResult.LoadCharacterDetailResult
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CharacterDetailActionProcessor @Inject constructor(
    private val characterDetailUseCase: GetCharacterDetailUseCase
) : ObservableTransformer<MviAction, MviResult> {
    override fun apply(upstream: Observable<MviAction>): ObservableSource<MviResult> {
        return upstream.share().let { sharedUpstream ->
            Observable.mergeArray(
                sharedUpstream.ofType(BackPressedAction::class.java)
                    .compose(backPressedSubProcessor),
                sharedUpstream.ofType(LoadCharacterDetailAction::class.java)
                    .compose(loadCharacterDetailSubProcessor)
            )
        }
    }

    private val backPressedSubProcessor =
        ObservableTransformer<BackPressedAction, BackPressedResult> { upstream ->
            upstream.map { BackPressedResult }
        }

    private val loadCharacterDetailSubProcessor =
        ObservableTransformer<LoadCharacterDetailAction, LoadCharacterDetailResult> { upstream ->
            upstream.switchMap { action ->
                val param = GetCharacterDetailUseCase.Param(id = action.characterId)
                characterDetailUseCase.execute(param)
                    .map { character -> LoadCharacterDetailResult.Success(character) as LoadCharacterDetailResult }
                    .onErrorReturn { cause -> LoadCharacterDetailResult.Failure(cause) }
                    .flatMapObservable { Observable.just(it) }
                    .delay(1, TimeUnit.SECONDS)
            }
        }
}