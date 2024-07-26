package com.gojek.rickandmorty.features.characters.data

import com.gojek.rickandmorty.features.characters.data.remote.RickAndMortyApi
import com.gojek.rickandmorty.features.characters.domain.model.Character
import com.gojek.rickandmorty.features.characters.domain.repository.CharacterRepository
import io.reactivex.Single

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val characterMapper: CharacterMapper
) : CharacterRepository {
    override fun getCharacters(): Single<List<Character>> {
        return api.getCharacters().map { response ->
            response.results?.map { characterMapper.map(it) }
        }
    }
}