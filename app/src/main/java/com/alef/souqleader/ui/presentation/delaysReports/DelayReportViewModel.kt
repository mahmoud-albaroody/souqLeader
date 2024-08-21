package com.alef.souqleader.ui.presentation.delaysReports


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.DelayReport
import com.alef.souqleader.data.remote.dto.MeetingReport
import com.alef.souqleader.domain.MeetingReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DelayReportViewModel @Inject constructor(
    private val meetingReportsUseCase: MeetingReportsUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

     var delayReport: ArrayList<DelayReport>? by mutableStateOf(null)

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun delayReport() {
        viewModelScope.launch(job) {
            delayReport = meetingReportsUseCase.delayReport().data?.data!!
        }
    }


}