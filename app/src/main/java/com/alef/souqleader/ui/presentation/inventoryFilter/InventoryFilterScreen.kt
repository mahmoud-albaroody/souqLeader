package com.alef.souqleader.ui.presentation.inventoryFilter

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.ProjectFilterRequest
import com.alef.souqleader.data.remote.dto.Regions
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.addlead.DynamicSelectTextField
import com.alef.souqleader.ui.presentation.addlead.TextFiledItem
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.theme.AndroidCookiesTheme
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

@Composable
fun InventoryFilterScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    sharedViewModel: SharedViewModel, type: String
) {
    val context = LocalContext.current
    val inventoryFilterViewModel: InventoryFilterViewModel = hiltViewModel()

    val regionList = remember { mutableStateListOf<Regions>() }
    val propertyViewList = remember { mutableStateListOf<Regions>() }
    val propertyCategoryList = remember { mutableStateListOf<Regions>() }
    val allLeadStatus = remember { mutableStateListOf<AllLeadStatus>() }
    val propertyFinishingList = remember { mutableStateListOf<Regions>() }


    LaunchedEffect(key1 = true) {
        inventoryFilterViewModel.getRegion()
        inventoryFilterViewModel.propertyView()
        inventoryFilterViewModel.propertyCategory()
        inventoryFilterViewModel.propertyFinishing()
        inventoryFilterViewModel.getLeads()
        inventoryFilterViewModel.viewModelScope.launch {
            inventoryFilterViewModel.allLead.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.data?.let { it1 -> allLeadStatus.addAll(it1) }
                        mainViewModel.showLoader = false
                    }


                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        if (it.errorCode == 401) {
                            AccountData.clear()
                            (context as MainActivity).setContent {
                                AndroidCookiesTheme {
                                    MainScreen(
                                        Modifier,
                                        navController,
                                        sharedViewModel,
                                        mainViewModel
                                    )
                                }
                            }
                        }
                        mainViewModel.showLoader = false
                    }
                }
            }
        }
        inventoryFilterViewModel.viewModelScope.launch {
            inventoryFilterViewModel.region.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.data.let { it1 -> it1?.let { it2 -> regionList.addAll(it2) } }
                        mainViewModel.showLoader = false
                    }


                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        if (it.errorCode == 401) {
                            AccountData.clear()
                            (context as MainActivity).setContent {
                                AndroidCookiesTheme {
                                    MainScreen(
                                        Modifier,
                                        navController,
                                        sharedViewModel,
                                        mainViewModel
                                    )
                                }
                            }
                        }
                        mainViewModel.showLoader = false
                    }
                }
            }
        }
        inventoryFilterViewModel.viewModelScope.launch {
            inventoryFilterViewModel.propertyView.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.data.let { it1 -> it1?.let { it2 -> propertyViewList.addAll(it2) } }
                        mainViewModel.showLoader = false
                    }


                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        if (it.errorCode == 401) {
                            AccountData.clear()
                            (context as MainActivity).setContent {
                                AndroidCookiesTheme {
                                    MainScreen(
                                        Modifier,
                                        navController,
                                        sharedViewModel,
                                        mainViewModel
                                    )
                                }
                            }
                        }
                        mainViewModel.showLoader = false
                    }
                }
            }
        }
        inventoryFilterViewModel.viewModelScope.launch {
            inventoryFilterViewModel.propertyCategory.collect {
                when (it) {
                    is Resource.Success -> {

                        it.data?.data.let { it1 -> it1?.let { it2 -> propertyCategoryList.addAll(it2) } }
                        mainViewModel.showLoader = false
                    }


                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        if (it.errorCode == 401) {
                            AccountData.clear()
                            (context as MainActivity).setContent {
                                AndroidCookiesTheme {
                                    MainScreen(
                                        Modifier,
                                        navController,
                                        sharedViewModel,
                                        mainViewModel
                                    )
                                }
                            }
                        }
                        mainViewModel.showLoader = false
                    }
                }
            }
        }

        inventoryFilterViewModel.viewModelScope.launch {
            inventoryFilterViewModel.propertyFinishing.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.data?.let { it1 -> propertyFinishingList.addAll(it1) }
                        mainViewModel.showLoader = false
                    }


                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        if (it.errorCode == 401) {
                            AccountData.clear()
                            (context as MainActivity).setContent {
                                AndroidCookiesTheme {
                                    MainScreen(
                                        Modifier,
                                        navController,
                                        sharedViewModel,
                                        mainViewModel
                                    )
                                }
                            }
                        }
                        mainViewModel.showLoader = false
                    }
                }
            }
        }


    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp)
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.white))
    ) {

        Filter(
            regionList,
            propertyViewList,
            propertyCategoryList,
            allLeadStatus,
            propertyFinishingList,
            type
        ) {
            val jsonObject = JsonObject()
            jsonObject.addProperty("title", it.title)
            jsonObject.addProperty("name", it.name)
            jsonObject.addProperty("status", it.status)
            jsonObject.addProperty("region", it.region)
            jsonObject.addProperty("finishing", it.finishing)
            jsonObject.addProperty("view", it.view)
            jsonObject.addProperty("category", it.category)
            jsonObject.addProperty("type", it.type)
            navController.navigate(
                Screen.ProductFilterResultScreen.route +
                        "?" + Screen.ProductFilterResultScreen.objectName + "=${jsonObject}"
            )
        }

    }
}


