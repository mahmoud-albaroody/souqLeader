package com.alef.souqleader.ui.presentation.jobPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.UsersItem
import com.alef.souqleader.data.remote.dto.JopPersion
import com.alef.souqleader.domain.AllJobUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobPostViewModel @Inject constructor(
    private val allJobUseCase: AllJobUseCase
) : ViewModel() {

    private val _allJob =
        MutableSharedFlow<ArrayList<JopPersion>>()
    val allJob: MutableSharedFlow<ArrayList<JopPersion>>
        get() = _allJob

    private val job = Job()

    fun allJob() {
        viewModelScope.launch(job) {
            _allJob.emit(allJobUseCase.allJob().data?.data!!)
        }
    }


}