package com.alef.souqleader.ui.presentation.projectFilterResult

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.ProjectFilterRequest
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.domain.FilterUseCase
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
class ProjectFilterResultViewModel @Inject constructor(
    private val filterUseCase: FilterUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _projectFilter =
        MutableSharedFlow<Resource<ProjectResponse>>(replay = 1)
    val projectFilter: MutableSharedFlow<Resource<ProjectResponse>>
        get() = _projectFilter

    private val _propertyFilter =
        MutableSharedFlow<Resource<PropertyResponse>>(replay = 1)
    val propertyFilter: MutableSharedFlow<Resource<PropertyResponse>>
        get() = _propertyFilter

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }
    var page by mutableIntStateOf(1)
    fun projectFilter(projectFilterRequest: ProjectFilterRequest) {

        viewModelScope.launch(job) {
            filterUseCase.projectFilter(page,projectFilterRequest).catch {

            }
                .onStart {
                    _projectFilter.emit(Resource.Loading())
                }.buffer().collect {
                    _projectFilter.emit(it)
                }
        }
    }


    fun propertyFilter(projectFilterRequest: ProjectFilterRequest) {
        viewModelScope.launch(job) {
            filterUseCase.propertyFilter(page,projectFilterRequest).catch {

            }
                .onStart {
                    _propertyFilter.emit(Resource.Loading())
                }.buffer().collect {
                    _propertyFilter.emit(it)
                }
        }
    }

}
