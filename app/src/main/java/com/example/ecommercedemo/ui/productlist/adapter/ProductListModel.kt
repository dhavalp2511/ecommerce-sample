package com.example.ecommercedemo.ui.productlist.adapter

import com.example.ecommercedemo.data.api.response.ProductListingResponse


enum class ProductListType(val type: Int) {
    PRODUCT(1), REWARDS(1),
}


data class ProductListModel(
    val type: ProductListType,
    val product: ProductListingResponse.Data.Product?
)