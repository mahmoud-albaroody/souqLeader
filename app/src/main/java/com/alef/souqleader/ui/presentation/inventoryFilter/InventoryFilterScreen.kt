package com.alef.souqleader.ui.presentation.inventoryFilter

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.Area
import com.alef.souqleader.data.remote.dto.Category
import com.alef.souqleader.data.remote.dto.City
import com.alef.souqleader.data.remote.dto.Country
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.ProjectFilterRequest
import com.alef.souqleader.data.remote.dto.PropertyDepartment
import com.alef.souqleader.data.remote.dto.PropertyFinishing
import com.alef.souqleader.data.remote.dto.PropertyView
import com.alef.souqleader.data.remote.dto.Regions
import com.alef.souqleader.data.remote.dto.Type
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
    val propertyViewList = remember { mutableStateListOf<PropertyView>() }
    val propertyCategoryList = remember { mutableStateListOf<Category>() }
    val allLeadStatus = remember { mutableStateListOf<AllLeadStatus>() }
    val propertyFinishingList = remember { mutableStateListOf<PropertyFinishing>() }
    val inventoryTypeList = remember { mutableStateListOf<Type>() }
    val inventoryDepartmentList = remember { mutableStateListOf<PropertyDepartment>() }

    val countryList = remember { mutableStateListOf<Country>() }
    val cityList = remember { mutableStateListOf<City>() }
    val areaList = remember { mutableStateListOf<Area>() }
    LaunchedEffect(key1 = true) {
//        inventoryFilterViewModel.getRegion()

        if (type == "Property") {
            inventoryFilterViewModel.propertyFilterData()
        } else {
            inventoryFilterViewModel.projectFilterData()
        }
//        inventoryFilterViewModel.propertyView()
//        inventoryFilterViewModel.propertyCategory()
//        inventoryFilterViewModel.propertyFinishing()
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
            inventoryFilterViewModel.projectFilterData.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.data?.let { it1 ->
                            propertyCategoryList.clear()
                            countryList.clear()
                            propertyFinishingList.clear()
                            inventoryDepartmentList.clear()
                            propertyViewList.clear()
                            inventoryTypeList.clear()
                            propertyCategoryList.addAll(it1.categories)
                            countryList.addAll(it1.countries)
                            it1.finishing?.let { it2 ->
                                propertyFinishingList.addAll(it2)
                            }
                            it1.view?.let { it2 -> propertyViewList.addAll(it2) }
                            it1.department?.let { it2 -> inventoryDepartmentList.addAll(it2) }
                            it1.type?.let { it2 -> inventoryTypeList.addAll(it2) }


                        }
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
//        inventoryFilterViewModel.viewModelScope.launch {
//            inventoryFilterViewModel.propertyView.collect {
//                when (it) {
//                    is Resource.Success -> {
//                        it.data?.data.let { it1 -> it1?.let { it2 -> propertyViewList.addAll(it2) } }
//                        mainViewModel.showLoader = false
//                    }
//
//
//                    is Resource.Loading -> {
//                        mainViewModel.showLoader = true
//                    }
//
//                    is Resource.DataError -> {
//                        if (it.errorCode == 401) {
//                            AccountData.clear()
//                            (context as MainActivity).setContent {
//                                AndroidCookiesTheme {
//                                    MainScreen(
//                                        Modifier,
//                                        navController,
//                                        sharedViewModel,
//                                        mainViewModel
//                                    )
//                                }
//                            }
//                        }
//                        mainViewModel.showLoader = false
//                    }
//                }
//            }
//        }
//        inventoryFilterViewModel.viewModelScope.launch {
//            inventoryFilterViewModel.propertyCategory.collect {
//                when (it) {
//                    is Resource.Success -> {
//
//                        it.data?.data.let { it1 ->
//                            it1?.let { it2 ->
//                                propertyCategoryList.addAll(it2)
//                            }
//                        }
//                        mainViewModel.showLoader = false
//                    }
//
//
//                    is Resource.Loading -> {
//                        mainViewModel.showLoader = true
//                    }
//
//                    is Resource.DataError -> {
//                        if (it.errorCode == 401) {
//                            AccountData.clear()
//                            (context as MainActivity).setContent {
//                                AndroidCookiesTheme {
//                                    MainScreen(
//                                        Modifier,
//                                        navController,
//                                        sharedViewModel,
//                                        mainViewModel
//                                    )
//                                }
//                            }
//                        }
//                        mainViewModel.showLoader = false
//                    }
//                }
//            }
//        }
//        inventoryFilterViewModel.viewModelScope.launch {
//            inventoryFilterViewModel.propertyFinishing.collect {
//                when (it) {
//                    is Resource.Success -> {
//                        it.data?.data?.let { it1 -> propertyFinishingList.addAll(it1) }
//                        mainViewModel.showLoader = false
//                    }
//
//
//                    is Resource.Loading -> {
//                        mainViewModel.showLoader = true
//                    }
//
//                    is Resource.DataError -> {
//                        if (it.errorCode == 401) {
//                            AccountData.clear()
//                            (context as MainActivity).setContent {
//                                AndroidCookiesTheme {
//                                    MainScreen(
//                                        Modifier,
//                                        navController,
//                                        sharedViewModel,
//                                        mainViewModel
//                                    )
//                                }
//                            }
//                        }
//                        mainViewModel.showLoader = false
//                    }
//                }
//            }
//        }
        inventoryFilterViewModel.viewModelScope.launch {
            inventoryFilterViewModel.locationFilterData.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.data?.let {
                            cityList.clear()
                            areaList.clear()
                            cityList.addAll(it.cities)
                            areaList.addAll(it.areas)

                        }
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
            inventoryTypeList,
            inventoryDepartmentList,
            countryList,
            cityList,
            areaList,
            type, onCountryClick = { countryId, cityId ->
                if (type == "Property") {
                    inventoryFilterViewModel.propertyLocationFilterData(
                        countryId = countryId,
                        cityId = cityId
                    )
                } else {
                    inventoryFilterViewModel.locationFilterData(
                        countryId = countryId,
                        cityId = cityId
                    )
                }
            }, onShowClick = {

                    if ((!it.budget_from.isNullOrEmpty() && !it.budget_to.isNullOrEmpty())
                        && (it.budget_from?.toDouble()!! > it.budget_to?.toDouble()!!)) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.please_enter_a_valid_price_range),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    else {

                        val jsonObject = JsonObject()
                        jsonObject.addProperty("title", it.title)
                        jsonObject.addProperty("name", it.name)
                        jsonObject.addProperty("status", it.status)
                        jsonObject.addProperty("region", it.region)
                        jsonObject.addProperty("finishing", it.finishing)
                        jsonObject.addProperty("view", it.view)
                        jsonObject.addProperty("category", it.category)
                        jsonObject.addProperty("countryId", it.countryId)
                        jsonObject.addProperty("cityId", it.cityId)
                        jsonObject.addProperty("areaId", it.areaId)
                        jsonObject.addProperty("typeInventory", it.typeInventory)
                        jsonObject.addProperty("type", it.type)
                        jsonObject.addProperty("department", it.department)


                        jsonObject.addProperty("budget_from", it.budget_from)
                        jsonObject.addProperty("budget_to", it.budget_to)
                        navController.navigate(
                            Screen.ProductFilterResultScreen.route +
                                    "?" + Screen.ProductFilterResultScreen.objectName + "=${jsonObject}"
                        )
                    }

            }
        )
    }
}


