package com.gojek.character.detail.usecase

import com.gojek.characters.shared.domain.model.Character
import com.gojek.characters.shared.domain.repository.CharacterRepository
import io.reactivex.Single
import javax.inject.Inject

class DefaultGetCharacterDetailUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : GetCharacterDetailUseCase {
    override fun execute(input: GetCharacterDetailUseCase.Param): Single<Character> {
        return characterRepository.getCharacterDetail(input.id)
    }
}