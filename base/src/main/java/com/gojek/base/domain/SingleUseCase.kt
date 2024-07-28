package com.gojek.base.domain

import io.reactivex.Single

interface SingleUseCase<Input, Output> {
    fun execute(input: Input): Single<Output>
}