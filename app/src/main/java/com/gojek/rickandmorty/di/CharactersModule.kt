package com.gojek.rickandmorty.di

import com.gojek.rickandmorty.base.presentation.MviAction
import com.gojek.rickandmorty.base.presentation.MviResult
import com.gojek.rickandmorty.features.characters.data.CharacterRepositoryImpl
import com.gojek.rickandmorty.features.characters.domain.repository.CharacterRepository
import com.gojek.rickandmorty.features.characters.domain.usecase.DefaultGetCharactersUseCase
import com.gojek.rickandmorty.features.characters.domain.usecase.GetCharactersUseCase
import com.gojek.rickandmorty.features.characters.presentation.CharactersActionProcessor
import dagger.Binds
import dagger.Module
import io.reactivex.ObservableTransformer
import javax.inject.Named

@Module
abstract class CharactersModule {
    @Binds
    abstract fun bindsCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository

    @Binds
    abstract fun bindsGetCharactersUseCase(
        defaultGetCharactersUseCase: DefaultGetCharactersUseCase
    ): GetCharactersUseCase

    @Binds
    @Named("charactersActionProcessor")
    abstract fun bindsCharactersObservableTransformer(
        charactersActionProcessor: CharactersActionProcessor
    ): ObservableTransformer<MviAction, MviResult>
}