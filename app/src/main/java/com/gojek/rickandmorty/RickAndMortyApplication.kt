package com.gojek.rickandmorty

import android.app.Application
import com.gojek.rickandmorty.di.DaggerRickAndMortyComponent
import com.gojek.rickandmorty.di.RickAndMortyComponent

class RickAndMortyApplication : Application() {
    val rickAndMortyComponent: RickAndMortyComponent by lazy {
        DaggerRickAndMortyComponent.factory()
            .create(this)
    }
}