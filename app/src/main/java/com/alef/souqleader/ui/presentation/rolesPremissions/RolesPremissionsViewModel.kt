package com.alef.souqleader.ui.presentation.rolesPremissions


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissions
import com.alef.souqleader.data.remote.dto.Plan
import com.alef.souqleader.data.remote.dto.SalesProfileReport
import com.alef.souqleader.data.remote.dto.SalesProfileReportResponse
import com.alef.souqleader.domain.AllRolesAndAllPermissionsUseCase
import com.alef.souqleader.domain.SalesProfileReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RolesPremissionsViewModel @Inject constructor(
    private val allPermissionsUseCase: AllRolesAndAllPermissionsUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var allRoles by mutableStateOf(emptyList<AllRolesAndAllPermissions>())
    var allPermissions by mutableStateOf(emptyList<AllRolesAndAllPermissions>())
    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    fun getAllRoles() {
        viewModelScope.launch(job) {
            allRoles = allPermissionsUseCase.getAllRoles().data?.data!!
        }
    }

    fun getAllPermissions() {
        viewModelScope.launch(job) {
            allPermissions = allPermissionsUseCase.getAllPermissions().data?.data!!
        }
    }
}