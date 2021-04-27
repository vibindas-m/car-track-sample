package com.example.car.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.car.domain.model.Event
import com.example.car.domain.model.Result
import com.example.car.domain.usecase.UserListUseCase
import com.example.car.ui.model.UserDataModel
import com.google.android.gms.maps.model.LatLng

class UserViewModel(private val userListUseCase: UserListUseCase) : ViewModel() {

    internal val userListEventTrigger = MutableLiveData<Event<Unit>>()
    val userListEvent: LiveData<Result<List<UserDataModel>>> =
        Transformations.switchMap(userListEventTrigger) {
            userListUseCase.execute()
        }

    private var _userList: MutableLiveData<List<UserDataModel>> = MutableLiveData()
    val userList: LiveData<List<UserDataModel>>
        get() = _userList

    private var _selectedUser: MutableLiveData<UserDataModel> = MutableLiveData()
    val selectedUser: LiveData<UserDataModel>
        get() = _selectedUser

    fun updateUserList(userList: List<UserDataModel>?) {
        _userList.value = userList
    }
    fun selectedUser(user: UserDataModel) {
        _selectedUser.value = user
    }
}