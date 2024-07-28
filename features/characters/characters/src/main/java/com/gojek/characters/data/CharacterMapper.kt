package com.gojek.characters.data

import com.gojek.base.mapper.Mapper
import com.gojek.characters.data.remote.response.CharacterItemResponse
import com.gojek.characters.shared.domain.model.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor() : Mapper<CharacterItemResponse, Character> {
    override fun map(input: CharacterItemResponse): Character {
        return with(input) {
            Character(
                image = image ?: "",
                gender = gender ?: "",
                species = species ?: "",
                created = created ?: "",
                name = name ?: "",
                id = id ?: 0,
                type = type ?: "",
                url = url ?: "",
                status = status ?: ""
            )
        }
    }
}