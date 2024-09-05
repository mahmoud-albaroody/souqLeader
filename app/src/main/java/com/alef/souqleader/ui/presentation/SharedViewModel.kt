package com.alef.souqleader.ui.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.ChannelResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.domain.AddLeadUseCase
import com.alef.souqleader.domain.GetLeadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getLeadUseCase: GetLeadUseCase
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    private val _allLead =
        MutableSharedFlow<Resource<LeadsStatusResponse>>()
    val allLead: MutableSharedFlow<Resource<LeadsStatusResponse>>
        get() = _allLead

    private val _nameState = MutableStateFlow(String())
    val nameState: StateFlow<String> get() = _nameState
    private val _photoState = MutableStateFlow(String())
    val photoState: StateFlow<String> get() = _photoState

    private val _salesNameState = mutableStateOf("Initial State")
    val salesNameState: State<String> = _salesNameState

    fun updateSalesNameState(newState: String) {
        _salesNameState.value = newState
    }

    fun updatePhotoState(newState: String) {
        _photoState.value = newState
    }

    fun updateNameState(newState: String) {
        _nameState.value = newState
    }

    fun getLeads() {
        viewModelScope.launch(job) {
            getLeadUseCase.getLeadStatus().catch { }
                .onStart {
                    _allLead.emit(Resource.Loading())
                }.buffer().collect {
                    _allLead.emit(it)
                }
        }
    }

}