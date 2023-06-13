package com.example.ecommercedemo.di

import android.content.Context
import android.content.SharedPreferences
import com.example.ecommercedemo.data.local.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val versionSecurityGuards = 1
        val cacheName = StringBuilder().append(
            SharedPrefs.PREFS_NAME
        ).append(
            versionSecurityGuards.toString()
        ).toString()
        return context.getSharedPreferences(cacheName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPrefs(
        sharedPreferences: SharedPreferences,
    ): SharedPrefs {
        return SharedPrefs(sharedPreferences)
    }
}