package com.alef.souqleader.ui.presentation.channelReport


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.CancelationReport
import com.alef.souqleader.data.remote.dto.ChannelReportResponse
import com.alef.souqleader.data.remote.dto.ChannelResponse
import com.alef.souqleader.domain.CancelationUseCase
import com.alef.souqleader.domain.MeetingReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelReportViewModel @Inject constructor(
    private val meetingReportsUseCase: MeetingReportsUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
//    private val _channelReports =
//        MutableSharedFlow<ChannelReportResponse>()
//    val channelReports: MutableSharedFlow<ChannelReportResponse>
//        get() = _channelReports
    var channelReports: ChannelReportResponse? by mutableStateOf(null)

    private val job = Job()
    fun channelReport() {
        viewModelScope.launch(job) {
            meetingReportsUseCase.channelReport().data?.let { channelReports =it }
        }
    }

}