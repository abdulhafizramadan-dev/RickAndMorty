package com.gojek.rickandmorty.features.characterdetail.domain.usecase

import com.gojek.rickandmorty.features.characters.domain.model.Character
import com.gojek.rickandmorty.features.characters.domain.repository.CharacterRepository
import io.reactivex.Single
import javax.inject.Inject

class DefaultGetCharacterDetailUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharacterDetailUseCase {
    override fun execute(input: GetCharacterDetailUseCase.Param): Single<Character> {
        return characterRepository.getCharacterDetail(input.id)
    }
}