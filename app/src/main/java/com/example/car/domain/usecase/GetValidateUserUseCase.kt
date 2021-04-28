package com.example.car.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.car.domain.room.UserRoom
import com.example.car.domain.model.Cancellable
import com.example.car.domain.util.CustomCoroutineDispatcherProvider
import com.example.car.domain.model.Result
import com.example.car.domain.model.UseCaseWithParams
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

internal class GetValidateUserUseCase(
    private val userRoom: UserRoom,
    private val customCoroutineDispatcherProvider: CustomCoroutineDispatcherProvider
) :
    UseCaseWithParams<Pair<String, String>, LiveData<Result<Boolean>>>,
    CoroutineScope,
    Cancellable {
    var job: Job? = null
    override val coroutineContext: CoroutineContext
        get() = customCoroutineDispatcherProvider.io

    override fun execute(params: Pair<String, String>): LiveData<Result<Boolean>> {
        val result = MutableLiveData<Result<Boolean>>()
        result.postValue(Result.Loading)
        job = launch {
            val response = userRoom.validateUser(userName = params.first, password = params.second)
            val toPost = when {
                response -> Result.Success(response)
                else -> Result.Failure("Invalid User name or Password")
            }
            result.postValue(toPost)
        }
        return result
    }

    override fun cancel() {
        if (coroutineContext.isActive)
            job?.cancel()
    }
}