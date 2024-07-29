package com.gojek.character.detail.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gojek.base.presentation.MviAction
import com.gojek.base.presentation.MviResult
import com.gojek.base.utils.ViewModelFactory
import com.gojek.character.detail.presentation.CharacterDetailActionProcessor
import com.gojek.character.detail.presentation.CharacterDetailViewModel
import com.gojek.character.detail.usecase.DefaultGetCharacterDetailUseCase
import com.gojek.character.detail.usecase.GetCharacterDetailUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.ObservableTransformer
import javax.inject.Named
import javax.inject.Provider

@Module(subcomponents = [CharacterDetailComponent::class])
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