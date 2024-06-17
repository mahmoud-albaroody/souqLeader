package com.alef.souqleader.ui.presentation.leadUpdate


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.domain.GetLeadUseCase
import com.alef.souqleader.domain.SaveMultiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeadUpdateViewModel @Inject constructor(
    private val getLeadUseCase: GetLeadUseCase,
    private val saveMultiUseCase: SaveMultiUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private var stateUpdateMulti by mutableStateOf(true)

    val stateListOfLeads: MutableState<ArrayList<Lead>?> = mutableStateOf(null)
    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getLeads(id:String) {
        viewModelScope.launch(job) {
            stateListOfLeads.value = getLeadUseCase.getLeadByStatus(id).data?.data!!
        }
    }
    fun updateMulti(ids:ArrayList<String>) {
        viewModelScope.launch(job) {
            stateUpdateMulti = saveMultiUseCase.updateMulti(ids).data?.data!!
        }
    }
}