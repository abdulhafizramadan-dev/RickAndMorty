package com.gojek.rickandmorty.features.characterdetail.presentation

import com.gojek.rickandmorty.base.presentation.MviAction
import com.gojek.rickandmorty.base.presentation.MviResult
import com.gojek.rickandmorty.features.characterdetail.domain.usecase.GetCharacterDetailUseCase
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailAction.BackPressedAction
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailAction.LoadCharacterDetailAction
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailResult.BackPressedResult
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailResult.LoadCharacterDetailResult
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class CharacterDetailActionProcessor(
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
            }
        }
}