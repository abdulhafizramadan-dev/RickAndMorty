package com.gojek.rickandmorty.features.characterdetail.presentation

import com.gojek.rickandmorty.base.presentation.MviAction

sealed class CharacterDetailAction : MviAction {
    data object BackPressedAction : CharacterDetailAction()
    data class LoadCharacterDetailAction(val characterId: Int) : CharacterDetailAction()
}