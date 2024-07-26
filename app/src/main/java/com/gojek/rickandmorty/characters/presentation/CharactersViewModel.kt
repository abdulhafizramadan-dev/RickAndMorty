package com.gojek.rickandmorty.characters.presentation

import android.content.Intent
import com.gojek.rickandmorty.base.presentation.AbstractViewModel
import com.gojek.rickandmorty.base.presentation.MviAction
import com.gojek.rickandmorty.base.presentation.MviEffect
import com.gojek.rickandmorty.base.presentation.MviIntent
import com.gojek.rickandmorty.base.presentation.MviResult
import com.gojek.rickandmorty.characters.domain.model.Character
import com.gojek.rickandmorty.characters.presentation.CharactersAction.LoadCharactersAction
import com.gojek.rickandmorty.characters.presentation.CharactersAction.ShowDetailedCharacterAction
import com.gojek.rickandmorty.characters.presentation.CharactersEffect.NavigateEffect
import com.gojek.rickandmorty.characters.presentation.CharactersEffect.ShowErrorNotificationEffect
import com.gojek.rickandmorty.characters.presentation.CharactersIntent.SeeAllCharactersIntent
import com.gojek.rickandmorty.characters.presentation.CharactersIntent.SeeDetailCharacterIntent
import com.gojek.rickandmorty.characters.presentation.CharactersResult.LoadCharactersResult
import com.gojek.rickandmorty.characters.presentation.CharactersResult.ShowDetailedCharacterResult
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.ObservableTransformer

class CharactersViewModel(
    actionProcessor: ObservableTransformer<MviAction, MviResult>
) : AbstractViewModel<CharactersViewState>(
    actionProcessor = actionProcessor,
    initialState = CharactersViewState.INITIAL
) {
    override fun mapIntentToActions(intent: MviIntent): Observable<MviAction> {
        return when (intent) {
            is SeeAllCharactersIntent -> just(LoadCharactersAction)
            is SeeDetailCharacterIntent -> just(ShowDetailedCharacterAction(intent.id))
            else -> Observable.empty()
        }
    }

    override fun reduceState(
        previous: CharactersViewState,
        result: MviResult
    ): CharactersViewState {
        return when (result) {
            is LoadCharactersResult.Success -> {
                previous.copy(characters = result.characters)
            }

            else -> previous
        }
    }

    override fun inferSideEffectsFromResult(result: MviResult): List<MviEffect> {
        return when (result) {
            is ShowDetailedCharacterResult.Success ->
                listOf(NavigateEffect(createDetailIntent(result.character)))

            is LoadCharactersResult.Failure ->
                listOf(ShowErrorNotificationEffect(result.cause))

            is ShowDetailedCharacterResult.Failure ->
                listOf(ShowErrorNotificationEffect(result.cause))

            else -> emptyList()
        }
    }

    private fun createDetailIntent(character: Character): Intent {
        val intent = Intent("com.gojek.rickandmorty.action.SHOW_DETAIL")
        intent.putExtra("character_id", character.id)
        return intent
    }
}