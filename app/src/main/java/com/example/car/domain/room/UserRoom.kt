package com.example.car.domain.room

import androidx.annotation.WorkerThread

class UserRoom constructor(private val userDao: UserDao) {
    @WorkerThread
    fun insert(userData: UserData) {
        userDao.insert(userData)
    }

    @WorkerThread
    fun validateUser(userName: String, password: String): Boolean {
        return userDao.validateUser(userName, password)
    }
}