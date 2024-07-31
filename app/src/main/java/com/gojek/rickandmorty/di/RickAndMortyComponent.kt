package com.gojek.rickandmorty.di

import android.content.Context
import com.gojek.character.detail.di.CharacterDetailComponent
import com.gojek.character.detail.di.CharacterDetailModule
import com.gojek.characters.di.CharactersComponent
import com.gojek.characters.di.CharactersModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class, CharactersModule::class, CharacterDetailModule::class])
interface RickAndMortyComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): RickAndMortyComponent
    }

    fun charactersComponent(): CharactersComponent.Factory
    fun characterDetailComponent(): CharacterDetailComponent.Factory
}