@Composable
private fun Filter(
    regionList: SnapshotStateList<Regions>,
    propertyViewList: SnapshotStateList<Regions>,
    propertyCategoryList: SnapshotStateList<Regions>,
    propertyStatusList: SnapshotStateList<AllLeadStatus>,
    propertyFinishingList: SnapshotStateList<Regions>,
    type: String,
    onShowClick: (ProjectFilterRequest) -> Unit
) {

    val filterRequest by remember { mutableStateOf(ProjectFilterRequest()) }


    val region = arrayListOf<String>()
    region.add(stringResource(R.string.region))
    if (regionList.isNotEmpty()) regionList.forEach {
        it.getTitle()?.let { it1 -> region.add(it1) }
    }
    val status = arrayListOf<String>()
    status.add(stringResource(R.string.status))
    if (propertyStatusList.isNotEmpty()) propertyStatusList.forEach {
        it.getTitle().let { it1 -> status.add(it1) }
    }

    val propertyView = arrayListOf<String>()
    propertyView.add(stringResource(R.string.view))
    if (propertyViewList.isNotEmpty()) propertyViewList.forEach {
        it.getTitle()?.let { it1 -> propertyView.add(it1) }
    }

    val propertyCategory = arrayListOf<String>()
    propertyCategory.add(stringResource(R.string.category))
    if (propertyCategoryList.isNotEmpty()) propertyCategoryList.forEach {
        it.getTitle()?.let { it1 -> propertyCategory.add(it1) }
    }
    val propertyFinishing = arrayListOf<String>()
    propertyFinishing.add(stringResource(R.string.finishing))
    if (propertyFinishingList.isNotEmpty()) propertyFinishingList.forEach {
        it.getTitle()?.let { it1 -> propertyFinishing.add(it1) }
    }
    filterRequest.type = type
    Column(
        Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .padding(horizontal = 24.dp)
    ) {

        Column {
            if (type != "Property") {
                TextFiledItem(stringResource(R.string.title), true) {
                    filterRequest.title = it
                }
            }

            DynamicSelectTextField(region) { region ->
                filterRequest.region = regionList.find { it.getTitle() == region }?.id.toString()
            }


            DynamicSelectTextField(status) { st ->
                filterRequest.status =
                    propertyStatusList.find { it.getTitle() == st }?.id.toString()
            }
            if (type == "Property") {
                DynamicSelectTextField(propertyView) { view ->
                    filterRequest.view =
                        propertyViewList.find { it.getTitle() == view }?.id.toString()
                }
                DynamicSelectTextField(propertyCategory) { category ->
                    filterRequest.category =
                        propertyCategoryList.find { it.getTitle() == category }?.id.toString()
                }
                DynamicSelectTextField(propertyFinishing) { finishing ->
                    filterRequest.finishing =
                        propertyFinishingList.find { it.getTitle() == finishing }?.id.toString()
                }
            }
        }


        Row(
            Modifier
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
//            OutlinedButton(modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth(),
//                shape = RoundedCornerShape(15.dp),
//                onClick = {
//                    addLead = AddLead(is_fresh = true)
//                    fromAmount = "0"
//                    toAmount = "1000"
//                    hasName=!hasName
//                }) {
//                Text(
//                    text = stringResource(R.string.reset),
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//            }
            Button(modifier = Modifier
//                .weight(2f)
                .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue)),
                onClick = {
                    onShowClick(filterRequest)
                }) {
                Text(
                    text = stringResource(R.string.show),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}