package com.example.medicalapp.ui.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalapp.R
import com.example.medicalapp.db.entitys.AuthEntity
import com.example.medicalapp.repository.AuthRepository
import com.example.medicalapp.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val emailErrorLiveData = MutableLiveData<Int>()
    val passwordErrorLiveData = MutableLiveData<Int>()

    val loginResponse = MutableLiveData<AuthEntity?>()

    val registerResponse = MutableLiveData<Boolean>()

    fun login(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        if (validate(username, password)) {
            authRepository.loginUser(username, password)?.let {
                loginResponse.postValue(it)
            } ?: loginResponse.postValue(null)
        }
    }


    fun register(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        if (validate(username, password)) {
            authRepository.registerUser(username, password)
            registerResponse.postValue(true)
        }
    }


    private fun validate(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            emailErrorLiveData.postValue(R.string.please_enter_email_id)
            return false
        } else if (!username.isValidEmail()) {
            emailErrorLiveData.postValue(R.string.please_enter_valid_email_id)
            return false
        }
        emailErrorLiveData.postValue(-1)
        if (password.isEmpty()) {
            passwordErrorLiveData.postValue(R.string.please_enter_password)
            return false
        }
        passwordErrorLiveData.postValue(-1)
        return true
    }

}