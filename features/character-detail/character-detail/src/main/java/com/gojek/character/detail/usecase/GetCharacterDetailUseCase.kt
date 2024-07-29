package com.gojek.character.detail.usecase

import com.gojek.base.domain.SingleUseCase
import com.gojek.character.detail.usecase.GetCharacterDetailUseCase.Param
import com.gojek.characters.shared.domain.model.Character

interface GetCharacterDetailUseCase : SingleUseCase<Param, Character> {
    data class Param(
        val id: Int
    )
}
