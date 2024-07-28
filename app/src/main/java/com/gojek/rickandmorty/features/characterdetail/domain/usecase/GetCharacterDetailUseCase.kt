package com.gojek.rickandmorty.features.characterdetail.domain.usecase

import com.gojek.rickandmorty.base.domain.SingleUseCase
import com.gojek.rickandmorty.features.characterdetail.domain.usecase.GetCharacterDetailUseCase.Param
import com.gojek.rickandmorty.features.characters.domain.model.Character

interface GetCharacterDetailUseCase : SingleUseCase<Param, Character> {
    data class Param(
        val id: Int
    )
}
