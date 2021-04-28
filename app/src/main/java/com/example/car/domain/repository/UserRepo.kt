package com.example.car.domain.repository


import com.example.car.domain.model.Response
import com.example.car.data.User
import retrofit2.await

class UserRepo(private val userServices: UserServices) {

    suspend fun getUserList(): Response<List<User>> {
        return try {
            val result = userServices.getUserList().await()
            with(result) {
                if (this.isNotEmpty()) {
                    Response.Success(this)
                } else {
                    Response.Error("No User Data")
                }
            }
        } catch (e: Exception) {
            Response.Error(e.message ?: "")
        }
    }
}