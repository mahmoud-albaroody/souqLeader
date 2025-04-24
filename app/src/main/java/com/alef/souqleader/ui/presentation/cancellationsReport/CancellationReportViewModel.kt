package com.alef.souqleader.ui.presentation.cancellationsReport


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.CancelationReport
import com.alef.souqleader.domain.CancelationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CancellationReportViewModel @Inject constructor(
    private val cancelationUseCase: CancelationUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    var cancellationStatus: CancelationReport? by mutableStateOf(null)
    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }


    //
//
//
//    fun toggleFav(gym: Gym) {
//        val gyms = stateListOfGym.toMutableList()
//        val index = gyms.indexOf(gym)
//        gyms[index] = gyms[index].copy(isFav = !gyms[index].isFav)
//        stateListOfGym = gyms
//    }
//
//,
    fun getCancellationReport(userId:String) {
        viewModelScope.launch(job) {
            cancellationStatus = cancelationUseCase.getCancelationReport(userId).data?.data!!
        }
    }

}