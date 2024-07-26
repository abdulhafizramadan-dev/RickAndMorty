package com.gojek.rickandmorty.characters.domain.usecase

import com.gojek.rickandmorty.characters.domain.model.Character
import com.gojek.rickandmorty.characters.domain.repository.CharacterRepository
import io.reactivex.Single

class DefaultGetCharactersUseCase(
    private val characterRepository: CharacterRepository
) : GetCharactersUseCase {
    override fun execute(input: Unit): Single<List<Character>> {
        return characterRepository.getCharacters()
    }
}