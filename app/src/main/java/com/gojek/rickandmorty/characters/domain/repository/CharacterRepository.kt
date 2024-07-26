package com.gojek.rickandmorty.characters.domain.repository

import com.gojek.rickandmorty.characters.domain.model.Character
import io.reactivex.Single

interface CharacterRepository {
    fun getCharacters(): Single<List<Character>>
}