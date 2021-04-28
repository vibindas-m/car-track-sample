package com.example.car.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.car.MainCoroutineRule
import com.example.car.data.Address
import com.example.car.data.Company
import com.example.car.data.Geo
import com.example.car.data.User
import com.example.car.domain.model.Response
import com.example.car.domain.model.Result
import com.example.car.domain.repository.UserRepo
import com.example.car.ui.model.UserDataModel
import com.google.android.gms.maps.model.LatLng
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

class UserListUseCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val repo = mockk<UserRepo>()
    private lateinit var useCase: UserListUseCase

    @ExperimentalCoroutinesApi
    @Before
    fun init() {
        useCase = UserListUseCase(repo, provideFaveCoroutineDispatcher(testCoroutineDispatcher))
    }

    @ExperimentalCoroutinesApi
    @After
    fun after() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test User list usecase with success result`() = mainCoroutineRule.runBlockingTest {
        coEvery { repo.getUserList() } returns Response.Success(
            listOf(
                User(
                    id = "1",
                    name = "Leanne Graham",
                    username = "Bret",
                    email = "Sincere@april.biz",
                    address = Address(
                        street = "Kulas Light",
                        suite = "Apt. 556",
                        city = "Gwenborough",
                        zipcode = "92998-3874",
                        geo = Geo(
                            lat = "-37.3159",
                            lng = "81.1496"
                        )
                    ),
                    phone = "1-770-736-8031 x56442",
                    website = "hildegard.org",
                    company = Company(
                        name = "Romaguera-Crona",
                        catchPhrase = "Multi-layered client-server neural-net",
                        bs = "harness real-time e-markets"
                    )
                )
            )
        )
        testCoroutineDispatcher.pauseDispatcher()
        val resultLiveData = useCase.execute()
        val loadingResult = resultLiveData.getOrAwaitValue()
        Assert.assertTrue(loadingResult is Result.Loading)

        testCoroutineDispatcher.resumeDispatcher()
        val successResult = resultLiveData.getOrAwaitValue()
        Assert.assertTrue(successResult is Result.Success)

        Assert.assertEquals(
            listOf(
                UserDataModel(
                    userName = "Leanne Graham",
                    mobile = "1-770-736-8031 x56442",
                    latLang = LatLng(
                        -37.3159,
                        81.1496
                    ),
                    address = "Kulas Light\nApt. 556\nGwenborough\n92998-3874",
                    company = "Romaguera-Crona\nMulti-layered client-server neural-net\nharness real-time e-markets",
                    web = "hildegard.org",
                    mail = "Sincere@april.biz"
                )
            ), (successResult as Result.Success).data
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test User list usecase with Failure result`() = mainCoroutineRule.runBlockingTest {
        coEvery { repo.getUserList() } returns Response.Error("Failed")
        testCoroutineDispatcher.pauseDispatcher()
        val resultLiveData = useCase.execute()
        val loadingResult = resultLiveData.getOrAwaitValue()
        Assert.assertTrue(loadingResult is Result.Loading)

        testCoroutineDispatcher.resumeDispatcher()
        val successResult = resultLiveData.getOrAwaitValue()
        Assert.assertTrue(successResult is Result.Failure)
    }

}