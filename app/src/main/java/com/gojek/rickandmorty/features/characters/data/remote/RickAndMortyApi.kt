package com.gojek.rickandmorty.features.characters.data.remote

import com.gojek.rickandmorty.features.characters.data.remote.response.CharactersResponse
import io.reactivex.Single
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    fun getCharacters(): Single<CharactersResponse>
}