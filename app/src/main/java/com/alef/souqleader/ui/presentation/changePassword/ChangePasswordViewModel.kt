package com.alef.souqleader.ui.presentation.changePassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ChangePasswordViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _changePassword =
        MutableSharedFlow<StatusResponse>()
    val changePassword: MutableSharedFlow<StatusResponse>
        get() = _changePassword
    private val job = Job()
    fun changePassword(password: String, newPassword: String, confirmPassword: String) {
        viewModelScope.launch(job) {
            _changePassword.emit(
                loginUseCase.changePassword(
                    password,
                    newPassword,
                    confirmPassword
                ).data!!
            )
        }
    }
}