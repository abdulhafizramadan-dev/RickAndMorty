package com.gojek.rickandmorty.characters.data.remote

import com.gojek.rickandmorty.characters.data.remote.response.CharactersResponse
import io.reactivex.Single
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    fun getCharacters(): Single<CharactersResponse>
}