package com.example.ecommercedemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ecommercedemo.common.Result
import com.example.ecommercedemo.data.api.ApiService
import com.example.ecommercedemo.data.api.AppRepositoryImpl
import com.example.ecommercedemo.data.api.response.GetProfileResponse
import com.example.ecommercedemo.data.api.response.ProductListingResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AppRepoTest {

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


    @Mock
    private lateinit var api: ApiService

    private lateinit var appRepository: AppRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        appRepository = AppRepositoryImpl(api)
    }

    @Test
    fun `getProducts should return success result when API call is successful`() = runBlocking {
        // Arrange
        val products = ProductListingResponse(
            ProductListingResponse.Data(
                listOf(
                    ProductListingResponse.Data.Product(
                        id = "1",
                        brand = "VICTORIA'S SECRET",
                        name = "Bombshell Eau de Parfum 100ML",
                        productDesc = "THERE's A BOMBSHELL IN EVERY WOMAN. CONFIDENT &AMP; BOLD, AMERICAs NO . 1 FRAGRANCEIS A SPARKLING MIX OF FRESH-CUT PEONIES AND AFTERNOON SUN. CITRUS NOTES AND BRAZILIAN PURPLE PASSION FRUIT START BOMBSHELL OFF WITH BRIGHT, EXOTIC SPARKLE.",
                        offerPrice = "150",
                        price = "200",
                        productUrl = "https://www.victoriassecretbeauty.in/on/demandware.static/-/Sites-victoria_secret_master_catalog/default/dwaa448da2/large/gr2muwx6e4jk2c6_AABoAY6LC8TzxPWj5M5ad8A5a_111893792457-2.jpg"
                    )
                )
            )
        )
        `when`(api.getProducts()).thenReturn(products)

        // Act
        val result = appRepository.getProducts()

        // Assert
        assertEquals(Result.Success(products), result)
    }


    @Test
    fun `getProfile should return success result when API call is successful`() = runBlocking {
        // Arrange
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
        `when`(api.getProfile()).thenReturn(profile)

        // Act
        val result = appRepository.getProfile()

        // Assert
        assertEquals(Result.Success(profile), result)
    }


}