package com.alef.souqleader.ui.presentation.property

import android.util.Log
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
import com.alef.souqleader.data.remote.dto.PropertyObject
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.projectFilterResult.ProjectFilterResultViewModel
import com.alef.souqleader.ui.presentation.projects.Filter
import kotlinx.coroutines.launch


@Composable
fun PropertyScreen(navController: NavController, modifier: Modifier, mainViewModel: MainViewModel) {
    val viewModel: PropertyScreenViewModel = hiltViewModel()
    var info by remember { mutableStateOf(Info()) }
    val properties = remember { mutableStateListOf(PropertyObject()) }
    var propertyResponse by remember { mutableStateOf(PropertyResponse()) }

    LaunchedEffect(key1 = true) {

        viewModel.getProperty(viewModel.page)
        viewModel.viewModelScope.launch {
            viewModel.stateListOfProperty.collect {
                if (it is Resource.Success) {
                    it.data?.let {
                        propertyResponse = it
                        if (it.info != null)
                            info = it.info!!
                        if (viewModel.page == 1) {
                            properties.clear()
                        }
                        it.data?.let { it1 -> properties.addAll(it1) }
                    }
                    mainViewModel.showLoader = false
                } else if (it is Resource.Loading) {
                    if (viewModel.page == 1) {
                        mainViewModel.showLoader = true
                    }
                } else if (it is Resource.DataError) {
                    mainViewModel.showLoader = false
                }
            }
        }
    }

    Property(
        propertyResponse, properties, info, viewModel,
        navController, true,   false, false,onPaging = {}
    )

}

@Composable
fun Property(
    propertyResponse: PropertyResponse,
    properties: SnapshotStateList<PropertyObject>,
    info: Info,
    viewModel: PropertyScreenViewModel,
    navController: NavController, showFilter: Boolean,
    isFilter:Boolean,
    loadMore:Boolean,
    projectFilterResultViewModel: ProjectFilterResultViewModel?=null,
    onPaging:()->Unit
) {
    var isSort by remember { mutableStateOf(false) }
    Column {
        if (showFilter)
            Filter(onMapClick = {
                val projectJson = propertyResponse.toJson()
                Screen.MapScreen.title = "pro"
                navController.navigate(
                    Screen.MapScreen.route.plus(
                        "?" + Screen.MapScreen.objectName + "=${projectJson}"
                    )
                )
            }, onSortClick = {
                isSort = true
                viewModel.page = 1
                viewModel.propertySort(page = viewModel.page)
            }, onFilterClick = {
                navController.navigate(Screen.InventoryFilterScreen.route.plus("/${"Property"}"))
            })
        LazyColumn(Modifier.padding(top = 8.dp)) {
            items(properties) {
                PropertyItem(it) { property ->

                    val propertyJson = property.toJson()
                    navController.navigate(
                        Screen.PropertyDetailsScreen.route
                            .plus("?" + Screen.PropertyDetailsScreen.objectName + "=${propertyJson}")
                    )
                    viewModel.page = 1

                }
            }
            if (isFilter) {
                if (info.pages != null  && loadMore)
                    if (info.pages >= projectFilterResultViewModel!!.page && properties.size > 10) {
                        item {
                            onPaging()
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.width(16.dp),
                                    color = MaterialTheme.colorScheme.secondary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                    }

            } else {
                if (info.pages != null)
                    if (info.pages > viewModel.page && properties.size > 10) {
                        item {
                            if (isSort) {
                                if (properties.isNotEmpty()) {
                                    viewModel.propertySort(++viewModel.page)
                                }
                            } else {
                                if (properties.isNotEmpty()) {
                                    viewModel.getProperty(++viewModel.page)
                                }
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
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
}


@Composable
fun PropertyItem(property: PropertyObject, onProjectClick: (PropertyObject) -> Unit) {
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
                onProjectClick.invoke(property)
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
                if (!property.gallery.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            AccountData.BASE_URL + property.gallery[0].image
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
                    property.getTitle().let {
                        it?.let { it1 ->
                            Text(
                                text = it1,
                                style = TextStyle(
                                    color = colorResource(id = R.color.blue),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                    property.region_name.let {
                        it?.let { it1 ->
                            Text(
                                text = it1, style = TextStyle(
                                    fontSize = 13.sp,
                                )
                            )
                        }
                    }
                }
                property.price?.let {
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