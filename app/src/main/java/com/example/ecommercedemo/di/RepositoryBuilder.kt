package com.example.ecommercedemo.di

import com.example.ecommercedemo.data.api.ApiService
import com.example.ecommercedemo.data.api.AppRepository
import com.example.ecommercedemo.data.api.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryBuilder {

    @Singleton
    @Provides
    fun bindsAppRepository(apiService: ApiService): AppRepository {
        return AppRepositoryImpl(apiService)
    }
}
