package com.alef.souqleader.ui.presentation.dashboardScreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.domain.GetLeadUseCase
import com.alef.souqleader.domain.NetworkManager
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
class DashboardViewModel @Inject constructor(
    private val getLeadUseCase: GetLeadUseCase,private val networkManager: NetworkManager
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _allLead =
        MutableSharedFlow<Resource<LeadsStatusResponse>>()
    val allLead: MutableSharedFlow<Resource<LeadsStatusResponse>>
        get() = _allLead

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
    fun getLeads() {
        viewModelScope.launch(job) {
            getLeadUseCase.getLeadStatus().catch { }
                .onStart {
                    _allLead.emit(Resource.Loading())
                }.buffer().collect {
                    _allLead.emit(it)
                }
        }
    }
    fun updateBaseUrl(newUrl: String) {
        networkManager.changeBaseUrl(newUrl)
    }
}