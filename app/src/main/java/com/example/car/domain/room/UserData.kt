package com.example.car.domain.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserData(
    @PrimaryKey
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "pwd") val pwd: String?
)