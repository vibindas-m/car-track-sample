package com.example.car.di

import com.example.car.domain.room.UserDB
import com.example.car.domain.room.UserRoom
import com.example.car.domain.repository.ApiFactory
import com.example.car.domain.repository.UserRepo
import com.example.car.domain.repository.UserServices
import com.example.car.domain.usecase.GetValidateUserUseCase
import com.example.car.domain.usecase.UserListUseCase
import com.example.car.ui.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val carTrackModule = module {
    single {
        ApiFactory.retrofit().create(UserServices::class.java)
    }
    factory {
        UserRepo(get())
    }

    single { UserDB.getInstance(androidContext()) }
    single {
        val userDB: UserDB = get()
        userDB.userDao()
    }
    single { UserRoom(get()) }
    factory {
        GetValidateUserUseCase(get())
    }

    factory {
        UserListUseCase(get())
    }
    viewModel {
        UserViewModel(get(), get())
    }
}