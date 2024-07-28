package com.gojek.characters.data.remote.response

import com.google.gson.annotations.SerializedName

data class CharactersResponse(

    @field:SerializedName("results")
    val results: List<CharacterItemResponse>? = null,

    @field:SerializedName("info")
    val info: InfoResponse? = null
)