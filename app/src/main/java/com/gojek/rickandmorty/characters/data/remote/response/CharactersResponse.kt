package com.gojek.rickandmorty.characters.data.remote.response

import com.google.gson.annotations.SerializedName

data class CharactersResponse(

	@field:SerializedName("results")
	val results: List<CharacterItem>? = null,

	@field:SerializedName("info")
	val info: InfoResponse? = null
)