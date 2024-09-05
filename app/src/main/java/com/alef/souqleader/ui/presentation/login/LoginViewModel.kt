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
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AddLike
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.LoginResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase, private val networkManager: NetworkManager
) : ViewModel() {

    //  var loginState: Login? by mutableStateOf(null)


    private val _loginState =
        MutableSharedFlow<Resource<LoginResponse>>()
    val loginState: MutableSharedFlow<Resource<LoginResponse>>
        get() = _loginState

    private val _updateFcmToken = MutableStateFlow(StatusResponse())
    val updateFcmToken: StateFlow<StatusResponse> get() = _updateFcmToken

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(job) {
            loginUseCase.login(username, password).catch { }
                .onStart {
                    _loginState.emit(Resource.Loading())
                }.buffer().collect {
                    _loginState.emit(it)
                }
        }
    }

    fun updateFcmToken(token: String) {
        viewModelScope.launch(job) {
            loginUseCase.updateFcmToken(token).data?.let {
                _updateFcmToken.value = it
            }
        }
    }


    fun updateBaseUrl(newUrl: String) {
        networkManager.changeBaseUrl(newUrl)
    }
}