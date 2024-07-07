package com.alef.souqleader.ui.presentation.login


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.Login
import com.alef.souqleader.domain.LoginUseCase
import com.alef.souqleader.domain.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.State

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase, private val networkManager: NetworkManager
) : ViewModel() {

    //  var loginState: Login? by mutableStateOf(null)

    private val _loginState = MutableStateFlow(Login())
    val loginState: StateFlow<Login> get() = _loginState


    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(job) {
            _loginState.value = loginUseCase.login(username, password).data!!.data!!
        }
    }

    fun updateBaseUrl(newUrl: String) {
        networkManager.changeBaseUrl(newUrl)
    }
}