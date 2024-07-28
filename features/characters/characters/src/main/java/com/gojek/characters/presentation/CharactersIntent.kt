package com.gojek.characters.presentation

import com.gojek.base.presentation.MviIntent

sealed class CharactersIntent : MviIntent {
    data object SeeAllCharactersIntent : CharactersIntent()
    data class SeeDetailCharacterIntent(val id: Int) : CharactersIntent()
}