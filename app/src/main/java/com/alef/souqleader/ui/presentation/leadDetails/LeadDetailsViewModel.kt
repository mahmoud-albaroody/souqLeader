package com.alef.souqleader.ui.presentation.leadDetails


import android.util.Log
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
import com.alef.souqleader.data.remote.dto.LeadDetailsResponse
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
class LeadDetailsViewModel @Inject constructor(
    private val getLeadUseCase: GetLeadUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    private val _leadDetails =
        MutableSharedFlow<Resource<LeadDetailsResponse>>()
    val leadDetails: MutableSharedFlow<Resource<LeadDetailsResponse>>
        get() = _leadDetails


    fun getLeadDetails(id: String) {

        viewModelScope.launch(job) {
            getLeadUseCase.leadDetails(id).catch {
            }
                .onStart {
                    _leadDetails.emit(Resource.Loading())
                }.buffer().collect {
                    _leadDetails.emit(it)
                }
        }
    }


}