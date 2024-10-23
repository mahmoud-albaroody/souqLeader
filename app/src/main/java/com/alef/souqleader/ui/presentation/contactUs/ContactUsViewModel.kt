package com.alef.souqleader.ui.presentation.contactUs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.ForgetPasswordResponse
import com.alef.souqleader.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _contactUs =
        MutableSharedFlow<ForgetPasswordResponse>()
    val contactUs: MutableSharedFlow<ForgetPasswordResponse>
        get() = _contactUs
    private val job = Job()
    fun contactus(
        name: String?,
        email: String?,
        phone: String?,
        organizationName: String?,
        message: String?
    ) {
        viewModelScope.launch(job) {
            _contactUs.emit(
                loginUseCase.contactus(
                    name,
                    email,
                    phone,
                    organizationName,
                    message
                ).data!!
            )
        }
    }
}