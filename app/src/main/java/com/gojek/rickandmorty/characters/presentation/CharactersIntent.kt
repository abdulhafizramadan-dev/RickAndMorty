package com.gojek.rickandmorty.characters.presentation

import com.gojek.rickandmorty.base.presentation.MviIntent

sealed class CharactersIntent : MviIntent {
    data object SeeAllCharactersIntent : CharactersIntent()
    data class SeeDetailCharacterIntent(val id: Int) : CharactersIntent()
}