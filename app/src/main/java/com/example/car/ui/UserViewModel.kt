package com.example.car.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.car.domain.model.Event
import com.example.car.domain.model.Result
import com.example.car.domain.usecase.GetValidateUserUseCase
import com.example.car.domain.usecase.UserListUseCase
import com.example.car.ui.model.UserDataModel

internal class UserViewModel(
    private val userListUseCase: UserListUseCase,
    private val getValidateUserUseCase: GetValidateUserUseCase
) : ViewModel() {

    internal val userListEventTrigger = MutableLiveData<Event<Unit>>()
    val userListEvent: LiveData<Result<List<UserDataModel>>> =
        Transformations.switchMap(userListEventTrigger) {
            userListUseCase.execute()
        }

    internal val validateUserEventTrigger = MutableLiveData<Event<Pair<String, String>>>()
    val validateUserEvent: LiveData<Event<Result<Boolean>>> =
        Transformations.switchMap(validateUserEventTrigger) {
            it.getContentIfNotHandled()?.let { iit ->
                getValidateUserUseCase.execute(iit)
            }
        }

    private var _userList: MutableLiveData<List<UserDataModel>> = MutableLiveData()
    val userList: LiveData<List<UserDataModel>>
        get() = _userList

    private var _selectedUser: MutableLiveData<UserDataModel> = MutableLiveData()
    val selectedUser: LiveData<UserDataModel>
        get() = _selectedUser

    private var _enableLogin: MutableLiveData<Boolean> = MutableLiveData()
    val enableLogin: LiveData<Boolean>
        get() = _enableLogin

    fun updateUserList(userList: List<UserDataModel>?) {
        _userList.value = userList
    }

    fun selectedUser(user: UserDataModel) {
        _selectedUser.value = user
    }

    fun validateFields(userName: String?, pwd: String?, country: String?) {
        _enableLogin.value =
            !userName.isNullOrEmpty() && !pwd.isNullOrEmpty() && !country.isNullOrEmpty()
    }

    fun getCountryList(): List<String> {
        return listOf(
            "Botswana",
            "Espana",
            "France",
            "Hong Kong",
            "indonesia",
            "Malawi",
            "Malawi",
            "Malaysia",
            "Mozambique",
            "Namibia",
            "New Zealand",
            "Nigeria",
            "Philippines",
            "Polska",
            "Portugal",
            "Singapore",
            "South Africa",
            "Swaziland",
            "Tanzania",
            "Thailand",
            "UAE",
            "USA",
            "Zimbabwe"
        )
    }
}