package com.example.car.di

import com.example.car.domain.repository.ApiFactory
import com.example.car.domain.repository.UserRepo
import com.example.car.domain.repository.UserServices
import com.example.car.domain.usecase.UserListUseCase
import com.example.car.ui.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val carTrackModule = module {
    single {
        ApiFactory.retrofit().create(UserServices::class.java)
    }
    factory {
        UserRepo(get())
    }
    factory {
        UserListUseCase(get())
    }
    viewModel {
        UserViewModel(get())
    }
}