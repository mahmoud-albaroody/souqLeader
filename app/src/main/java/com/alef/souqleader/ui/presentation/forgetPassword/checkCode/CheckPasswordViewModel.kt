package com.alef.souqleader.ui.presentation.forgetPassword.checkCode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckPasswordViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _checkCode =
        MutableSharedFlow<StatusResponse>()
    val checkCode: MutableSharedFlow<StatusResponse>
        get() = _checkCode
    private val job = Job()
    fun checkCode(code: String) {
        viewModelScope.launch(job) {
            _checkCode.emit(
                loginUseCase.checkCode(
                    code
                ).data!!
            )
        }
    }
}