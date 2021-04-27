package com.example.car.ui.model

import com.google.android.gms.maps.model.LatLng

data class UserDataModel(
    val userName: String,
    val mobile: String,
    val latLang: LatLng,
    val address: String,
    val company: String,
    val web: String,
    val mail: String
)