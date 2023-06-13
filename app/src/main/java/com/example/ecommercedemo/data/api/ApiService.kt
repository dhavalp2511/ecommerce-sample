package com.example.ecommercedemo.data.api


import com.example.ecommercedemo.data.api.response.GetProfileResponse
import com.example.ecommercedemo.data.api.response.ProductListingResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("bc09a745-4346-4025-9611-9da76366dbbc")
    suspend fun getProducts(): ProductListingResponse

    @GET("aaf97364-eedc-46a5-8f9e-56eb4b3cedd2")
    suspend fun getProfile(): GetProfileResponse

    companion object {
        private const val BASE_URL = "https://run.mocky.io/v3/"

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
