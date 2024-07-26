package com.gojek.rickandmorty.features.characters.presentation

import com.gojek.rickandmorty.base.presentation.MviAction

sealed class CharactersAction : MviAction {
    object LoadCharactersAction : CharactersAction()
    data class ShowDetailedCharacterAction(val id: Int) : CharactersAction()
}