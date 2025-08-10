package com.alef.souqleader.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var showDialog by mutableStateOf(false)

    var showLoader by mutableStateOf(false)
    var showFilter by mutableStateOf(false)
    var showFilterIcon by mutableStateOf(false)
    var showShareIcon by mutableStateOf(false)
    var selectedLead by mutableStateOf("")
    var showSendContact by mutableStateOf(false)
    var showMenuContact by mutableStateOf(false)
    var isCall by mutableStateOf(true)
    var isGesturesEnabled by mutableStateOf(true)


    var value1: String? by mutableStateOf(null)
    var value2: String? by mutableStateOf(null)

    private val _onShareClick =
        MutableSharedFlow<Boolean>()
    val onShareClick: MutableSharedFlow<Boolean>
        get() = _onShareClick

    private val _onSendMailClick =
        MutableSharedFlow<Boolean>()
    val onSendMailClick: MutableSharedFlow<Boolean>
        get() = _onSendMailClick

    private val _onSmsMailClick =
        MutableSharedFlow<Boolean>()
    val onSmsMailClick: MutableSharedFlow<Boolean>
        get() = _onSmsMailClick

    private val _onWhatsClick =
        MutableSharedFlow<Boolean>()
    val onWhatsClick: MutableSharedFlow<Boolean>
        get() = _onWhatsClick


    private val _onSelectAllClick =
        MutableSharedFlow<Boolean>()
    val onSelectAllClick: MutableSharedFlow<Boolean>
        get() = _onSelectAllClick

}