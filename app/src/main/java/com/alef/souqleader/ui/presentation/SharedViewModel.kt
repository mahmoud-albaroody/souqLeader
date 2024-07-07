package com.alef.souqleader.ui.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {

    private val _nameState = MutableStateFlow(String())
    val nameState: StateFlow<String> get() = _nameState
    private val _photoState = MutableStateFlow(String())
    val photoState: StateFlow<String> get() = _photoState

    private val _salesNameState = mutableStateOf("Initial State")
    val salesNameState: State<String> = _salesNameState

    fun updateSalesNameState(newState: String) {
        _salesNameState.value = newState
    }

    fun updatePhotoState(newState: String) {
        _photoState.value = newState
    }

    fun updateNameState(newState: String) {
        _nameState.value = newState
    }
}