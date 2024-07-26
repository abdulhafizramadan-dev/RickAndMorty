package com.gojek.rickandmorty.features.characters.domain.repository

import com.gojek.rickandmorty.features.characters.domain.model.Character
import io.reactivex.Single

interface CharacterRepository {
    fun getCharacters(): Single<List<Character>>
}