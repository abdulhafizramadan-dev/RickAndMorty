package com.gojek.rickandmorty.features.characters.domain.usecase

import com.gojek.rickandmorty.features.characters.domain.model.Character
import com.gojek.rickandmorty.features.characters.domain.repository.CharacterRepository
import io.reactivex.Single

class DefaultGetCharactersUseCase(
    private val characterRepository: CharacterRepository
) : GetCharactersUseCase {
    override fun execute(input: Unit): Single<List<Character>> {
        return characterRepository.getCharacters()
    }
}