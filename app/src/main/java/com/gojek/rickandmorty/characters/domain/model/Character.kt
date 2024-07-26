package com.gojek.rickandmorty.characters.domain.model

data class Character(
    val image: String = "",
    val gender: String = "",
    val species: String = "",
    val created: String = "",
    val name: String = "",
    val id: Int = 0,
    val type: String = "",
    val url: String = "",
    val status: String = ""
)