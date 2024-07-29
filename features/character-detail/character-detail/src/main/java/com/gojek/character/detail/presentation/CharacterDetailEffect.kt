package com.gojek.character.detail.presentation

import com.gojek.base.presentation.MviEffect

sealed class CharacterDetailEffect : MviEffect {
    data object BackPressedEffect : CharacterDetailEffect()
    data class ShowErrorNotificationEffect(val cause: Throwable) : CharacterDetailEffect()
}