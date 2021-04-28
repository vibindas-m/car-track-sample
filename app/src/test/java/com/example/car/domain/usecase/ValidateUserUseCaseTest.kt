package com.example.car.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.car.MainCoroutineRule
import com.example.car.domain.model.Event
import com.example.car.domain.model.Result
import com.example.car.domain.room.UserRoom
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

class ValidateUserUseCaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val userRoom = mockk<UserRoom>()
    private lateinit var useCase: GetValidateUserUseCase

    @ExperimentalCoroutinesApi
    @Before
    fun init() {
        useCase = GetValidateUserUseCase(userRoom, provideFaveCoroutineDispatcher(testCoroutineDispatcher))
    }

    @ExperimentalCoroutinesApi
    @After
    fun after() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `validate usecase with success result`() = mainCoroutineRule.runBlockingTest {
        coEvery { userRoom.validateUser("usr", "pwd") } returns true
        testCoroutineDispatcher.pauseDispatcher()
        val resultLiveData = useCase.execute(Pair("usr", "pwd"))
        val loadingResult = resultLiveData.getOrAwaitValue()
        Assert.assertTrue(loadingResult.getContentIfNotHandled() is Result.Loading)

        testCoroutineDispatcher.resumeDispatcher()
        val successResult = resultLiveData.getOrAwaitValue()
        Assert.assertTrue(successResult.getContentIfNotHandled()is Result.Success)

        Assert.assertEquals(true, (successResult.peekContent() as Result.Success).data)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `validate usecase with Failure result`() = mainCoroutineRule.runBlockingTest {
        coEvery { userRoom.validateUser("","") } returns false
        testCoroutineDispatcher.pauseDispatcher()
        val resultLiveData = useCase.execute(Pair("",""))
        val loadingResult = resultLiveData.getOrAwaitValue()
        Assert.assertTrue(loadingResult.getContentIfNotHandled() is Result.Loading)

        testCoroutineDispatcher.resumeDispatcher()
        val successResult = resultLiveData.getOrAwaitValue()
        Assert.assertTrue(successResult.getContentIfNotHandled() is Result.Failure)
    }

}