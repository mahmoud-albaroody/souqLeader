package com.alef.souqleader.ui.presentation.property

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.domain.ProjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyScreenViewModel @Inject constructor(
    private val projectsUseCase: ProjectsUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _stateListOfProperty =
        MutableSharedFlow<Resource<PropertyResponse>>(replay = 1)
    val stateListOfProperty: MutableSharedFlow<Resource<PropertyResponse>>
        get() = _stateListOfProperty
    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    var page by mutableIntStateOf(1)

    fun getProperty(page: Int) {
        viewModelScope.launch(job) {
            _stateListOfProperty.emit(projectsUseCase.property(page))
        }
    }

    fun propertySort(page: Int) {
        viewModelScope.launch(job) {
            viewModelScope.launch(job) {
                projectsUseCase.propertySort(page).collect {
                    _stateListOfProperty.emit(it)
                }
            }
        }
    }
}
