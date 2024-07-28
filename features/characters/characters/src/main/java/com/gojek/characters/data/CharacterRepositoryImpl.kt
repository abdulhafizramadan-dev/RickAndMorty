package com.gojek.characters.data

import com.gojek.characters.data.remote.RickAndMortyApi
import com.gojek.characters.shared.domain.model.Character
import com.gojek.characters.shared.domain.repository.CharacterRepository
import io.reactivex.Single
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
    private val characterMapper: CharacterMapper
) : CharacterRepository {
    override fun getCharacters(): Single<List<Character>> {
        return api.getCharacters().map { response ->
            response.results?.map { characterMapper.map(it) }
        }
    }

    override fun getCharacterDetail(id: Int): Single<Character> {
        return api.getCharacterDetail(id).map { characterMapper.map(it) }
    }
}