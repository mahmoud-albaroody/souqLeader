package com.alef.souqleader.ui.presentation.dashboardScreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.domain.GetLeadUseCase
import com.alef.souqleader.domain.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getLeadUseCase: GetLeadUseCase,private val networkManager: NetworkManager
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    var stateListOfLeads by mutableStateOf(emptyList<AllLeadStatus>())

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
            stateListOfLeads = getLeadUseCase.getLeadStatus().data?.data!!
        }
    }
    fun updateBaseUrl(newUrl: String) {
        networkManager.changeBaseUrl(newUrl)
    }
}