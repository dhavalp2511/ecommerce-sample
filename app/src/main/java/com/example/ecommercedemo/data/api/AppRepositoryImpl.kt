package com.example.ecommercedemo.data.api

import com.example.ecommercedemo.data.api.response.GetProfileResponse
import com.example.ecommercedemo.data.api.response.ProductListingResponse
import javax.inject.Inject
import com.example.ecommercedemo.common.Result

class AppRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : AppRepository {
    override suspend fun getProducts(): Result<Throwable,ProductListingResponse> {
        return try {
            val products = api.getProducts()
            Result.Success(products)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getProfile(): Result<Throwable,GetProfileResponse> {
        return try {
            val profile = api.getProfile()
            Result.Success(profile)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}