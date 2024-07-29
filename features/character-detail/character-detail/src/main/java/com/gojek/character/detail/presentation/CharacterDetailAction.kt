package com.gojek.character.detail.presentation

import com.gojek.base.presentation.MviAction

sealed class CharacterDetailAction : MviAction {
    data object BackPressedAction : CharacterDetailAction()
    data class LoadCharacterDetailAction(val characterId: Int) : CharacterDetailAction()
}