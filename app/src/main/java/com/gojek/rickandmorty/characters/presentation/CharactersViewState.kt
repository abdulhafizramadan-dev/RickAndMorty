package com.gojek.rickandmorty.characters.presentation

import com.gojek.rickandmorty.base.presentation.MviState
import com.gojek.rickandmorty.characters.domain.model.Character

data class CharactersViewState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
) : MviState {
    companion object {
        val INITIAL = CharactersViewState()
    }
}