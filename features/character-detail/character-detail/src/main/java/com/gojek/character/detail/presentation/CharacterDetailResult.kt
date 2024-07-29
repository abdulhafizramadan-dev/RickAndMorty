package com.gojek.character.detail.presentation

import com.gojek.base.presentation.MviResult
import com.gojek.characters.shared.domain.model.Character

sealed class CharacterDetailResult : MviResult {
    data object BackPressedResult : CharacterDetailResult()
    sealed class LoadCharacterDetailResult() : CharacterDetailResult() {
        data class Success(val character: Character) : LoadCharacterDetailResult()
        data class Failure(val cause: Throwable) : LoadCharacterDetailResult()
    }
}