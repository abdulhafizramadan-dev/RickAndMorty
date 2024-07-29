package com.gojek.characters.di

import com.gojek.characters.ui.CharactersActivity
import dagger.Subcomponent

@Subcomponent
interface CharactersComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): CharactersComponent
    }

    fun inject(activity: CharactersActivity)
}