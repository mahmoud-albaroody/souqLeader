package com.alef.souqleader.ui.presentation.leadUpdate


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import com.alef.souqleader.domain.GetLeadUseCase
import com.alef.souqleader.domain.SaveMultiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeadUpdateViewModel @Inject constructor(
    private val getLeadUseCase: GetLeadUseCase,
    private val saveMultiUseCase: SaveMultiUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val stateListOfLeads: MutableState<ArrayList<Lead>?> = mutableStateOf(null)
    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    private val _allLead =
        MutableSharedFlow<ArrayList<AllLeadStatus>>()
    val allLead: MutableSharedFlow<ArrayList<AllLeadStatus>>
        get() = _allLead


    private val _updateLead =
        MutableSharedFlow<UpdateLeadResponse>()
    val updateLead: MutableSharedFlow<UpdateLeadResponse>
        get() = _updateLead

    private val _cancelationReason =
        MutableSharedFlow<CancelationReasonResponse>()
    val cancelationReason: MutableSharedFlow<CancelationReasonResponse>
        get() = _cancelationReason


    fun getLeads() {
        viewModelScope.launch(job) {
            _allLead.emit(getLeadUseCase.getLeadStatus().data?.data!!)
        }
    }

    fun updateMulti(
        ids: Array<String>, status: String?,
        note: String?,
        reminderTime: String?,
        cancelReason: String?
    ) {
        viewModelScope.launch(job) {
            _updateLead.emit(
                saveMultiUseCase.updateMulti(
                    ids,
                    status,
                    note,
                    reminderTime,
                    cancelReason
                ).data!!
            )
        }
    }

    fun updateLead(
        id: String, status: String?,
        note: String?,
        reminderTime: String?,
        cancelReason: String?
    ) {
        viewModelScope.launch(job) {
            getLeadUseCase.updateLead(
                id,
                status,
                note,
                reminderTime,
                cancelReason
            ).data?.let { _updateLead.emit(it) }
        }
    }

    fun cancelationReason() {
        viewModelScope.launch(job) {
            getLeadUseCase.cancelationReason().data?.let { _cancelationReason.emit(it) }
        }
    }
}