package com.alef.souqleader.ui.presentation.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.UserDateResponse
import com.alef.souqleader.domain.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _userDate =
        MutableSharedFlow<UserDateResponse>()
    val userDate: MutableSharedFlow<UserDateResponse>
        get() = _userDate


    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun userDate(id: String) {
        viewModelScope.launch(job) {
            profileUseCase.userDate(id, page = 0, activityPage = 0).data?.let { _userDate.emit(it) }
        }
    }

}