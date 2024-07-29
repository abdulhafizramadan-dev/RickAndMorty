package com.gojek.characters.di

interface CharactersComponentProvider {
    fun provideCharactersComponent(): CharactersComponent
}