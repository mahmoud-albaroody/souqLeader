package com.alef.souqleader.ui.presentation.jobApplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.JobAppRequest
import com.alef.souqleader.data.remote.dto.Jobapps
import com.alef.souqleader.domain.AllJobUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobApplicationViewModel @Inject constructor(
    private val allJobUseCase: AllJobUseCase
) : ViewModel() {
    private val _jobAppResponse =
        MutableSharedFlow<ArrayList<Jobapps>>()
    val jobAppResponse: MutableSharedFlow<ArrayList<Jobapps>>
        get() = _jobAppResponse

    private val job = Job()
    fun jobApp(jobAppRequest: JobAppRequest) {
        viewModelScope.launch(job) {
            _jobAppResponse.emit(
                allJobUseCase.JobApp(jobAppRequest = jobAppRequest).data?.data!!
            )
        }
    }
}