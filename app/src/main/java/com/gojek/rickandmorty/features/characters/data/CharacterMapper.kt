package com.gojek.rickandmorty.features.characters.data

import com.gojek.base.mapper.Mapper
import com.gojek.rickandmorty.features.characters.data.remote.response.CharacterItemResponse
import com.gojek.rickandmorty.features.characters.domain.model.Character
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