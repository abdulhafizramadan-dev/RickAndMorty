package com.gojek.rickandmorty.di

import com.gojek.rickandmorty.base.presentation.MviAction
import com.gojek.rickandmorty.base.presentation.MviResult
import com.gojek.rickandmorty.features.characterdetail.domain.usecase.DefaultGetCharacterDetailUseCase
import com.gojek.rickandmorty.features.characterdetail.domain.usecase.GetCharacterDetailUseCase
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailActionProcessor
import dagger.Binds
import dagger.Module
import io.reactivex.ObservableTransformer
import javax.inject.Named

@Module
abstract class CharacterDetailModule {
    @Binds
    abstract fun bindsGetCharacterDetailUseCase(
        defaultGetCharacterDetailUseCase: DefaultGetCharacterDetailUseCase
    ): GetCharacterDetailUseCase

    @Binds
    @Named("characterDetailActionProcessor")
    abstract fun bindsCharacterDetailObservableTransformer(
        characterDetailActionProcessor: CharacterDetailActionProcessor
    ): ObservableTransformer<MviAction, MviResult>
}