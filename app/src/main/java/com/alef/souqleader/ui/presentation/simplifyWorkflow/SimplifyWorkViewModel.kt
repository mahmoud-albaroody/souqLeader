package com.alef.souqleader.ui.presentation.simplifyWorkflow


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.domain.SimplifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimplifyWorkViewModel @Inject constructor(
    private val simplifyUseCase: SimplifyUseCase,
) : ViewModel() {

    //  var loginState: Login? by mutableStateOf(null)

  //  private val _getClientState = MutableState(GetClientResponse())
    //val getClientState: MutableState<GetClientResponse> get() = _getClientState

    var getClientState by mutableStateOf(GetClientResponse())

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("ddd",throwable.printStackTrace().toString())
        throwable.printStackTrace()
    }

    fun getClient(username: String) {
        viewModelScope.launch(job) {
            simplifyUseCase.getClient(username).data?.let {
                getClientState =it
            }
        }
    }

}