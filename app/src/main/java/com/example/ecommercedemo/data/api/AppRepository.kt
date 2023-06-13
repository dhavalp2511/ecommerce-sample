package com.example.ecommercedemo.data.api

import com.example.ecommercedemo.data.api.response.GetProfileResponse
import com.example.ecommercedemo.data.api.response.ProductListingResponse
import com.example.ecommercedemo.common.Result

interface AppRepository {
    suspend fun getProducts() : Result<Throwable,ProductListingResponse>
    suspend fun getProfile() : Result<Throwable,GetProfileResponse>
}