package com.gojek.rickandmorty.features.characterdetail.presentation

import com.gojek.rickandmorty.base.presentation.MviResult
import com.gojek.rickandmorty.features.characters.domain.model.Character

sealed class CharacterDetailResult : MviResult {
    data object BackPressedResult : CharacterDetailResult()
    sealed class LoadCharacterDetailResult() : CharacterDetailResult() {
        data class Success(val character: Character) : LoadCharacterDetailResult()
        data class Failure(val cause: Throwable) : LoadCharacterDetailResult()
    }
}