package com.example.car.domain.model

interface UseCase <R> {
    fun execute(): R
}

interface Cancellable {
    fun cancel()
}