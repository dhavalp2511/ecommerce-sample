package com.example.ecommercedemo.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.ecommercedemo.R
import com.example.ecommercedemo.databinding.ActivityLoginBinding
import com.example.ecommercedemo.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_login)
        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (loginViewModel.isLoggedIn()) {
                        launchMainActivity()
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        false
                    } else {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }

                }
            }
        )
        binding.viewModel = loginViewModel
        viewModelSetup()
    }

    private fun launchMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun viewModelSetup() {
        loginViewModel.emailPhoneValidationLiveData.observe(this) {
            when (it) {
                LoginViewModel.IS_EMPTY -> {
                    binding.tilLoginEmail.setErrorIconDrawable(0)
                    binding.tilLoginEmail.error = getString(R.string.msg_required_field)
                    binding.tilLoginEmail.isErrorEnabled = true
                }

                LoginViewModel.INVALID -> {
                    binding.tilLoginEmail.setErrorIconDrawable(0)
                    binding.tilLoginEmail.error = getString(R.string.msg_invalid_email_address)
                    binding.tilLoginEmail.isErrorEnabled = true
                }

                LoginViewModel.SUCCESS -> {
                    binding.tilLoginEmail.error = null
                    binding.tilLoginEmail.isErrorEnabled = false
                }

                else -> {
                    binding.tilLoginEmail.error = null
                    binding.tilLoginEmail.isErrorEnabled = false
                }
            }
        }

        loginViewModel.passwordValidationLiveData.observe(this) {
            when (it) {
                LoginViewModel.IS_EMPTY -> {
                    binding.tilLoginPassword.setErrorIconDrawable(0)
                    binding.tilLoginPassword.error = getString(R.string.msg_required_field)
                    binding.tilLoginPassword.isErrorEnabled = true
                }

                LoginViewModel.SUCCESS -> {
                    binding.tilLoginPassword.error = null
                    binding.tilLoginPassword.isErrorEnabled = false
                }

                else -> {
                    binding.tilLoginPassword.error = null
                    binding.tilLoginPassword.isErrorEnabled = false
                }
            }
        }

        loginViewModel.successLoginData.observe(this) {
            if (it) {
                loginViewModel.setSuccessLoginDataFalse()
                loginViewModel.setErrorLiveDataFalse()
                launchMainActivity()
            }
        }

        loginViewModel.errorStringLiveData.observe(this) {
            if (!it.isNullOrEmpty()) {
                run {
                    Toast.makeText(
                        this,
                        getString(R.string.msg_invalid_email_or_password),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}