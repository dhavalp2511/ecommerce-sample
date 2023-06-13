package com.example.ecommercedemo.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommercedemo.common.BaseObservableViewModel
import com.example.ecommercedemo.common.UiState
import com.example.ecommercedemo.data.api.AppRepository
import com.example.ecommercedemo.ui.productlist.adapter.ProductListModel
import com.example.ecommercedemo.ui.productlist.adapter.ProductListType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: AppRepository
) : BaseObservableViewModel() {

    private val _productsLiveData = MutableLiveData<UiState<List<ProductListModel>>>()
    val productsLiveData: LiveData<UiState<List<ProductListModel>>>
        get() = _productsLiveData

    fun getProducts() {
        _productsLiveData.postValue(UiState.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProducts().result({ result ->
                result.data?.products?.let {
                    val list = it.map { product ->
                        ProductListModel(ProductListType.PRODUCT, product)
                    }.toMutableList()
                    list.add(1, ProductListModel(ProductListType.REWARDS, null))
                    _productsLiveData.postValue(UiState.Success(list))
                }
            }) {
                _productsLiveData.postValue(UiState.Error(it))
            }
        }

    }
}
