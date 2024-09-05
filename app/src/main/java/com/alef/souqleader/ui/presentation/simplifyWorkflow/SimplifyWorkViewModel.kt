package com.alef.souqleader.ui.presentation.simplifyWorkflow


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.domain.SimplifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimplifyWorkViewModel @Inject constructor(
    private val simplifyUseCase: SimplifyUseCase,
) : ViewModel() {


    private val _getClientState =
        MutableSharedFlow<Resource<GetClientResponse>>()
    val getClientState: MutableSharedFlow<Resource<GetClientResponse>>
        get() = _getClientState
    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getClient(username: String) {
        viewModelScope.launch(job) {
            simplifyUseCase.getClient(username).catch { }
                .onStart {
                    _getClientState.emit(Resource.Loading())
                }.buffer().collect {
                    _getClientState.emit(it)
                }
        }
    }

}