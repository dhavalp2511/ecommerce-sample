package com.example.ecommercedemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ecommercedemo.data.local.SharedPrefs
import com.example.ecommercedemo.ui.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    //A JUnit Test Rule that swaps the background executor used by the Architecture Components with a
    // different one which executes each task synchronously.
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    //  This class is a unit test rule which overrides the default Dispatchers.Main dispatcher and replaces the default with our test dispatcher.
    @ExperimentalCoroutinesApi
    @Rule
    @JvmField
    val coRoutineTestRule = CoroutineTestRule()

    private var loginViewModel: LoginViewModel? = null

    private val sharedPrefs = mock<SharedPrefs>()

    private var mockLoginSuccessObserver = mock<Observer<Boolean>>()
    private var mockLoginFailedObserver = mock<Observer<String?>>()
    private var mockEmailValidationError = mock<Observer<Int>>()
    private var mockPasswordValidationError = mock<Observer<Int>>()

    @Before
    fun before() {
        loginViewModel = LoginViewModel(sharedPrefs)

        loginViewModel?.successLoginData?.observeForever(mockLoginSuccessObserver)
        loginViewModel?.errorStringLiveData?.observeForever(mockLoginFailedObserver)
        loginViewModel?.emailPhoneValidationLiveData?.observeForever(mockEmailValidationError)
        loginViewModel?.passwordValidationLiveData?.observeForever(mockPasswordValidationError)

    }

    @Test
    fun testEmptyEmailPassword() {
        loginViewModel?.login()
        verify(mockEmailValidationError).onChanged(LoginViewModel.IS_EMPTY)
        verify(mockPasswordValidationError).onChanged(LoginViewModel.IS_EMPTY)
    }

    @Test
    fun testInvalidEmail() {
        loginViewModel?.email = "test!"
        loginViewModel?.login()
        verify(mockEmailValidationError).onChanged(LoginViewModel.INVALID)
        verify(mockPasswordValidationError).onChanged(LoginViewModel.IS_EMPTY)
    }

    @Test
    fun testInvalidEmailPassword() {
        loginViewModel?.email = LoginViewModel.EMAIL
        loginViewModel?.password = "ACS"
        loginViewModel?.login()
        verify(mockEmailValidationError).onChanged(LoginViewModel.SUCCESS)
        verify(mockPasswordValidationError).onChanged(LoginViewModel.SUCCESS)
        verify(mockLoginFailedObserver).onChanged("Invalid username or password")
    }

    @Test
    fun testLogin() {
        loginViewModel?.email = LoginViewModel.EMAIL
        loginViewModel?.password = LoginViewModel.PASSWORD
        loginViewModel?.login()
        verify(mockEmailValidationError).onChanged(LoginViewModel.SUCCESS)
        verify(mockPasswordValidationError).onChanged(LoginViewModel.SUCCESS)
        verify(mockLoginSuccessObserver).onChanged(true)
    }
}