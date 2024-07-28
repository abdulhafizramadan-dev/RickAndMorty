package com.gojek.characters.presentation

import com.gojek.base.presentation.MviState
import com.gojek.characters.shared.domain.model.Character

data class CharactersViewState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
) : MviState {
    companion object {
        val INITIAL = CharactersViewState(
            isLoading = true
        )
    }
}