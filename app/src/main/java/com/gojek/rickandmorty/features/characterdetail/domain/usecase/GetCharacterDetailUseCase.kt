package com.gojek.rickandmorty.features.characterdetail.domain.usecase

import com.gojek.base.domain.SingleUseCase
import com.gojek.rickandmorty.features.characterdetail.domain.usecase.GetCharacterDetailUseCase.Param
import com.gojek.characters.shared.domain.model.Character

interface GetCharacterDetailUseCase : SingleUseCase<Param, Character> {
    data class Param(
        val id: Int
    )
}
