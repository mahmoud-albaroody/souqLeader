package com.alef.souqleader.ui.presentation.forgetPassword.resetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.ForgetPasswordResponse
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.domain.CancelationUseCase
import com.alef.souqleader.domain.LoginUseCase
import com.alef.souqleader.domain.model.AddLead
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _resetPassword =
        MutableSharedFlow<ForgetPasswordResponse>()
    val resetPassword: MutableSharedFlow<ForgetPasswordResponse>
        get() = _resetPassword
    private val job = Job()
    fun resetPassword(
        email: String,
        password: String,
        passwordConfirmation: String,
        code: String
    ) {
        viewModelScope.launch(job) {
            loginUseCase.resetPassword(
                email,
                password,
                passwordConfirmation,
                code
            ).data?.let {
                _resetPassword.emit(
                    it
                )
            }
        }
    }
}