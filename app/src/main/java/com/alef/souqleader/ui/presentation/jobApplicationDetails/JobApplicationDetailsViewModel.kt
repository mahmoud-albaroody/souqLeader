package com.alef.souqleader.ui.presentation.jobApplicationDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.JobAppRequest
import com.alef.souqleader.data.remote.dto.JobAppsResponse
import com.alef.souqleader.data.remote.dto.Jobapps
import com.alef.souqleader.data.remote.dto.JopPersion
import com.alef.souqleader.domain.AllJobUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobApplicationDetailsViewModel @Inject constructor(
    private val allJobUseCase: AllJobUseCase

) : ViewModel() {
    private val job = Job()

    private val _jobAppResponse =
        MutableSharedFlow<ArrayList<Jobapps>>()
    val jobAppResponse: MutableSharedFlow<ArrayList<Jobapps>>
        get() = _jobAppResponse


    fun jobApp(jobAppRequest: JobAppRequest) {
        viewModelScope.launch(job) {
            _jobAppResponse.emit(
                allJobUseCase.JobApp(jobAppRequest = jobAppRequest).data?.data!!
            )
        }
    }
}