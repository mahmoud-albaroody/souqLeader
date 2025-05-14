package com.alef.souqleader.ui.presentation.userDetails


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.LeadDetailsResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.SalesProfileReport
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import com.alef.souqleader.data.remote.dto.UserDateResponse
import com.alef.souqleader.data.remote.dto.UserDetailsResponse
import com.alef.souqleader.domain.GetLeadUseCase
import com.alef.souqleader.domain.LoginUseCase
import com.alef.souqleader.domain.ProfileUseCase
import com.alef.souqleader.domain.SalesProfileReportUseCase
import com.alef.souqleader.domain.SaveMultiUseCase
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
class UserDetailsViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val salesProfileReportUseCase: SalesProfileReportUseCase,
    private val profileUseCase: ProfileUseCase,

    ) : ViewModel() {

    private val job = Job()

    private val _userDetailsState =
        MutableSharedFlow<Resource<UserDetailsResponse>>()
    val userDetailsState: MutableSharedFlow<Resource<UserDetailsResponse>>
        get() = _userDetailsState
    private val _userDate =
        MutableSharedFlow<UserDateResponse>()
    val userDate: MutableSharedFlow<UserDateResponse>
        get() = _userDate
    var salesProfileReport: SalesProfileReport? by mutableStateOf(null)

    var page by mutableIntStateOf(1)
    var activityPage by mutableIntStateOf(1)

    fun userDetails(user: String) {
        viewModelScope.launch(job) {
            loginUseCase.userDetails(user).catch { }
                .onStart {
                    _userDetailsState.emit(Resource.Loading())
                }.buffer().collect {
                    _userDetailsState.emit(it)
                }
        }
    }

    fun getSalesProfileReport(userId:String) {
        viewModelScope.launch(job) {
            salesProfileReport = salesProfileReportUseCase.getSalesProfileReport(userId).data?.data!!
        }
    }
    fun userDate(id: String,page:Int,activityPage:Int) {
        viewModelScope.launch(job) {
            profileUseCase.userDate(id,page,activityPage).data?.let { _userDate.emit(it) }
        }
    }
}