package com.gojek.rickandmorty.di

import android.content.Context
import com.gojek.rickandmorty.MainActivity
import com.gojek.rickandmorty.features.characterdetail.ui.CharacterDetailActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class, CharactersModule::class, CharacterDetailModule::class])
interface RickAndMortyComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): RickAndMortyComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: CharacterDetailActivity)
}