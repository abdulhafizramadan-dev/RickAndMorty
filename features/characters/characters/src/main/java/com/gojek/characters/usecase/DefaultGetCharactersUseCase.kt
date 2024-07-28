package com.gojek.characters.usecase

import com.gojek.characters.shared.domain.model.Character
import com.gojek.characters.shared.domain.repository.CharacterRepository
import io.reactivex.Single
import javax.inject.Inject

class DefaultGetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharactersUseCase {
    override fun execute(input: Unit): Single<List<Character>> {
        return characterRepository.getCharacters()
    }
}