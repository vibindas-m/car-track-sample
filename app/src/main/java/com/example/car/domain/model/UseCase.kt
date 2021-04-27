package com.example.car.domain.model

interface UseCase <R> {
    fun execute(): R
}

interface UseCaseWithDate <P, R> {
    fun execute(params: P): R
}

interface Cancellable {
    fun cancel()
}