package com.alef.souqleader.ui.presentation.property

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.data.remote.dto.Property
import com.alef.souqleader.domain.ProjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyScreenViewModel @Inject constructor(
    private val projectsUseCase: ProjectsUseCase,
//    @IODispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {



    var stateListOfProperty by mutableStateOf(emptyList<Property>())


    private val job = Job()
    private val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }


    fun getProperty() {
        viewModelScope.launch(job) {
            stateListOfProperty = projectsUseCase.property().data?.data!!
        }
    }
}
