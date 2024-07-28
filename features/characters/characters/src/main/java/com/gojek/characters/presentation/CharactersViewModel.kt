package com.gojek.characters.presentation

import android.content.Intent
import com.gojek.base.presentation.AbstractViewModel
import com.gojek.base.presentation.MviAction
import com.gojek.base.presentation.MviEffect
import com.gojek.base.presentation.MviIntent
import com.gojek.base.presentation.MviResult
import com.gojek.base.utils.ActionConstant
import com.gojek.characters.presentation.CharactersAction.ShowDetailedCharacterAction
import com.gojek.characters.presentation.CharactersEffect.ShowErrorNotificationEffect
import com.gojek.characters.presentation.CharactersIntent.SeeAllCharactersIntent
import com.gojek.characters.presentation.CharactersResult.LoadCharactersResult
import com.gojek.characters.presentation.CharactersResult.ShowDetailedCharacterResult
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.ObservableTransformer
import javax.inject.Inject
import javax.inject.Named

class CharactersViewModel @Inject constructor(
    @Named("charactersActionProcessor")
    actionProcessor: ObservableTransformer<MviAction, MviResult>
) : AbstractViewModel<CharactersViewState>(
    actionProcessor = actionProcessor,
    initialState = CharactersViewState.INITIAL
) {
    override fun mapIntentToActions(intent: MviIntent): Observable<MviAction> {
        return when (intent) {
            is SeeAllCharactersIntent -> just(CharactersAction.LoadCharactersAction)
            is CharactersIntent.SeeDetailCharacterIntent -> just(
                ShowDetailedCharacterAction(
                    intent.id
                )
            )

            else -> Observable.empty()
        }
    }

    override fun reduceState(
        previous: CharactersViewState,
        result: MviResult
    ): CharactersViewState {
        return when (result) {
            is LoadCharactersResult.Success -> {
                previous.copy(
                    characters = result.characters,
                    isLoading = false
                )
            }

            else -> previous
        }
    }

    override fun inferSideEffectsFromResult(result: MviResult): List<MviEffect> {
        return when (result) {
            is ShowDetailedCharacterResult.Success ->
                listOf(CharactersEffect.NavigateEffect(createDetailIntent(result.characterId)))

            is LoadCharactersResult.Failure ->
                listOf(ShowErrorNotificationEffect(result.cause))

            else -> emptyList()
        }
    }

    private fun createDetailIntent(characterId: Int): Intent {
        val intent = Intent("com.gojek.rickandmorty.action.SHOW_DETAIL")
        intent.putExtra(ActionConstant.CHARACTER_ID, characterId)
        return intent
    }
}