package com.example.car.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.car.domain.usecase.GetValidateUserUseCase
import com.example.car.domain.usecase.UserListUseCase
import com.example.car.ui.model.UserDataModel
import com.google.android.gms.maps.model.LatLng
import com.example.car.domain.model.*
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserViewModel
    private val getValidateUserUseCase = mockk<GetValidateUserUseCase>()
    private val userListUseCase = mockk<UserListUseCase>()
    private var userDataModel: UserDataModel? = null

    @Before
    fun setUp() {
        viewModel = UserViewModel(
            userListUseCase,
            getValidateUserUseCase
        )
        userDataModel = UserDataModel(
            userName = "Name",
            mobile = "65 9876542",
            latLang = LatLng(1.3834995613473386, 103.89349767650657),
            address = "ABC 1233",
            company = "CDEF 0987",
            web = "abc.com",
            mail = "abc@gmail.com"
        )
    }

    @Test
    fun `updateUserList test`() {
        Assert.assertEquals(null, viewModel.userList.value)
        viewModel.updateUserList(null)
        Assert.assertEquals(null, viewModel.userList.value)
        viewModel.updateUserList(listOf())
        Assert.assertEquals(emptyList<UserDataModel>(), viewModel.userList.value)

        viewModel.updateUserList(listOf(userDataModel!!))
        Assert.assertEquals(listOf(userDataModel), viewModel.userList.value)
    }

    @Test
    fun `selectedUser test`() {
        Assert.assertEquals(null, viewModel.selectedUser.value)
        viewModel.selectedUser(userDataModel!!)
        Assert.assertEquals(userDataModel, viewModel.selectedUser.value)
    }

    @Test
    fun `validateFields for login app test`() {
        Assert.assertEquals(null, viewModel.enableLogin.value)
        viewModel.validateFields(null, null, null)
        Assert.assertEquals(false, viewModel.enableLogin.value)
        viewModel.validateFields("qwerty", null, null)
        Assert.assertEquals(false, viewModel.enableLogin.value)
        viewModel.validateFields("qwerty", "rewq", null)
        Assert.assertEquals(false, viewModel.enableLogin.value)
        viewModel.validateFields("qwerty", "rewq", "fghjk")
        Assert.assertEquals(true, viewModel.enableLogin.value)
        viewModel.validateFields("", "rewq", "fghjk")
        Assert.assertEquals(false, viewModel.enableLogin.value)
        viewModel.validateFields("werw", "", "fghjk")
        Assert.assertEquals(false, viewModel.enableLogin.value)
        viewModel.validateFields("werw", "kjhg", "")
        Assert.assertEquals(false, viewModel.enableLogin.value)
    }

    @Test
    fun `getCountryList Test`(){
        Assert.assertEquals(listOf(
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
        ), viewModel.getCountryList())
    }


    @Test
    fun `get user list test`() {
        val mockObserver = spyk(Observer<Result<List<UserDataModel>>> {})
        val useCaseResult = MutableLiveData<Result<List<UserDataModel>>>()
        useCaseResult.postValue(Result.Loading)
        viewModel.userListEvent.observeForever(mockObserver)
        every { userListUseCase.execute() } returns useCaseResult
        viewModel.userListEventTrigger.postValue(Event(Unit))
        val slot = slot<Result<List<UserDataModel>>>()
        verify { mockObserver.onChanged(capture(slot)) }
        Assert.assertEquals(Result.Loading, slot.captured)
        verify { userListUseCase.execute() }
    }

    @Test
    fun `validate user test`() {
        val mockObserver = spyk(Observer<Result<Boolean>> {})
        val useCaseResult = MutableLiveData<Result<Boolean>>()
        useCaseResult.postValue(Result.Success(true))
        viewModel.validateUserEvent.observeForever(mockObserver)
        every { getValidateUserUseCase.execute(any()) } returns useCaseResult
        viewModel.validateUserEventTrigger.postValue(Event(Pair("user", "pwd")))
        val slot = slot<Result<Boolean>>()
        verify { mockObserver.onChanged(capture(slot)) }
        Assert.assertEquals(Result.Success(true), slot.captured)
        verify { getValidateUserUseCase.execute(Pair("user", "pwd")) }
    }

}