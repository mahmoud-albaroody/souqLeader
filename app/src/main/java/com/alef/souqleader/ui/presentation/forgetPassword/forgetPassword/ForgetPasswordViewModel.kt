package com.alef.souqleader.ui.presentation.forgetPassword.forgetPassword

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
class ForgetPasswordViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _forgetPassword =
        MutableSharedFlow<ForgetPasswordResponse>()
    val forgetPassword: MutableSharedFlow<ForgetPasswordResponse>
        get() = _forgetPassword
    private val job = Job()
    fun forgetPassword(email: String) {
        viewModelScope.launch(job) {
            loginUseCase.forgetPassword(
                email
            ).data?.let {
                _forgetPassword.emit(
                    it
                )
            }
        }
    }
}