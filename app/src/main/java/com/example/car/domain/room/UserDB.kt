package com.example.car.domain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.car.domain.model.DataGenerator
import java.util.concurrent.Executors

@Database(entities = [UserData::class], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: UserDB? = null

        fun getInstance(context: Context): UserDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): UserDB {
            synchronized(this) {
                return Room.databaseBuilder(context, UserDB::class.java, "user_database")
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //pre-populate data
                            Executors.newSingleThreadExecutor().execute {
                                instance?.let {
                                    it.userDao().insert(DataGenerator.getUsers())
                                }
                            }
                        }
                    }).build()
            }
        }
    }

}

