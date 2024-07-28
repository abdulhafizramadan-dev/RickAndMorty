package com.gojek.characters.shared.domain.repository

import com.gojek.characters.shared.domain.model.Character
import io.reactivex.Single

interface CharacterRepository {
    fun getCharacters(): Single<List<Character>>
    fun getCharacterDetail(id: Int): Single<Character>
}