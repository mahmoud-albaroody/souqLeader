package com.alef.souqleader.ui.presentation.callLog


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import com.alef.souqleader.domain.GetLeadUseCase
import com.alef.souqleader.domain.SaveMultiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallLogViewModel @Inject constructor(
    private val getLeadUseCase: GetLeadUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val job = Job()


    private val _quickCreate =
        MutableSharedFlow<LeadsStatusResponse>()
    val quickCreate: MutableSharedFlow<LeadsStatusResponse>
        get() = _quickCreate



    fun quickCreate(
        name: String, phone: String?,
        duration: String?,
        note: String?
    ) {
        viewModelScope.launch(job) {
            getLeadUseCase.quickCreate(
                name,
                phone,
                duration,
                note
            ).data?.let {
                _quickCreate.emit(it)
            }
        }
    }


}