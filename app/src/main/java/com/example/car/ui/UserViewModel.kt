package com.example.car.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.car.domain.model.Result
import com.example.car.domain.model.Event
import com.example.car.domain.model.User
import com.example.car.domain.usecase.UserListUseCase
import com.example.car.ui.model.UserDataModel

class UserViewModel(private val userListUseCase: UserListUseCase) : ViewModel() {

    internal val userListEventTrigger = MutableLiveData<Event<Unit>>()
    val userListEvent: LiveData<Result<List<UserDataModel>>> =
        Transformations.switchMap(userListEventTrigger) {
            userListUseCase.execute()
        }

    private var _userList: MutableLiveData<List<UserDataModel>> = MutableLiveData()
    val userList: LiveData<List<UserDataModel>>
        get() = _userList

    fun updateUser(userList: List<UserDataModel>?) {
        _userList.value = userList
    }
}