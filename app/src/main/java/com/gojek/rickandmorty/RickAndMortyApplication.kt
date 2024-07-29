package com.gojek.rickandmorty

import android.app.Application
import com.gojek.character.detail.di.CharacterDetailComponent
import com.gojek.character.detail.di.CharacterDetailComponentProvider
import com.gojek.characters.di.CharactersComponent
import com.gojek.characters.di.CharactersComponentProvider
import com.gojek.rickandmorty.di.DaggerRickAndMortyComponent
import com.gojek.rickandmorty.di.RickAndMortyComponent

class RickAndMortyApplication : Application(), CharactersComponentProvider, CharacterDetailComponentProvider {
    private val rickAndMortyComponent: RickAndMortyComponent by lazy {
        DaggerRickAndMortyComponent.factory()
            .create(this)
    }

    override fun provideCharactersComponent(): CharactersComponent {
        return rickAndMortyComponent.charactersComponent().create()
    }

    override fun provideCharacterDetailComponent(): CharacterDetailComponent {
        return rickAndMortyComponent.characterDetailComponent().create()
    }
}