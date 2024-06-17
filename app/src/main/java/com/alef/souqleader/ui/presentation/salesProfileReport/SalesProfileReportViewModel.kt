package com.alef.souqleader.ui.presentation.salesProfileReport


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.SalesProfileReport
import com.alef.souqleader.data.remote.dto.SalesProfileReportResponse
import com.alef.souqleader.domain.SalesProfileReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesProfileReportViewModel @Inject constructor(
    private val salesProfileReportUseCase: SalesProfileReportUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

     var salesProfileReport: SalesProfileReport? by mutableStateOf(null)
    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getSalesProfileReport() {
        viewModelScope.launch(job) {
            salesProfileReport = salesProfileReportUseCase.getSalesProfileReport().data?.data!!
        }
    }

}