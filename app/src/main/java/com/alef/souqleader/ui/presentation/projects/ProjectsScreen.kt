package com.alef.souqleader.ui.presentation.projects

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.Info
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen

import com.alef.souqleader.ui.presentation.salesProfileReport.VerticalDivider

import kotlinx.coroutines.launch

@Composable
fun ProjectsScreen(navController: NavController, modifier: Modifier, mainViewModel: MainViewModel) {
    val viewModel: ProjectsScreenViewModel = hiltViewModel()
    var info by remember { mutableStateOf(Info()) }
    val projects = remember { mutableStateListOf<Project>() }
    var projectResponse by remember { mutableStateOf(ProjectResponse()) }
    var isSort by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        viewModel.getProject(viewModel.page)

        viewModel.viewModelScope.launch {
            viewModel.stateListOfProjects.collect {
                if (it is Resource.Success) {

                    it.data?.let {
                        projectResponse = it
                        if (it.info != null)
                            info = it.info
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
        viewModel.viewModelScope.launch {
            viewModel.stateListOfProjects.collect {
                if (it is Resource.Success) {

                    it.data?.let {
                        projectResponse = it
                        if (it.info != null)
                            info = it.info
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
    }


    Projects(projects, true, info, viewModel.page, onItemClick = {
        viewModel.page = 1
        projects.clear()
        val projectJson = it.toJson()
        navController.navigate(
            Screen.ProjectsDetailsScreen
                .route.plus("?" + Screen.ProjectsDetailsScreen.objectName + "=${projectJson}")
        )
    },
        onPage = {
            if (isSort) {
                if (projects.isNotEmpty()) {
                    viewModel.projectSort(++viewModel.page)
                }
            } else {
                if (projects.isNotEmpty()) {
                    viewModel.getProject(++viewModel.page)
                }
            }
        }, onMapClick = {

            val projectJson = projectResponse.toJson()
            navController.navigate(
                Screen.MapScreen.route.plus(
                    "?" + Screen.MapScreen.objectName + "=${projectJson}"
                )
            )
        }, onSortClick = {
            isSort = true
            viewModel.page = 1
            viewModel.projectSort(viewModel.page)
        }, onFilterClick = {
            navController.navigate(Screen.InventoryFilterScreen.route.plus("/${"Product"}"))
        })
//    PopupBox(150f, 150f, viewPopup, onClickOutside = {
//
//    }, content = {
//        Text(text = "sdfdsfdsfds")
//    })
}

@Composable
fun Projects(
    projects: SnapshotStateList<Project>, showFilter: Boolean,
    info: Info, page: Int, onPage: () -> Unit,
    onItemClick: (Project) -> Unit,
    onMapClick: () -> Unit,
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    Column {
        if (showFilter)
            Filter(onMapClick = {
                onMapClick()
            }, onSortClick = {
                onSortClick()
            }, onFilterClick = {
                onFilterClick()
            })
        LazyColumn(Modifier.padding(top = 8.dp)) {
            items(projects) { item ->
                ProjectsItem(item) { project ->
                    onItemClick(project)
                }
            }
            if (info.pages != null)
                if (info.pages > page && projects.size > 10) {
                    item {
                        onPage()
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(16.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                    }
                }
        }
    }
}


@Composable
fun Filter(
    onMapClick: () -> Unit, onSortClick: () -> Unit, onFilterClick: () -> Unit
) {
    Card(
        Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {

            Row(
                Modifier
                    .clickable {
                        onFilterClick()
                    }
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.filter_icon),
                    contentDescription = "",
                )
                Text(
                    text = stringResource(id = R.string.filter),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.blue2)
                    )
                )
            }

            VerticalDivider()
            Row(
                Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f)
                    .clickable {
                        onSortClick()
                    },
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.sort_icon),
                    contentDescription = "",
                    Modifier


                )
                Text(
                    text = stringResource(R.string.sort_by),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.blue2)
                    )
                )
            }
            VerticalDivider()
            Row(
                Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f)
                    .clickable {
                        onMapClick()
                    },
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.map_icon),
                    contentDescription = "",

                    )
                Text(
                    text = stringResource(R.string.map),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.blue2)
                    )
                )
            }
        }
    }

}

@Composable
fun ProjectsItem(project: Project, onProjectClick: (Project) -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3.2f)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onProjectClick.invoke(project)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
                .weight(4f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(3.4f)
            ) {
                if (project.images?.isNotEmpty() == true) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            AccountData.BASE_URL + project.images[0].file
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(percent = 10))
                    )
                }else{
                    Image(
                        painter = rememberAsyncImagePainter(
                            AccountData.BASE_URL + "images/default.jpg"
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(percent = 10))
                    )
                }
            }

            Row(
                Modifier
                    .fillMaxSize()
                    .weight(0.9f),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    project.title?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                color = colorResource(id = R.color.blue),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    project.region_name?.let {
                        Text(
                            text = it, style = TextStyle(
                                fontSize = 13.sp,
                            )
                        )
                    }
                }
                project.start_price?.let {
                    Text(
                        text = it, style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}