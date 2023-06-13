package com.example.ecommercedemo


import com.example.ecommercedemo.common.ValidationUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidationTest {

    @Test
    fun isValidEmail() {
        assertEquals(ValidationUtils.isValidEmail("test"), false)
        assertEquals(ValidationUtils.isValidEmail("test@test.com"), true)
    }

}
