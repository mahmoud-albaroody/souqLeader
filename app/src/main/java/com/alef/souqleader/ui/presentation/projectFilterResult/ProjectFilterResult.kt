package com.alef.souqleader.ui.presentation.projectFilterResult


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.Info
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.data.remote.dto.ProjectFilterRequest
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyObject
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.projects.Projects
import com.alef.souqleader.ui.presentation.projects.ProjectsItem
import com.alef.souqleader.ui.presentation.projects.ProjectsScreenViewModel
import com.alef.souqleader.ui.presentation.property.Property
import com.alef.souqleader.ui.presentation.property.PropertyItem
import com.alef.souqleader.ui.presentation.property.PropertyScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ProductFilterResultScreen(
    navController: NavController, mainViewModel: MainViewModel,
    projectFilterRequest: ProjectFilterRequest?
) {
    var info by remember { mutableStateOf(Info()) }
    val viewModel: ProjectFilterResultViewModel = hiltViewModel()
    val viewPropertyModel: PropertyScreenViewModel = hiltViewModel()
    val viewProjectModel: ProjectsScreenViewModel = hiltViewModel()
    val projects = remember { mutableStateListOf<Project>() }
    var projectResponse by remember { mutableStateOf(ProjectResponse()) }
    var loadMore by remember { mutableStateOf(false) }
    val properties = remember { mutableStateListOf<PropertyObject>() }
    var propertyResponse by remember { mutableStateOf(PropertyResponse()) }


    LaunchedEffect(key1 = true) {
        if (projectFilterRequest?.type == "Product") {

            projectFilterRequest.let { viewModel.projectFilter(it) }

            viewModel.viewModelScope.launch {
                viewModel.projectFilter.collect {
                    if (it is Resource.Success) {

                        it.data?.let {
                            projectResponse = it

                            if (it.info != null) {
                                info = it.info
                            }
                            if (viewModel.page == 1) {
                                projects.clear()
                            }
                            it.data?.let { it1 -> projects.addAll(it1) }

                        }
                        mainViewModel.showLoader = false
                    } else if (it is Resource.Loading) {
                        if (viewModel.page == 1)
                            mainViewModel.showLoader = true
                    } else if (it is Resource.DataError) {
                        mainViewModel.showLoader = false
                    }
                }
            }


        } else {
            projectFilterRequest?.let { viewModel.propertyFilter(it) }
            viewModel.viewModelScope.launch {
                viewModel.propertyFilter.collect {
                    if (it is Resource.Success) {
                        loadMore = true
                        it.data?.let {
                            propertyResponse = it
                            if (it.info != null)
                                info = it.info!!
                            if (viewModel.page == 1) {
                                projects.clear()
                            }
                            it.data?.let { it1 -> properties.addAll(it1) }

                        }
                        mainViewModel.showLoader = false
                    } else if (it is Resource.Loading) {
                        if (viewModel.page == 1)
                            mainViewModel.showLoader = true
                    } else if (it is Resource.DataError) {
                        mainViewModel.showLoader = false
                    }
                }
            }
        }
    }
    if (projectFilterRequest?.type == "Product") {
        Projects(projects, false, info, viewModel.page, onItemClick = {
            viewModel.page = 1
            projects.clear()
            val projectJson = it.toJson()
            navController.navigate(
                Screen.ProjectsDetailsScreen
                    .route.plus("?" + Screen.ProjectsDetailsScreen.objectName + "=${projectJson}")
            )
        },
            onPage = {
                viewModel.viewModelScope.launch {
                    delay(2000)
                    loadMore = false
                    projectFilterRequest.let {
                        viewModel.page++
                        viewModel.projectFilter(it)
                    }
                }

            }, onMapClick = {

            }, onSortClick = {

            }, onFilterClick = {

            })
    } else {
        Property(propertyResponse, properties, info,
            viewPropertyModel, navController,
            false, true, loadMore, viewModel, onPaging = {
                viewModel.viewModelScope.launch {
                    delay(2000)
                    loadMore = false
                    viewModel.page++
                    projectFilterRequest?.let { viewModel.propertyFilter(it) }
                }
            })
    }
}
