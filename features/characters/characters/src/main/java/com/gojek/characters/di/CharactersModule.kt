package com.gojek.characters.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gojek.base.presentation.MviAction
import com.gojek.base.presentation.MviResult
import com.gojek.base.utils.ViewModelFactory
import com.gojek.characters.data.CharacterRepositoryImpl
import com.gojek.characters.presentation.CharactersActionProcessor
import com.gojek.characters.presentation.CharactersViewModel
import com.gojek.characters.shared.domain.repository.CharacterRepository
import com.gojek.characters.usecase.DefaultGetCharactersUseCase
import com.gojek.characters.usecase.GetCharactersUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.ObservableTransformer
import javax.inject.Named
import javax.inject.Provider

@Module(subcomponents = [CharactersComponent::class])
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

    companion object {
        @Provides
        @Named("charactersViewModelFactory")
        fun providesViewModelFactory(
            charactersViewModel: CharactersViewModel
        ): ViewModelProvider.Factory {
            return ViewModelFactory(
                creators = mapOf(
                    CharactersViewModel::class.java to Provider<ViewModel> {
                        charactersViewModel
                    }
                )
            )
        }
    }
}