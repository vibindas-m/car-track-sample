package com.example.car.domain.model

import com.example.car.domain.room.UserData

class DataGenerator {
    companion object {
        fun getUsers(): UserData{
            return UserData("user1", "password1")

        }
    }
}