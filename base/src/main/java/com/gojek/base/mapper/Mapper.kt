package com.gojek.base.mapper

interface Mapper<Input, Output> {
    fun map(input: Input): Output
}