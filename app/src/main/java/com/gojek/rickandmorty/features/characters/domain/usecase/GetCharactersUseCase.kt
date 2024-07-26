package com.gojek.rickandmorty.features.characters.domain.usecase

import com.gojek.rickandmorty.base.domain.SingleUseCase
import com.gojek.rickandmorty.features.characters.domain.model.Character

interface GetCharactersUseCase : SingleUseCase<Unit, List<Character>>
