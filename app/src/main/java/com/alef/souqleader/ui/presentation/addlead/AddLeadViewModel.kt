package com.alef.souqleader.ui.presentation.addlead


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AddLeadResponse
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.CampaignResponse
import com.alef.souqleader.data.remote.dto.ChannelResponse
import com.alef.souqleader.data.remote.dto.CommunicationWayResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.MarketerResponse
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.SalesResponse
import com.alef.souqleader.domain.AddLeadUseCase
import com.alef.souqleader.domain.GetLeadUseCase
import com.alef.souqleader.domain.model.AddLead
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
class AddLeadViewModel @Inject constructor(
    private val addLeadUseCase: AddLeadUseCase, private val getLeadUseCase: GetLeadUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _channel =
        MutableSharedFlow<ChannelResponse>()
    val channel: MutableSharedFlow<ChannelResponse>
        get() = _channel

    private val _addLead =
        MutableSharedFlow<Resource<AddLeadResponse>>()
    val addLead: MutableSharedFlow<Resource<AddLeadResponse>>
        get() = _addLead

    private val _campaignResponse =
        MutableSharedFlow<CampaignResponse>()
    val campaignResponse: MutableSharedFlow<CampaignResponse>
        get() = _campaignResponse
    private val _sales =
        MutableSharedFlow<SalesResponse>()
    val sales: MutableSharedFlow<SalesResponse>
        get() = _sales
    private val _marketer =
        MutableSharedFlow<MarketerResponse>()
    val marketer: MutableSharedFlow<MarketerResponse>
        get() = _marketer
    private val _communicationWay =
        MutableSharedFlow<CommunicationWayResponse>()
    val communicationWay: MutableSharedFlow<CommunicationWayResponse>
        get() = _communicationWay

    private val _project =
        MutableSharedFlow<ProjectResponse>()
    val project: MutableSharedFlow<ProjectResponse>
        get() = _project


    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }
    private val _allLead =
        MutableSharedFlow<Resource<LeadsStatusResponse>>()
    val allLead: MutableSharedFlow<Resource<LeadsStatusResponse>>
        get() = _allLead

    fun communicationWay() {
        viewModelScope.launch(job) {
            _communicationWay.emit(addLeadUseCase.communicationWay().data!!)
        }
    }

    fun allMarketer() {
        viewModelScope.launch(job) {
            _marketer.emit(addLeadUseCase.allMarketer().data!!)
        }
    }

    fun allSales() {
        viewModelScope.launch(job) {
            _sales.emit(addLeadUseCase.allSales().data!!)
        }
    }

    fun campaign() {
        viewModelScope.launch(job) {
            _campaignResponse.emit(addLeadUseCase.campaign().data!!)
        }
    }

    fun channel() {
        viewModelScope.launch(job) {
            _channel.emit(addLeadUseCase.channel().data!!)
        }
    }

    fun lead(addLead: AddLead) {
        viewModelScope.launch(job) {
            addLeadUseCase.lead(addLead).catch { }
                .onStart {
                    _addLead.emit(Resource.Loading())
                }.buffer().collect {
                    _addLead.emit(it)
                }
        }
    }

    fun getProject(page:Int) {
        viewModelScope.launch(job) {
            _project.emit(addLeadUseCase.project(page = page).data!!)
        }
    }

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
}