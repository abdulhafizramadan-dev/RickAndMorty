package com.gojek.characters.usecase

import com.gojek.base.domain.SingleUseCase
import com.gojek.characters.shared.domain.model.Character

interface GetCharactersUseCase : SingleUseCase<Unit, List<Character>>
