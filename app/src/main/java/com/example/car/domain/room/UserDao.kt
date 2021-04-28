package com.example.car.domain.room

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT EXISTS( SELECT * FROM user_data WHERE username == :userName AND pwd == :password)")
    fun validateUser(userName: String, password: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userData: UserData)

}