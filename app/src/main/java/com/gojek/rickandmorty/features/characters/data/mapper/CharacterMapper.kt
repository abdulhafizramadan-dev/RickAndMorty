package com.gojek.rickandmorty.features.characters.data.mapper

import com.gojek.rickandmorty.features.characters.data.remote.response.CharacterItem
import com.gojek.rickandmorty.features.characters.domain.model.Character

fun List<CharacterItem>.toDomains(): List<Character> = map { it.toDomain() }

fun CharacterItem.toDomain(): Character =
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