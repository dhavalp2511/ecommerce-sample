package com.example.ecommercedemo.di

import com.example.ecommercedemo.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideUnsplashService(): ApiService {
        return ApiService.create()
    }
}
