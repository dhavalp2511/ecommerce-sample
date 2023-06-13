package com.example.ecommercedemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ecommercedemo.common.UiState
import com.example.ecommercedemo.data.api.AppRepository
import com.example.ecommercedemo.data.api.response.ProductListingResponse
import com.example.ecommercedemo.ui.productlist.ProductListFragment
import com.example.ecommercedemo.ui.productlist.ProductListViewModel
import com.example.ecommercedemo.ui.productlist.adapter.ProductListModel
import com.example.ecommercedemo.ui.productlist.adapter.ProductListType
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


//A helper function to mock classes with types (generics)
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

@RunWith(MockitoJUnitRunner::class)
class ProductListModelTest {

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


    private var productListViewModel: ProductListViewModel? = null

    private var appRepository = mock<AppRepository>()

    private var mockProductListObserver = mock<Observer<UiState<List<ProductListModel>>>>()

    @Before
    fun before() {
        productListViewModel = ProductListViewModel(appRepository)
        productListViewModel?.productsLiveData?.observeForever(mockProductListObserver)
    }




}