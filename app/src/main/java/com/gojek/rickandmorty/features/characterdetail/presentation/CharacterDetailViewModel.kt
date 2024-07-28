package com.gojek.rickandmorty.features.characterdetail.presentation

import com.gojek.rickandmorty.base.presentation.AbstractViewModel
import com.gojek.rickandmorty.base.presentation.MviAction
import com.gojek.rickandmorty.base.presentation.MviEffect
import com.gojek.rickandmorty.base.presentation.MviIntent
import com.gojek.rickandmorty.base.presentation.MviResult
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailAction.BackPressedAction
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailIntent.BackPressedIntent
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailResult.LoadCharacterDetailResult
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.ObservableTransformer
import javax.inject.Inject
import javax.inject.Named

class CharacterDetailViewModel @Inject constructor(
    @Named("characterDetailActionProcessor")
    actionProcessor: ObservableTransformer<MviAction, MviResult>
) : AbstractViewModel<CharacterDetailViewState>(
    actionProcessor = actionProcessor,
    initialState = CharacterDetailViewState.INITIAL
) {
    override fun mapIntentToActions(intent: MviIntent): Observable<MviAction> {
        return when (intent) {
            is BackPressedIntent ->
                just(BackPressedAction)

            is CharacterDetailIntent.LoadCharacterDetailIntent ->
                just(CharacterDetailAction.LoadCharacterDetailAction(intent.characterId))

            else -> Observable.empty()
        }
    }

    override fun reduceState(
        previous: CharacterDetailViewState,
        result: MviResult
    ): CharacterDetailViewState {
        return when (result) {
            is LoadCharacterDetailResult.Success ->
                previous.copy(
                    character = result.character,
                    isLoading = false
                )

            else -> previous
        }
    }

    override fun inferSideEffectsFromResult(result: MviResult): List<MviEffect> {
        return when (result) {
            is CharacterDetailResult.BackPressedResult ->
                listOf(CharacterDetailEffect.BackPressedEffect)

            is LoadCharacterDetailResult.Failure ->
                listOf(CharacterDetailEffect.ShowErrorNotificationEffect(result.cause))

            else -> emptyList()
        }
    }
}