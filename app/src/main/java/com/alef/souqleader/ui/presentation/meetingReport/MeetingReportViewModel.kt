package com.alef.souqleader.ui.presentation.meetingReport


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.ChannelResponse
import com.alef.souqleader.data.remote.dto.MeetingReport
import com.alef.souqleader.data.remote.dto.Plan
import com.alef.souqleader.data.remote.dto.SalesProfileReport
import com.alef.souqleader.domain.MeetingReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingReportViewModel @Inject constructor(
    private val meetingReportsUseCase: MeetingReportsUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

     var meetingReports: MeetingReport? by mutableStateOf(null)

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getMeetingReport() {
        viewModelScope.launch(job) {
            meetingReports = meetingReportsUseCase.getMeetingReport().data?.data!!
        }
    }


}