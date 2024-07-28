package com.gojek.rickandmorty.features.characterdetail.presentation

import com.gojek.rickandmorty.base.presentation.MviState
import com.gojek.rickandmorty.features.characters.domain.model.Character

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