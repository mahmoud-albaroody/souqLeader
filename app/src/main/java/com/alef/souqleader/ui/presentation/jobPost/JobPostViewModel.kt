package com.alef.souqleader.ui.presentation.jobPost

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.UsersItem
import com.alef.souqleader.data.remote.dto.AllJobResponse
import com.alef.souqleader.data.remote.dto.JopPersion
import com.alef.souqleader.domain.AllJobUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobPostViewModel @Inject constructor(
    private val allJobUseCase: AllJobUseCase
) : ViewModel() {

    private val _allJob =
        MutableSharedFlow< Resource<AllJobResponse>>()
    val allJob: MutableSharedFlow<Resource<AllJobResponse>>
        get() = _allJob
    var page by mutableIntStateOf(1)
    private val job = Job()

    fun allJob(pageNumber:Int) {
        viewModelScope.launch(job) {
            allJobUseCase.allJob(pageNumber = pageNumber).catch {
                Log.e("zzzzzzz",it.message.toString())
            }
                .onStart {
                    _allJob.emit(Resource.Loading())
                }.buffer().collect {
                    _allJob.emit(it)
                }
        }
    }


}