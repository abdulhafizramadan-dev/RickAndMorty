package com.gojek.characters.presentation

import android.content.Intent
import com.gojek.base.presentation.MviEffect

sealed class CharactersEffect : MviEffect {
    data class NavigateEffect(val intent: Intent) : CharactersEffect()
    data class ShowErrorNotificationEffect(val cause: Throwable) : CharactersEffect()
}