package com.example.ecommercedemo.common

import androidx.core.util.PatternsCompat

object ValidationUtils {

    fun isValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

}
