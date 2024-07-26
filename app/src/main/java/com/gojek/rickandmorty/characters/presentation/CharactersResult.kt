package com.gojek.rickandmorty.characters.presentation

import com.gojek.rickandmorty.base.presentation.MviResult
import com.gojek.rickandmorty.characters.domain.model.Character

sealed class CharactersResult : MviResult {
    sealed class LoadCharactersResult : CharactersResult() {
        data class Success(val characters: List<Character>) : LoadCharactersResult()
        data class Failure(val cause: Throwable) : LoadCharactersResult()
    }

    sealed class ShowDetailedCharacterResult : CharactersResult() {
        data class Success(val character: Character) : ShowDetailedCharacterResult()
        data class Failure(val cause: Throwable) : ShowDetailedCharacterResult()
    }
}