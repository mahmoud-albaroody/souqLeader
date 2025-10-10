package com.alef.souqleader.ui.presentation.dashboard1



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.DashboardResponse
import com.alef.souqleader.domain.DashboardUseCase
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
class DashboardViewModel1 @Inject constructor(
    private val dashboardUseCase: DashboardUseCase
) : ViewModel() {
    private val _dashboard =
        MutableSharedFlow<Resource<DashboardResponse>>()
    val dashboard: MutableSharedFlow<Resource<DashboardResponse>>
        get() = _dashboard

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }


    fun getDashboard() {
        viewModelScope.launch(job) {
            dashboardUseCase.dashboard().catch {
            }
                .onStart {
                    _dashboard.emit(Resource.Loading())
                }.buffer().collect {
                    _dashboard.emit(it)
                }
        }
    }

}