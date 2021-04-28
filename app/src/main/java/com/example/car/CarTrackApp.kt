package com.example.car

import android.app.Application
import com.example.car.di.carTrackModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class CarTrackApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {

            androidContext(this@CarTrackApp)
            modules(
                listOf(
                    carTrackModule
                )
            )
        }
    }
}