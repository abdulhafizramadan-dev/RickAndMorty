package com.gojek.rickandmorty

import android.app.Application
import com.gojek.rickandmorty.di.DaggerRickAndMortyComponent
import com.gojek.rickandmorty.di.RickAndMortyComponent

class RickAndMortyApplication : Application() {
    private val rickAndMortyComponent: RickAndMortyComponent by lazy {
        DaggerRickAndMortyComponent.create()
    }
}