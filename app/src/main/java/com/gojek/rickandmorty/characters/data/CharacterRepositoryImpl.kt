package com.gojek.rickandmorty.characters.data

import com.gojek.rickandmorty.characters.data.mapper.toDomains
import com.gojek.rickandmorty.characters.data.remote.RickAndMortyApi
import com.gojek.rickandmorty.characters.domain.model.Character
import com.gojek.rickandmorty.characters.domain.repository.CharacterRepository
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