@Composable
private fun Budget(onFrom: (String) -> Unit, onTo: (String) -> Unit) {

    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.from), Modifier.align(Alignment.Center),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
            )
        }
        Box(Modifier.weight(2f)) {
            TextFiledItem(stringResource(R.string.min_price), true) {
                onFrom(it)
            }
        }
        Box(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.to), Modifier.align(Alignment.Center),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
            )
        }
        Box(Modifier.weight(2f)) {
            TextFiledItem(stringResource(R.string.max_price), true) {
                onTo(it)
            }
        }
    }
}


@Composable
private fun Filter(
    regionList: SnapshotStateList<Regions>,
    propertyViewList: SnapshotStateList<PropertyView>,
    propertyCategoryList: SnapshotStateList<Category>,
    propertyStatusList: SnapshotStateList<AllLeadStatus>,
    propertyFinishingList: SnapshotStateList<PropertyFinishing>,
    typeInventoryList: SnapshotStateList<Type>,
    inventoryDepartmentList: SnapshotStateList<PropertyDepartment>,
    countryList: SnapshotStateList<Country>,
    cityList: SnapshotStateList<City>,
    areaList: SnapshotStateList<Area>,
    type: String,

    onShowClick: (ProjectFilterRequest) -> Unit,
    onCountryClick: (String, String?) -> Unit
) {

    val filterRequest by remember { mutableStateOf(ProjectFilterRequest()) }

    val typeInventory = arrayListOf<String>()
    typeInventory.add(stringResource(R.string.inventorytype))
    if (typeInventoryList.isNotEmpty()) typeInventoryList.forEach {
        it.getTitle().let { it1 -> typeInventory.add(it1) }
    }

    val inventoryDepartment = arrayListOf<String>()

    inventoryDepartment.add(stringResource(R.string.department))
    if (inventoryDepartmentList.isNotEmpty()) inventoryDepartmentList.forEach {
        it.getTitle().let { it1 -> inventoryDepartment.add(it1) }
    }

    val country = arrayListOf<String>()
    country.add(stringResource(R.string.country))
    if (countryList.isNotEmpty()) countryList.forEach {
        it.getCountry().let { it1 -> country.add(it1) }
    }
    val city = arrayListOf<String>()
    city.add(stringResource(R.string.city))
    if (cityList.isNotEmpty()) cityList.forEach {
        it.getCity().let { it1 -> city.add(it1) }
    }
    val area = arrayListOf<String>()
    area.add(stringResource(R.string.area))
    if (areaList.isNotEmpty()) areaList.forEach {
        it.getArea().let { it1 -> area.add(it1) }
    }
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
        it.getCategory().let { it1 -> propertyCategory.add(it1) }
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
            if (type == "Property") {
                DynamicSelectTextField(typeInventory) { type ->
                    filterRequest.typeInventory =
                        typeInventoryList.find { it.getTitle() == type }?.id.toString()
                }

                DynamicSelectTextField(propertyCategory) { category ->
                    filterRequest.category =
                        propertyCategoryList.find { it.getCategory() == category }?.id.toString()
                }



                DynamicSelectTextField(propertyFinishing) { finishing ->
                    filterRequest.finishing =
                        propertyFinishingList.find { it.getTitle() == finishing }?.id.toString()
                }
                DynamicSelectTextField(inventoryDepartment) { department ->
                    filterRequest.department =
                        inventoryDepartmentList.find { it.getTitle() == department }?.id.toString()
                }
                DynamicSelectTextField(propertyView) { view ->
                    filterRequest.view =
                        propertyViewList.find { it.getTitle() == view }?.id.toString()
                }
                DynamicSelectTextField(country) { country ->
                    filterRequest.countryId =
                        countryList.find { it.getCountry() == country }?.id.toString()
                    onCountryClick(filterRequest.countryId.toString(), null)
                }

                DynamicSelectTextField(city) { city ->
                    filterRequest.cityId =
                        cityList.find {
                            it.getCity() == city
                        }?.id.toString()
                    onCountryClick(
                        filterRequest.countryId.toString(),
                        filterRequest.cityId.toString()
                    )
                }
                DynamicSelectTextField(area) { area ->
                    filterRequest.areaId =
                        areaList.find { it.getArea() == area }?.id.toString()
                }
            } else {
                TextFiledItem(stringResource(R.string.title), true) {
                    filterRequest.title = it
                }
                DynamicSelectTextField(propertyCategory) { category ->
                    filterRequest.category =
                        propertyCategoryList.find { it.getCategory() == category }?.id.toString()
                }
                DynamicSelectTextField(country) { country ->
                    filterRequest.countryId =
                        countryList.find { it.getCountry() == country }?.id.toString()
                    onCountryClick(filterRequest.countryId.toString(), null)
                }

                DynamicSelectTextField(city) { city ->
                    filterRequest.cityId =
                        cityList.find {
                            it.getCity() == city
                        }?.id.toString()
                    onCountryClick(
                        filterRequest.countryId.toString(),
                        filterRequest.cityId.toString()
                    )
                }
                DynamicSelectTextField(area) { area ->
                    filterRequest.areaId =
                        areaList.find { it.getArea() == area }?.id.toString()
                }
            }

            Budget(onFrom = {
                filterRequest.budget_from = it
            }, onTo = {
                filterRequest.budget_to = it
            })
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