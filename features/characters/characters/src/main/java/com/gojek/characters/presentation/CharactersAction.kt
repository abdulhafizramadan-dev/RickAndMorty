package com.gojek.characters.presentation

import com.gojek.base.presentation.MviAction

sealed class CharactersAction : MviAction {
    data object LoadCharactersAction : CharactersAction()
    data class ShowDetailedCharacterAction(val id: Int) : CharactersAction()
}