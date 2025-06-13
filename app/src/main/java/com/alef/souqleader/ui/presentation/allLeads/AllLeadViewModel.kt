package com.alef.souqleader.ui.presentation.allLeads


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.ForgetPasswordResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.WhatMessageResponse
import com.alef.souqleader.domain.FilterUseCase
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
class AllLeadViewModel @Inject constructor(
    private val getLeadUseCase: GetLeadUseCase,
    private val filterUseCase: FilterUseCase,
    private val networkManager: NetworkManager
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _stateListOfLeads =
        MutableSharedFlow<Resource<LeadsByStatusResponse>>(replay = 1)
    val stateListOfLeads: MutableSharedFlow<Resource<LeadsByStatusResponse>>
        get() = _stateListOfLeads

    var page by mutableIntStateOf(1)

    private val _sendWhatsappMessage =
        MutableSharedFlow<ForgetPasswordResponse>()
    val sendWhatsappMessage:  MutableSharedFlow<ForgetPasswordResponse>
        get() = _sendWhatsappMessage

    private val _getWhatsMessage =
        MutableSharedFlow<WhatMessageResponse>()
    val getWhatsMessage:  MutableSharedFlow<WhatMessageResponse>
        get() = _getWhatsMessage

    private val _getMailMessage =
        MutableSharedFlow<WhatMessageResponse>()
    val getMailMessage:  MutableSharedFlow<WhatMessageResponse>
        get() = _getMailMessage

    private val _stateFilterLeads =
        MutableSharedFlow<Resource<LeadsByStatusResponse>>(replay = 1)
    val stateFilterLeads: MutableSharedFlow<Resource<LeadsByStatusResponse>>
        get() = _stateFilterLeads


    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getLeadByStatus(id: String, page: Int) {
        viewModelScope.launch(job) {
            getLeadUseCase.getLeadByStatus(id, page).catch { }
                .onStart {
                    _stateListOfLeads.emit(Resource.Loading())
                }.buffer().collect {
                    _stateListOfLeads.emit(it)
                }
        }
    }

    fun delayLeads(page: Int) {
        viewModelScope.launch(job) {
            getLeadUseCase.delayLeads(page).catch { }
                .onStart {
                    _stateListOfLeads.emit(Resource.Loading())
                }.buffer().collect {
                    _stateListOfLeads.emit(it)
                }
        }
    }

    fun duplicated(page: Int) {
        viewModelScope.launch(job) {
            getLeadUseCase.duplicated(page).catch { }
                .onStart {
                    _stateListOfLeads.emit(Resource.Loading())
                }.buffer().collect {
                    _stateListOfLeads.emit(it)
                }

        }
    }

    fun updateBaseUrl(newUrl: String) {
        networkManager.changeBaseUrl(newUrl)
    }

    fun leadsFilter(filterRequest: FilterRequest) {
        viewModelScope.launch(job) {
            filterUseCase.leadsFilter(filterRequest).catch {

            }
                .onStart {
                    _stateFilterLeads.emit(Resource.Loading())
                }.buffer().collect {
                    _stateFilterLeads.emit(it)
                }
        }
    }
    fun sendWhatsappMessage(
        message: String, isSaved:Boolean, checkLeads: List<String>
    ) {
        viewModelScope.launch(job) {
            getLeadUseCase.sendWhatsappMessage(
                message,
                isSaved,
                checkLeads,
            ).data?.let {
                _sendWhatsappMessage.emit(it)
            }
        }
    }
    fun sendMail(
        subject: String,body:String
        ,fromEmail:String,isSaved:Boolean,
        isHtml:Boolean, ids:List<Int>
    ) {
        viewModelScope.launch(job) {
            getLeadUseCase.sendMail(subject,body,fromEmail,isSaved,isHtml,ids).data?.let {
                _sendWhatsappMessage.emit(it)
            }
        }
    }
    fun sendSms(
        to: String,message:String
        ,from:String
    ) {
        viewModelScope.launch(job) {
            getLeadUseCase.sendSms(to,message,from).data?.let {
                _sendWhatsappMessage.emit(it)
            }
        }
    }
    fun prevMessages() {
        viewModelScope.launch(job) {
            getLeadUseCase.prevMessages().data?.let {
                _getWhatsMessage.emit(it)
            }
        }
    }
    fun prevMails() {
        viewModelScope.launch(job) {
            getLeadUseCase.prevMails().data?.let {
                _getMailMessage.emit(it)
            }
        }
    }

}