package com.gojek.rickandmorty.features.characters.data

import com.gojek.rickandmorty.features.characters.data.mapper.toDomains
import com.gojek.rickandmorty.features.characters.data.remote.RickAndMortyApi
import com.gojek.rickandmorty.features.characters.domain.model.Character
import com.gojek.rickandmorty.features.characters.domain.repository.CharacterRepository
import io.reactivex.Single

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi
) : CharacterRepository {
    override fun getCharacters(): Single<List<Character>> {
        return api.getCharacters().map { response ->
            response.results?.toDomains() ?: emptyList()
        }
    }
}