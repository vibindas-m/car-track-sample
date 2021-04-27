package com.example.car.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.car.domain.model.*
import com.example.car.domain.repository.UserRepo
import com.example.car.ui.model.UserDataModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UserListUseCase(private val userRepo: UserRepo) :
    UseCase<LiveData<Result<List<UserDataModel>>>>,
    CoroutineScope,
    Cancellable {
    var job: Job? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun execute(): LiveData<Result<List<UserDataModel>>> {
        val result = MutableLiveData<Result<List<UserDataModel>>>()
        result.postValue(Result.Loading)
        job = launch {
            val toPost = when (val response = userRepo.getUserList()) {
                is Response.Success -> {
                    Result.Success(response.data.getUserModelList())
                }
                is Response.Error -> {
                    Result.Failure(response.errorMsg)
                }
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

private fun List<User>.getUserModelList(): List<UserDataModel> {
    return this.map {
        UserDataModel(
            userName = it.name ?: "",
            mobile = it.phone ?: "",
            latLang = LatLng(
                it.address?.geo?.lat?.toDoubleOrNull() ?: 0.0,
                it.address?.geo?.lng?.toDoubleOrNull() ?: 0.0
            ),
            address = "${it.address?.street}\n${it.address?.suite}\n${it.address?.city}\n${it.address?.zipcode}",
            company = "${it.company?.name}\n${it.company?.catchPhrase}\n${it.company?.bs}",
            web = it.website ?: "",
            mail = it.email ?: ""
        )
    }
}