package com.example.ecommercedemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ecommercedemo.common.Result
import com.example.ecommercedemo.common.UiState
import com.example.ecommercedemo.data.api.AppRepository
import com.example.ecommercedemo.data.api.response.ProductListingResponse
import com.example.ecommercedemo.ui.productlist.ProductListViewModel
import com.example.ecommercedemo.ui.productlist.adapter.ProductListModel
import com.example.ecommercedemo.ui.productlist.adapter.ProductListType
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

//A helper function to mock classes with types (generics)
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

@RunWith(MockitoJUnitRunner::class)
class ProductListViewModelTest {

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

    @Test
    fun `getProducts() should return success state`() = runTest {
        // Create a mock repository
        val productList = ProductListingResponse(
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
        val successResult = Result.Success(productList)

        // Create the view model with the mock repository
        val viewModel = ProductListViewModel(appRepository)

        // Stub the repository's getProducts() method to return the success result
        `when`(appRepository.getProducts()).thenReturn(successResult)

        // Call the getProducts() method
        viewModel.getProducts()

        // Verify that the productsLiveData receives the expected success state
        val expectedState = UiState.Success(
            mutableListOf(
                ProductListModel(
                    ProductListType.PRODUCT, ProductListingResponse.Data.Product(
                        id = "1",
                        brand = "VICTORIA'S SECRET",
                        name = "Bombshell Eau de Parfum 100ML",
                        productDesc = "THERE's A BOMBSHELL IN EVERY WOMAN. CONFIDENT &AMP; BOLD, AMERICAs NO . 1 FRAGRANCEIS A SPARKLING MIX OF FRESH-CUT PEONIES AND AFTERNOON SUN. CITRUS NOTES AND BRAZILIAN PURPLE PASSION FRUIT START BOMBSHELL OFF WITH BRIGHT, EXOTIC SPARKLE.",
                        offerPrice = "150",
                        price = "200",
                        productUrl = "https://www.victoriassecretbeauty.in/on/demandware.static/-/Sites-victoria_secret_master_catalog/default/dwaa448da2/large/gr2muwx6e4jk2c6_AABoAY6LC8TzxPWj5M5ad8A5a_111893792457-2.jpg"
                    )
                ),
                ProductListModel(ProductListType.REWARDS, null)
            )
        )

        delay(1000)

        assertEquals(expectedState, viewModel.productsLiveData.value)
    }


}