package com.gojek.rickandmorty.features.characterdetail.presentation

import com.gojek.rickandmorty.base.presentation.MviEffect

sealed class CharacterDetailEffect : MviEffect {
    data object BackPressedEffect : CharacterDetailEffect()
    data class ShowErrorNotificationEffect(val cause: Throwable) : CharacterDetailEffect()
}