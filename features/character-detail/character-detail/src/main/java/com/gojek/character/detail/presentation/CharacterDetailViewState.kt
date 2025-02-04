package com.gojek.character.detail.presentation

import com.gojek.base.presentation.MviState
import com.gojek.characters.shared.domain.model.Character

data class CharacterDetailViewState(
    val character: Character,
    val isLoading: Boolean
) : MviState {
    companion object {
        val INITIAL = CharacterDetailViewState(
            character = Character(),
            isLoading = true
        )
    }
}