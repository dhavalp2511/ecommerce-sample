package com.example.ecommercedemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ecommercedemo.common.UiState
import com.example.ecommercedemo.data.api.AppRepository
import com.example.ecommercedemo.data.api.response.ProductListingResponse
import com.example.ecommercedemo.ui.productlist.ProductListViewModel
import com.example.ecommercedemo.ui.productlist.adapter.ProductListModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import com.example.ecommercedemo.common.Result
import com.example.ecommercedemo.data.api.response.GetProfileResponse
import com.example.ecommercedemo.data.local.SharedPrefs
import com.example.ecommercedemo.ui.productlist.adapter.ProductListType
import com.example.ecommercedemo.ui.profile.ProfileViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {

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

    private var appRepository = mock<AppRepository>()

    private var sharedPrefs = mock<SharedPrefs>()

    @Test
    fun `getProfile() should return success state`() = runTest  {
        // Create a mock repository
        val profile = GetProfileResponse(
            address = "H2 Block, A wing, MS Ramaiah North City, Manayata Tech Park, Nagavara, Bengaluru, Karnataka 560045",
            dob = "10/11/1900",
            firstname = "John",
            id = "113441",
            walletBalance = "2000",
            lastName = "Wick",
            pointsEarned = "1400",
            username = "wjohn"
        )
        val successResult = Result.Success(profile)

        // Create the view model with the mock repository
        val viewModel = ProfileViewModel(sharedPrefs,appRepository)

        // Stub the repository's getProfile() method to return the success result
        `when`(appRepository.getProfile()).thenReturn(successResult)

        // Call the getProfile() method
        viewModel.getProfile()

        val expectedState =  UiState.Success(GetProfileResponse(
            address = "H2 Block, A wing, MS Ramaiah North City, Manayata Tech Park, Nagavara, Bengaluru, Karnataka 560045",
            dob = "10/11/1900",
            firstname = "John",
            id = "113441",
            walletBalance = "2000",
            lastName = "Wick",
            pointsEarned = "1400",
            username = "wjohn"
        ))

        delay(1000)

        assertEquals(expectedState, viewModel.profileLiveData.value)
    }


}