package com.gojek.rickandmorty.base.mapper

interface Mapper<Input, Output> {
    fun map(input: Input): Output
}