package com.example.ecommercedemo.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommercedemo.common.BaseObservableViewModel
import com.example.ecommercedemo.common.UiState
import com.example.ecommercedemo.data.api.AppRepository
import com.example.ecommercedemo.data.api.response.GetProfileResponse
import com.example.ecommercedemo.data.local.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs, private val repository: AppRepository
) : BaseObservableViewModel() {

    private val _profileLiveData = MutableLiveData<UiState<GetProfileResponse>>()
    val profileLiveData: LiveData<UiState<GetProfileResponse>>
        get() = _profileLiveData


    fun logout() {
        sharedPrefs.isLoggedIn = false
    }

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _profileLiveData.postValue(UiState.Loading())
            repository.getProfile().result({ result ->
                _profileLiveData.postValue(UiState.Success(result))
            }) {
                _profileLiveData.postValue(UiState.Error(it))
            }
        }
    }

}