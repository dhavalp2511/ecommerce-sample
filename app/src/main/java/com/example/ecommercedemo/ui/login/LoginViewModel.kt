package com.example.ecommercedemo.ui.login

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommercedemo.common.BaseObservableViewModel
import com.example.ecommercedemo.BR
import com.example.ecommercedemo.common.ValidationUtils
import com.example.ecommercedemo.data.local.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val sharedPrefs: SharedPrefs)  : BaseObservableViewModel() {

    companion object {
        const val IS_EMPTY = 1
        const val INVALID = 2
        const val SUCCESS = 3
        const val EMAIL = "test@test.com"
        const val PASSWORD = "Test@123"
    }


    private val _emailPhoneValidationLiveData = MutableLiveData<Int>()
    val emailPhoneValidationLiveData: LiveData<Int>
        get() = _emailPhoneValidationLiveData


    private val _passwordValidationLiveData = MutableLiveData<Int>()
    val passwordValidationLiveData: LiveData<Int>
        get() = _passwordValidationLiveData

    private val _successLoginLiveData = MutableLiveData<Boolean>()
    val successLoginData: LiveData<Boolean>
        get() = _successLoginLiveData

    private val _errorStringLiveData = MutableLiveData<String?>()
    val errorStringLiveData: LiveData<String?>
        get() = _errorStringLiveData


    @Bindable
    var email: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    @Bindable
    var password: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }


    @Bindable
    var progress: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.progress)
        }

    fun isLoggedIn() : Boolean = sharedPrefs.isLoggedIn

    fun login() {
        if(email == null || email!!.isEmpty()){
            _emailPhoneValidationLiveData.value = IS_EMPTY
        }else if (!ValidationUtils.isValidEmail(email!!)) {
            _emailPhoneValidationLiveData.value = INVALID
        } else {
            _emailPhoneValidationLiveData.value = SUCCESS
        }

        if (password.isNullOrEmpty()) {
            _passwordValidationLiveData.value = IS_EMPTY
        } else {
            _passwordValidationLiveData.value = SUCCESS
        }

        if (_emailPhoneValidationLiveData.value == SUCCESS &&
            _passwordValidationLiveData.value == SUCCESS
        ) {
            progress = true
            if (email?.equals(EMAIL) == true && password?.equals(PASSWORD)  == true){
                sharedPrefs.isLoggedIn = true
                _successLoginLiveData.postValue(true)
            }else{
                _errorStringLiveData.postValue("Invalid username or password")
            }
            progress = false
        }
    }

    fun setErrorLiveDataFalse() {
        _errorStringLiveData.value = null
    }

    fun setSuccessLoginDataFalse() {
        _successLoginLiveData.value = false
    }

}