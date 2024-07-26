package com.gojek.rickandmorty.characters.domain.usecase

import com.gojek.rickandmorty.base.domain.SingleUseCase
import com.gojek.rickandmorty.characters.domain.model.Character

interface GetCharactersUseCase : SingleUseCase<Unit, List<Character>>
