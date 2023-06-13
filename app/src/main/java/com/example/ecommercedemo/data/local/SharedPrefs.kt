package com.example.ecommercedemo.data.local

import android.content.SharedPreferences

class SharedPrefs(
    private val sharedPreferences: SharedPreferences) : BaseSharedPreferences() {

    var isLoggedIn: Boolean
        set(value) = sharedPreferences.put(PREF_IS_LOGGED_IN, value)
        get() = sharedPreferences.get(PREF_IS_LOGGED_IN, Boolean::class.java)


    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        internal const val PREFS_NAME = "AppPreferences"

        private const val PREFIX = "AppPreferences_"
        private const val PREF_IS_LOGGED_IN = "PREF_IS_LOGGED_IN"
    }
}
