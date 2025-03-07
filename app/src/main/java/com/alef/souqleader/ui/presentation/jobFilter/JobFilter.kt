package com.alef.souqleader.ui.presentation.jobFilter

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Area
import com.alef.souqleader.data.remote.dto.CareerLevel
import com.alef.souqleader.data.remote.dto.Category
import com.alef.souqleader.data.remote.dto.City
import com.alef.souqleader.data.remote.dto.Country
import com.alef.souqleader.data.remote.dto.JobAppRequest
import com.alef.souqleader.data.remote.dto.Jobapps
import com.alef.souqleader.data.remote.dto.Type
import com.alef.souqleader.ui.presentation.addlead.DynamicSelectTextField
import com.alef.souqleader.ui.presentation.jobApplication.JobApplicationViewModel
import kotlinx.coroutines.launch

@Composable
fun JobFilter(
    onRestClick: () -> Unit,
    onCancelClick: () -> Unit,
    onFilterClick: (JobAppRequest) -> Unit,
    viewModel: JobApplicationViewModel
) {
    val jobCategoryList = remember { mutableStateListOf<Category>() }
    val countryList = remember { mutableStateListOf<Country>() }
    val cityList = remember { mutableStateListOf<City>() }
    val areaList = remember { mutableStateListOf<Area>() }
    val jobTypeList = remember { mutableStateListOf<Type>() }
    val careerLevelList = remember { mutableStateListOf<CareerLevel>() }
    val jobAppList = remember { mutableStateListOf<Jobapps>() }
    var refresh by remember { mutableStateOf<Boolean>(false) }
    val ctx = LocalContext.current
    val jobCategory = arrayListOf<String>()
    val country = arrayListOf<String>()
    val city = arrayListOf<String>()
    val area = arrayListOf<String>()
    val jobType = arrayListOf<String>()
    val careerLevel = arrayListOf<String>()

    LaunchedEffect(key1 = true) {
        viewModel.addressFilter()
        viewModel.basicData()
        viewModel.viewModelScope.launch {
            viewModel.jobAppResponse.collect {
                jobAppList.addAll(it)
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.addressFilter.collect {
                cityList.clear()
                areaList.clear()
                cityList.addAll(it.cities)
                areaList.addAll(it.areas)
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.basicDataResponse.collect {
                careerLevelList.clear()
                jobAppList.clear()
                countryList.clear()
                jobCategoryList.clear()

                countryList.addAll(it.countries)
                jobTypeList.addAll(it.types)
                careerLevelList.addAll(it.careerLevels)
                jobCategoryList.addAll(it.categories)
            }
        }
    }

    setJobData(
        ctx,
        jobCategory,
        jobCategoryList,
        country,
        countryList,
        city,
        cityList,
        area,
        areaList,
        jobType,
        jobTypeList,
        careerLevel,
        careerLevelList
    )


    Filter(
        jobCategory, country, city, area, jobType, careerLevel,
        jobCategoryList, countryList, cityList,
        areaList, jobTypeList, careerLevelList,refresh,
        onFilterClick = {
            onFilterClick(
                JobAppRequest(
                    jobId = it.jobId,
                    cityId = it.cityId,
                    areaId = it.areaId,
                    categoryId = it.categoryId,
                    careerLevelId = it.careerLevelId,
                    typeId = it.typeId,
                    workplaceId = it.workplaceId,
                    name = it.name,
                    countryId = it.countryId
                )
            )

        },
        onCancelClick = {
            onCancelClick()
        },
        onRestClick = {
            jobCategory.clear()
            country.clear()
            city.clear()
            area.clear()
            jobType.clear()
            careerLevel.clear()
            setJobData(
                ctx,
                jobCategory,
                jobCategoryList,
                country,
                countryList,
                city,
                cityList,
                area,
                areaList,
                jobType,
                jobTypeList,
                careerLevel,
                careerLevelList
            )
            refresh = !refresh
            onRestClick()
        })
}


fun setJobData(
    ctx: Context,
    jobCategory: ArrayList<String>,
    jobCategoryList: SnapshotStateList<Category>,
    country: ArrayList<String>,
    countryList: SnapshotStateList<Country>,
    city: ArrayList<String>,
    cityList: SnapshotStateList<City>,
    area: ArrayList<String>,
    areaList: SnapshotStateList<Area>,
    jobType: ArrayList<String>,
    jobTypeList: SnapshotStateList<Type>,
    careerLevel: ArrayList<String>,
    careerLevelList: SnapshotStateList<CareerLevel>
) {

    jobCategory.add(ctx.getString(R.string.job_category))
    if (jobCategoryList.isNotEmpty()) jobCategoryList.forEach {
        jobCategory.add(it.getCategory())
    }

    country.add(ctx.getString(R.string.country))
    if (countryList.isNotEmpty()) countryList.forEach {
        country.add(it.getCountry())
    }

    city.add(ctx.getString(R.string.city))
    if (cityList.isNotEmpty()) cityList.forEach {
        city.add(it.getCity())
    }
    area.add(ctx.getString(R.string.area))
    if (areaList.isNotEmpty()) areaList.forEach {
        area.add(it.getArea())
    }
    jobType.add(ctx.getString(R.string.job_type))
    if (jobTypeList.isNotEmpty()) jobTypeList.forEach {
        jobType.add(it.getTitle())
    }
    careerLevel.add(ctx.getString(R.string.career_level))
    if (careerLevelList.isNotEmpty()) careerLevelList.forEach {
        careerLevel.add(it.careerLevel())
    }
}

@Composable
fun Filter(
    jobCategory: List<String>,
    country: List<String>,
    city: List<String>,
    area: List<String>,
    jobType: List<String>,
    careerLevel: List<String>,

    jobCategoryList: SnapshotStateList<Category>,
    countryList: SnapshotStateList<Country>,
    cityList: SnapshotStateList<City>,
    areaList: SnapshotStateList<Area>,
    jobTypeList: SnapshotStateList<Type>,
    careerLevelList: SnapshotStateList<CareerLevel>,
    refresh:Boolean,
    onFilterClick: (JobAppRequest) -> Unit,
    onRestClick: () -> Unit,
    onCancelClick: () -> Unit

) {
    val jobAppRequest by remember { mutableStateOf(JobAppRequest()) }
    refresh
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 16.dp
        )
    ) {

        DynamicSelectTextField(jobCategory) { category ->
            jobAppRequest.categoryId =
                jobCategoryList.find { it.getCategory() == category }?.id.toString()
        }

        DynamicSelectTextField(country) { country ->
            jobAppRequest.countryId =
                countryList.find { it.getCountry() == country }?.id.toString()
        }
        DynamicSelectTextField(city) { city ->
            jobAppRequest.cityId =
                cityList.find { it.getCity() == city }?.id.toString()
        }
        DynamicSelectTextField(area) { area ->
            jobAppRequest.areaId =
                areaList.find { it.getArea() == area }?.id.toString()
        }
        DynamicSelectTextField(jobType) { jobType ->
            jobAppRequest.typeId =
                jobTypeList.find { it.getTitle() == jobType }?.id.toString()
        }
        DynamicSelectTextField(careerLevel) { careerLevel ->
            jobAppRequest.careerLevelId =
                careerLevelList.find { it.careerLevel() == careerLevel }?.id.toString()
        }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue2)),
            onClick = {
                onFilterClick(jobAppRequest)
            }) {
            Text(
                text = stringResource(R.string.apply_filter),
                modifier = Modifier.padding(vertical = 8.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.red1)),
                onClick = {
                    jobAppRequest.categoryId = null
                    jobAppRequest.countryId = null
                    jobAppRequest.cityId = null
                    jobAppRequest.areaId = null
                    jobAppRequest.typeId = null
                    jobAppRequest.workplaceId = null
                    jobAppRequest.careerLevelId = null
                    onRestClick()
                }) {
                Text(
                    text = stringResource(id = R.string.reset),
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
                )
            }
            Button(modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.gray_400)),
                onClick = {
                    onCancelClick()
                }) {
                Text(
                    text = stringResource(R.string.cancel),
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
                )
            }
        }

    }
}