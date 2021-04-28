package com.example.car.domain.repository

import com.example.car.data.User
import retrofit2.Call
import retrofit2.http.GET

interface UserServices {
    @GET("users")
    fun getUserList(): Call<List<User>>
}