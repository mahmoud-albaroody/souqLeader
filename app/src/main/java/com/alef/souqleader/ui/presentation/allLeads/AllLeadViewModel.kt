package com.alef.souqleader.ui.presentation.allLeads


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.domain.FilterUseCase
import com.alef.souqleader.domain.GetLeadUseCase
import com.alef.souqleader.domain.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllLeadViewModel @Inject constructor(
    private val getLeadUseCase: GetLeadUseCase, private val filterUseCase: FilterUseCase, private val networkManager: NetworkManager
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _stateListOfLeads =
        MutableSharedFlow<ArrayList<Lead>>()
    val stateListOfLeads: MutableSharedFlow<ArrayList<Lead>>
        get() = _stateListOfLeads

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getLeadByStatus(id: String) {
        viewModelScope.launch(job) {
            _stateListOfLeads.emit(getLeadUseCase.getLeadByStatus(id).data?.data!!)
        }
    }

    fun delayLeads() {
        viewModelScope.launch(job) {
            _stateListOfLeads.emit(getLeadUseCase.delayLeads().data?.data!!)
        }
    }

    fun duplicated() {
        viewModelScope.launch(job) {
            _stateListOfLeads.emit(getLeadUseCase.duplicated().data?.data!!)
        }
    }

    fun updateBaseUrl(newUrl: String) {
        networkManager.changeBaseUrl(newUrl)
    }
    fun leadsFilter(filterRequest: FilterRequest) {
        viewModelScope.launch(job) {
            _stateListOfLeads.emit(filterUseCase.leadsFilter(filterRequest).data?.data!!)
        }
    }

}