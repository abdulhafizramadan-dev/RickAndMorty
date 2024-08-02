package com.gojek.characters.di

import android.content.Context
import com.gojek.characters.shared.domain.repository.CharacterRepository
import com.gojek.characters.ui.CharactersActivity
import com.gojek.core.di.CoreComponent
import com.gojek.core.di.CoreComponentProvider
import dagger.Component

@Component(dependencies = [CoreComponent::class], modules = [CharactersModule::class])
@CharactersScope
interface CharactersComponent {
    fun inject(activity: CharactersActivity)

    fun charactersRepository(): CharacterRepository
}

object CharactersComponentProvider {
    private var component: CharactersComponent? = null

    @Synchronized
    fun get(): CharactersComponent {
        if (component == null) {
            throw IllegalStateException("CharactersComponent is not initialized")
        }
        return component!!
    }

    fun init(context: Context): CharactersComponent {
        if (component == null) {
            component = DaggerCharactersComponent.builder()
                .coreComponent(CoreComponentProvider.init(context))
                .build()
        }
        return component!!
    }
}
