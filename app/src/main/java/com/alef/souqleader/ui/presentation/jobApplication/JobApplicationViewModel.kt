package com.alef.souqleader.ui.presentation.jobApplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AddressFilter
import com.alef.souqleader.data.remote.dto.BasicData
import com.alef.souqleader.data.remote.dto.JobAppRequest
import com.alef.souqleader.data.remote.dto.Jobapps
import com.alef.souqleader.data.remote.dto.UnlockResponse
import com.alef.souqleader.domain.AllJobUseCase
import com.alef.souqleader.domain.FilterUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobApplicationViewModel @Inject constructor(
    private val allJobUseCase: AllJobUseCase,
    private val filterUseCase: FilterUseCase,
) : ViewModel() {

    private val _jobAppResponse =
        MutableSharedFlow<ArrayList<Jobapps>>()
    val jobAppResponse: MutableSharedFlow<ArrayList<Jobapps>>
        get() = _jobAppResponse

    private val _unLockResponse =
        MutableSharedFlow<UnlockResponse>()
    val unLockResponse: MutableSharedFlow<UnlockResponse>
        get() = _unLockResponse

    private val _addressFilter =
        MutableSharedFlow<AddressFilter>()
    val addressFilter: MutableSharedFlow<AddressFilter>
        get() = _addressFilter

    private val _basicDataResponse =
        MutableSharedFlow<BasicData>()
    val basicDataResponse: MutableSharedFlow<BasicData>
        get() = _basicDataResponse




    private val job = Job()
    fun jobApp(jobAppRequest: JobAppRequest) {
        viewModelScope.launch(job) {
            _jobAppResponse.emit(
                allJobUseCase.JobApp(jobAppRequest = jobAppRequest).data?.data!!
            )
        }
    }
    fun unlock(id: String) {
        viewModelScope.launch(job) {
            _unLockResponse.emit(
                allJobUseCase.unlock(id = id).data!!
            )
        }
    }

    fun addressFilter() {
        viewModelScope.launch(job) {
            _addressFilter.emit(
                filterUseCase.addressFilter().data?.data!!
            )
        }
    }

    fun basicData() {
        viewModelScope.launch(job) {
            _basicDataResponse.emit(
                filterUseCase.basicData().data?.data!!
            )
        }

    }
}