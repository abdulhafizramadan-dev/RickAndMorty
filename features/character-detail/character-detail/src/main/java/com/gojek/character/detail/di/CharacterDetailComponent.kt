package com.gojek.character.detail.di

import com.gojek.character.detail.ui.CharacterDetailActivity
import dagger.Subcomponent

@Subcomponent
interface CharacterDetailComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): CharacterDetailComponent
    }

    fun inject(activity: CharacterDetailActivity)
}