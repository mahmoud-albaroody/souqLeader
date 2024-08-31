package com.alef.souqleader.ui.presentation.filter

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
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
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.domain.model.Campaign
import com.alef.souqleader.domain.model.Channel
import com.alef.souqleader.domain.model.CommunicationWay
import com.alef.souqleader.domain.model.Marketer
import com.alef.souqleader.domain.model.Sales
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.addlead.AddLeadViewModel
import com.alef.souqleader.ui.presentation.addlead.DynamicSelectTextField
import com.alef.souqleader.ui.presentation.addlead.TextFiledItem
import com.alef.souqleader.ui.theme.*
import com.google.gson.JsonObject
import kotlinx.coroutines.launch


@Composable
fun FilterScreen(navController: NavController, modifier: Modifier) {
    val context = LocalContext.current
    val filterViewModel: AddLeadViewModel = hiltViewModel()
    val leadsFilterViewModel: LeadsFilterViewModel = hiltViewModel()
    val campaignList = remember { mutableStateListOf<Campaign>() }
    val projectList = remember { mutableStateListOf<Project>() }
    val channelList = remember { mutableStateListOf<Channel>() }
    val salesList = remember { mutableStateListOf<Sales>() }
    val allLead = remember { mutableStateListOf<AllLeadStatus>() }
    val marketerList = remember { mutableStateListOf<Marketer>() }
    val communicationWayList = remember { mutableStateListOf<CommunicationWay>() }
    LaunchedEffect(key1 = true) {
        filterViewModel.getLeads()
        filterViewModel.allMarketer()
        filterViewModel.allSales()
        filterViewModel.campaign()
        filterViewModel.communicationWay()
        filterViewModel.channel()
        filterViewModel.getProject()
        filterViewModel.viewModelScope.launch {
            filterViewModel.campaignResponse.collect {
                it.data?.let { it1 -> campaignList.addAll(it1) }
            }
        }
        filterViewModel.viewModelScope.launch {
            filterViewModel.allLead.collect {
                allLead.addAll(it)
            }
        }
        filterViewModel.viewModelScope.launch {
            filterViewModel.project.collect {
                it.data?.let { it1 -> projectList.addAll(it1) }
            }
        }
        filterViewModel.viewModelScope.launch {
            filterViewModel.channel.collect {
                it.data?.let { it1 -> channelList.addAll(it1) }
            }
        }
        filterViewModel.viewModelScope.launch {
            filterViewModel.addLead.collect {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
            }
        }

        filterViewModel.viewModelScope.launch {
            filterViewModel.sales.collect {
                it.data?.let { it1 -> salesList.addAll(it1) }
            }
        }
        filterViewModel.viewModelScope.launch {
            filterViewModel.marketer.collect {
                it.data?.let { it1 -> marketerList.addAll(it1) }
            }
        }
        filterViewModel.viewModelScope.launch {
            filterViewModel.communicationWay.collect {
                it.data?.let { it1 -> communicationWayList.addAll(it1) }
            }
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.white))
    ) {

        Filter(
            allLead,
            channelList,
            communicationWayList,
            projectList
        ) {
            val jsonObject = JsonObject()
            jsonObject.addProperty("name", it.name)
            jsonObject.addProperty("status", it.status)
            jsonObject.addProperty("channel", it.channel)
            jsonObject.addProperty("project", it.project)
            jsonObject.addProperty("communication_way", it.communication_way)
            jsonObject.addProperty("budget", it.budget)
            navController.navigate(Screen.FilterResultScreen.route+"/${jsonObject.toJson()}")
        }

    }
}


@Composable
fun Filter(
    statusList: SnapshotStateList<AllLeadStatus>,
    channelList: SnapshotStateList<Channel>,
    communicationWayList: SnapshotStateList<CommunicationWay>,
    projectList: SnapshotStateList<Project>,
    onShowClick: (FilterRequest) -> Unit
) {

    var fromAmount by remember { mutableStateOf("") }
    var toAmount by remember { mutableStateOf("10000") }
    val filterRequest = FilterRequest()


    val channels = arrayListOf<String>()
    channels.add(stringResource(R.string.channel))
    if (channelList.isNotEmpty()) channelList.forEach {
        channels.add(it.getTitle())
    }
    val projects = arrayListOf<String>()
    projects.add(stringResource(R.string.project))
    if (projectList.isNotEmpty()) projectList.forEach {
        it.title?.let { it1 -> projects.add(it1) }
    }

    val communicationWays = arrayListOf<String>()
    communicationWays.add(stringResource(id = R.string.communication_way))
    if (communicationWayList.isNotEmpty()) communicationWayList.forEach {
        communicationWays.add(it.getTitle())
    }

    val status = arrayListOf<String>()
    status.add(stringResource(R.string.status))
    if (statusList.isNotEmpty()) statusList.forEach {
        status.add(it.getTitle())
    }
    Column(
        Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .padding(horizontal = 24.dp)
    ) {

        Column {
            TextFiledItem(stringResource(R.string.name), true) {
                filterRequest.name = it
            }

            DynamicSelectTextField(status) { status ->
                filterRequest.status = statusList.find { it.getTitle() == status }?.id.toString()
            }

            DynamicSelectTextField(channels) { channel ->
                filterRequest.channel = channelList.find { it.getTitle() == channel }?.id.toString()
            }

            DynamicSelectTextField(projects) { projectName ->
                filterRequest.project = projectList.find { it.title == projectName }?.id.toString()
            }
            DynamicSelectTextField(communicationWays) { communicationWays ->
                filterRequest.communication_way =
                    communicationWayList.find { it.getTitle() == communicationWays }?.id.toString()
            }


        }

        Column {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.budget),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
            )
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(Modifier.weight(2f)) {
                    TextFiledItem(fromAmount, false) {

                    }
                }
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.to), Modifier.align(Alignment.Center),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    )
                }
                Box(Modifier.weight(2f)) {
                    TextFiledItem(toAmount, false) {

                    }
                }
            }
            RangeSliderExample(onSelectedValue = { from, to ->
                fromAmount = from
                toAmount = to
                filterRequest.budget = toAmount
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


@Composable
fun RangeSliderExample(onSelectedValue: (String, String) -> Unit) {
    var sliderPosition by remember { mutableStateOf(0f..100f) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RangeSlider(
            value = sliderPosition,
            steps = 5,
            onValueChange = { range -> sliderPosition = range },
            valueRange = 0f..100f,
            onValueChangeFinished = {

            },
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.blue),
                activeTrackColor = colorResource(id = R.color.blue),
                activeTickColor = colorResource(id = R.color.blue), inactiveTickColor = colorResource(id = R.color.lightGray),
                inactiveTrackColor = colorResource(id = R.color.lightGray)
            )
        )
        val from = String.format("%.2f", sliderPosition.toString().substringBefore("..").toDouble()*100)
        val to = String.format("%.2f", sliderPosition.toString().substringAfter("..").toDouble()*100)
        onSelectedValue(from, to)


    }
}
