package com.gojek.characters.presentation

import com.gojek.base.presentation.MviResult
import com.gojek.characters.shared.domain.model.Character

sealed class CharactersResult : MviResult {
    sealed class LoadCharactersResult : CharactersResult() {
        data class Success(val characters: List<Character>) : LoadCharactersResult()
        data class Failure(val cause: Throwable) : LoadCharactersResult()
    }

    sealed class ShowDetailedCharacterResult : CharactersResult() {
        data class Success(val characterId: Int) : ShowDetailedCharacterResult()
    }
}