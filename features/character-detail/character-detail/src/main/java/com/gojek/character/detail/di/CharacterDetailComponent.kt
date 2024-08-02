package com.gojek.character.detail.di

import com.gojek.character.detail.ui.CharacterDetailActivity
import com.gojek.characters.di.CharactersComponent
import com.gojek.characters.di.CharactersComponentProvider
import dagger.Component

@Component(dependencies = [CharactersComponent::class], modules = [CharacterDetailModule::class])
@CharacterDetailScope
interface CharacterDetailComponent {
    fun inject(activity: CharacterDetailActivity)
}

object CharacterDetailComponentProvider {
    private var component: CharacterDetailComponent? = null

    @Synchronized
    fun get(): CharacterDetailComponent {
        if (component == null) {
            throw IllegalStateException("CharacterDetailComponent is not initialized")
        }
        return component!!
    }

    fun init(): CharacterDetailComponent {
        if (component == null) {
            component = DaggerCharacterDetailComponent.builder()
                .charactersComponent(CharactersComponentProvider.get())
                .build()
        }
        return component!!
    }
}
