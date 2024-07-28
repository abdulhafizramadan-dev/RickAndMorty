package com.gojek.rickandmorty.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gojek.base.presentation.MviAction
import com.gojek.base.presentation.MviResult
import com.gojek.rickandmorty.features.characterdetail.domain.usecase.DefaultGetCharacterDetailUseCase
import com.gojek.rickandmorty.features.characterdetail.domain.usecase.GetCharacterDetailUseCase
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailActionProcessor
import com.gojek.rickandmorty.features.characterdetail.presentation.CharacterDetailViewModel
import com.gojek.rickandmorty.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.ObservableTransformer
import javax.inject.Named
import javax.inject.Provider

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

    companion object {
        @Provides
        @Named("characterDetailViewModelFactory")
        fun providesViewModelFactory(
            characterDetailViewModel: CharacterDetailViewModel
        ): ViewModelProvider.Factory {
            return ViewModelFactory(
                creators = mapOf(
                    CharacterDetailViewModel::class.java to Provider<ViewModel> {
                        characterDetailViewModel
                    }
                )
            )
        }
    }
}