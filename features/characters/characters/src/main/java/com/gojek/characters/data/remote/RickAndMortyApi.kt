package com.gojek.characters.data.remote

import com.gojek.characters.data.remote.response.CharacterItemResponse
import com.gojek.characters.data.remote.response.CharactersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {
    @GET("character")
    fun getCharacters(): Single<CharactersResponse>

    @GET("character/{id}")
    fun getCharacterDetail(
        @Path("id") id: Int
    ): Single<CharacterItemResponse>
}