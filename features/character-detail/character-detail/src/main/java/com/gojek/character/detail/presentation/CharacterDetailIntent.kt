package com.gojek.character.detail.presentation

import com.gojek.base.presentation.MviIntent

sealed class CharacterDetailIntent : MviIntent {
    data object BackPressedIntent : CharacterDetailIntent()
    data class LoadCharacterDetailIntent(val characterId: Int) : CharacterDetailIntent()
}