package com.alef.souqleader.ui.presentation.projects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.Property
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.domain.ProjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsScreenViewModel @Inject constructor(
    private val projectsUseCase: ProjectsUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _stateListOfProjects =
        MutableSharedFlow<Resource<ProjectResponse>>(replay = 1)
    val stateListOfProjects: MutableSharedFlow<Resource<ProjectResponse>>
        get() = _stateListOfProjects

    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }
    var page by mutableIntStateOf(1)
    fun getProject(page: Int) {
        viewModelScope.launch(job) {
            stateListOfProjects.emit(projectsUseCase.project(page))
        }
    }

//    fun getProperty(page: Int) {
//        viewModelScope.launch(job) {
//            stateListOfProperty = projectsUseCase.property().data?.data!!
//        }
//    }
}
