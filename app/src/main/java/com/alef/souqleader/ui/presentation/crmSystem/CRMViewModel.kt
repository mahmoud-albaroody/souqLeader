package com.alef.souqleader.ui.presentation.crmSystem


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.domain.AddCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CRMViewModel @Inject constructor(
    private val addCommentUseCase: AddCommentUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    var cancellationStatus: StatusResponse? by mutableStateOf(null)
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
//
    fun addComment(comment: String, post_id: String) {
        viewModelScope.launch(job) {
            cancellationStatus = addCommentUseCase.addComment(comment, post_id).data
        }
    }

}