package com.alef.souqleader.ui.presentation.filter


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.domain.FilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeadsFilterViewModel @Inject constructor(
    private val filterUseCase: FilterUseCase
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }
    private val _allLead =
        MutableSharedFlow<ArrayList<Lead>>()
    val allLead: MutableSharedFlow<ArrayList<Lead>>
        get() = _allLead


    fun leadsFilter(filterRequest: FilterRequest) {
        viewModelScope.launch(job) {
            _allLead.emit(filterUseCase.leadsFilter(filterRequest).data?.data!!)
        }
    }

}