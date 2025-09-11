package com.alef.souqleader.ui.presentation.addlead

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllLeadStatus
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.domain.model.AddLead
import com.alef.souqleader.domain.model.Campaign
import com.alef.souqleader.domain.model.Channel
import com.alef.souqleader.domain.model.CommunicationWay
import com.alef.souqleader.domain.model.Marketer
import com.alef.souqleader.domain.model.Sales
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.theme.*
import kotlinx.coroutines.launch


@Composable
fun AddLeadScreen(
    modifier: Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    sharedViewModel: SharedViewModel
) {
    val addLeadViewModel: AddLeadViewModel = hiltViewModel()
    val context = LocalContext.current
    val campaignList = remember { mutableStateListOf<Campaign>() }
    val projectList = remember { mutableStateListOf<Project>() }
    val allLead = remember { mutableStateListOf<AllLeadStatus>() }
    val channelList = remember { mutableStateListOf<Channel>() }
    val salesList = remember { mutableStateListOf<Sales>() }
    val marketerList = remember { mutableStateListOf<Marketer>() }
    var leadAdded by remember { mutableStateOf<AddLead?>(null) }

    val communicationWayList = remember { mutableStateListOf<CommunicationWay>() }
    LaunchedEffect(key1 = true) {
        addLeadViewModel.getLeads()
        addLeadViewModel.allMarketer()
        addLeadViewModel.allSales()
        addLeadViewModel.campaign()
        addLeadViewModel.communicationWay()
        addLeadViewModel.channel()
        addLeadViewModel.getProject(1)

        addLeadViewModel.viewModelScope.launch {
            addLeadViewModel.campaignResponse.collect {
                it.data?.let { it1 -> campaignList.addAll(it1) }
            }
        }
        addLeadViewModel.viewModelScope.launch {
            addLeadViewModel.project.collect {
                it.data?.let { it1 -> projectList.addAll(it1) }
            }
        }


        addLeadViewModel.viewModelScope.launch {
            addLeadViewModel.channel.collect {
                it.data?.let { it1 -> channelList.addAll(it1) }
            }
        }
        addLeadViewModel.viewModelScope.launch {
            addLeadViewModel.allLead.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.data?.let { it1 -> allLead.addAll(it1) }
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

        addLeadViewModel.viewModelScope.launch {
            addLeadViewModel.addLead.collect {
                when (it) {
                    is Resource.Success -> {
                        Toast.makeText(context, it.data?.message.toString(), Toast.LENGTH_LONG)
                            .show()
                        mainViewModel.showLoader = false
                        if(it.data?.status==true) {
                            if (leadAdded?.is_fresh == 1) {
                                Screen.AllLeadsScreen.title = context.getString(R.string.fresh)
                                navController.navigate(
                                    Screen.AllLeadsScreen.route.plus("/${1}")
                                )
                            } else {
                                Screen.AllLeadsScreen.title = context.getString(R.string.cold)
                                navController.navigate(
                                    Screen.AllLeadsScreen.route.plus("/${2}")
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        mainViewModel.showLoader = false
                    }
                }

            }
        }

        addLeadViewModel.viewModelScope.launch {
            addLeadViewModel.sales.collect {
                it.data?.let { it1 -> salesList.addAll(it1) }
            }
        }
        addLeadViewModel.viewModelScope.launch {
            addLeadViewModel.marketer.collect {
                it.data?.let { it1 -> marketerList.addAll(it1) }
            }
        }
        addLeadViewModel.viewModelScope.launch {
            addLeadViewModel.communicationWay.collect {
                it.data?.let { it1 -> communicationWayList.addAll(it1) }
            }
        }
    }
    AddLead(
        campaignList, marketerList, salesList, channelList, communicationWayList, projectList,
        allLead
    ) {
        leadAdded = it
        addLeadViewModel.lead(it)
    }
}


@Composable
fun AddLead(
    campaignList: SnapshotStateList<Campaign>,
    marketerList: SnapshotStateList<Marketer>,
    salesList: SnapshotStateList<Sales>,
    channelList: SnapshotStateList<Channel>,
    communicationWayList: SnapshotStateList<CommunicationWay>,
    projectList: SnapshotStateList<Project>,
    statusList: SnapshotStateList<AllLeadStatus>,
    onAddClick: (AddLead) -> Unit
) {
    val scrollState = rememberScrollState()
    val hasName = remember { mutableStateOf(false) }
    val hasPhone = remember { mutableStateOf(false) }
    val hasChannel = remember { mutableStateOf(false) }
    val hasMarketer = remember { mutableStateOf(false) }
    val hasSales = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val addLead by remember { mutableStateOf(AddLead(is_fresh = 1)) }
    val status = arrayListOf<String>()
    status.add(stringResource(R.string.fresh))
    status.add(stringResource(R.string.cold))

//    if (statusList.isNotEmpty()) statusList.forEach {
//        if (it.title_en?.uppercase() == "Cold".uppercase() || it.title_en?.uppercase() == "Fresh".uppercase())
//            status.add(it.getTitle())
//    }
    val channels = arrayListOf<String>()
    channels.add(stringResource(R.string.lead_source))
    if (channelList.isNotEmpty()) channelList.forEach {
        channels.add(it.getTitle())
    }
    val projects = arrayListOf<String>()
    projects.add(stringResource(R.string.project))
    if (projectList.isNotEmpty()) projectList.forEach {
        it.title?.let { it1 -> projects.add(it1) }
    }

    val communicationWays = arrayListOf<String>()
    communicationWays.add(stringResource(R.string.communication_way))
    if (communicationWayList.isNotEmpty()) communicationWayList.forEach {
        communicationWays.add(it.getTitle())
    }

    val marketers = arrayListOf<String>()
    marketers.add(stringResource(R.string.marketer))
    if (marketerList.isNotEmpty()) marketerList.forEach {
        marketers.add(it.name)
    }
    val sales = arrayListOf<String>()
    sales.add(stringResource(R.string.sales_rep)+"*")
    if (salesList.isNotEmpty()) salesList.forEach {
        sales.add(it.name)
    }

    val campaigns = arrayListOf<String>()
    campaigns.add(stringResource(R.string.campaign_id))
    if (campaignList.isNotEmpty()) campaignList.forEach {
        campaigns.add(it.getTitle())
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(vertical = 8.dp, horizontal = 24.dp)
    ) {
        Column(
            Modifier
                .verticalScroll(scrollState)
                .weight(12f)
        ) {

            DynamicSelectTextField(status) { status ->

                    if(status.uppercase() == context.getString(R.string.fresh).uppercase()) {
                        addLead.is_fresh = 1
                    }else{
                        addLead.is_fresh = 0
                    }

            }


            TextFiledItem(stringResource(R.string.name)+"*", true) {
                addLead.name = it
                hasName.value = false
            }
            if (hasName.value) Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.the_name_is_required),
                style = TextStyle(
                    color = colorResource(id = R.color.red), fontSize = 12.sp
                )
            )

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
//                Box(Modifier.weight(1f)) {
//                    TextFiledItem("+2") {
//                        addLead.phone = it
//                    }
//                }
                Box(Modifier.fillMaxWidth()) {
                    TextFiledItem(stringResource(R.string.mobile)+"*", true) {
                        addLead.phone = it
                        hasPhone.value = false
                    }
                }
            }
            if (hasPhone.value) Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.the_phone_is_required),
                style = TextStyle(
                    color = colorResource(id = R.color.red), fontSize = 12.sp
                )
            )
            DynamicSelectTextField(channels) { channel ->
                addLead.channel = channelList.find { it.getTitle() == channel }?.id
               // hasChannel.value = false
            }
//            if (hasChannel.value) Text(
//                modifier = Modifier.padding(start = 8.dp),
//                text = stringResource(R.string.the_channel_is_required),
//                style = TextStyle(
//                    color = colorResource(id = R.color.red), fontSize = 12.sp
//                )
//            )
            TextFiledItem(stringResource(id = R.string.e_mail), true) {
                addLead.email = it

            }


            DynamicSelectTextField(projects) { projectName ->
                addLead.project_id = projectList.find { it.title == projectName }?.id
            }
            DynamicSelectTextField(communicationWays) { communicationWays ->
                addLead.communication_way =
                    communicationWayList.find { it.getTitle() == communicationWays }?.id
            }
//            TextFiledItem(stringResource(R.string.cancel_reason), true) {
//                addLead.cancel_reason = it
//            }
            DynamicSelectTextField(marketers) { marketer ->
                addLead.marketer_id = marketerList.find { it.name == marketer }?.id
               // hasMarketer.value = false
            }
//            if (hasMarketer.value) Text(
//                modifier = Modifier.padding(start = 8.dp),
//                text = stringResource(R.string.the_marketer_id_is_required),
//                style = TextStyle(
//                    color = colorResource(id = R.color.red), fontSize = 12.sp
//                )
//            )
            TextFiledItem(stringResource(R.string.note), true) {
                addLead.note = it
            }
            DynamicSelectTextField(sales) { sales ->
                addLead.sales_id = salesList.find { it.name == sales }?.id
                hasSales.value = false
            }
            if (hasSales.value) Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.the_sales_id_is_required),
                style = TextStyle(
                    color = colorResource(id = R.color.red), fontSize = 12.sp
                )
            )
            TextFiledItem(stringResource(id = R.string.budget), true) {
                addLead.budget = it
            }
            DynamicSelectTextField(campaigns) { campaigns ->
                addLead.campaign_id = campaignList.find { it.getTitle() == campaigns }?.id
            }
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue)),
            onClick = {
                if (addLead.name.isNullOrEmpty()) {
                    hasName.value = true
                } else if (addLead.phone.isNullOrEmpty()) {
                    hasPhone.value = true
                }
//                else if (addLead.channel == null) {
//                    hasChannel.value = true
//                } else if (addLead.marketer_id == null) {
//                    hasMarketer.value = true
//                }
                else if (addLead.sales_id == null) {
                    hasSales.value = true
                } else {
                    onAddClick(addLead)
                }
            }) {
            Text(
                text = stringResource(R.string.add_lead), Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFiledItem(
    text: String,
    click: Boolean,
    value: String? = null,
    onTextChange: (String) -> Unit
) {

    var textValue by remember { mutableStateOf("") }
    var hint by remember { mutableStateOf(text) }

    val keyboardOptions: KeyboardOptions =
        when (text) {
            stringResource(id = R.string.mobile)-> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            }
            (stringResource(id = R.string.mobile)+"*") -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            }


            stringResource(id = R.string.filter) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                )
            }

            stringResource(R.string.caller_called_number) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            }

            stringResource(R.string.note) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            }

            stringResource(id = R.string.budget) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done
                )
            }

            stringResource(id = R.string.max_price) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done
                )
            }

            stringResource(id = R.string.min_price) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next
                )
            }

            stringResource(id = R.string.name) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            }

            stringResource(id = R.string.e_mail) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                )
            }

            else -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            }
        }

    if (value != null)
        hint = value

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        value = textValue,
        placeholder = {
            Text(
                text = hint, style = TextStyle(fontSize = 13.sp)
            )
        },
        keyboardOptions = keyboardOptions,
        //  keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),

        textStyle = TextStyle(fontSize = 13.sp),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = colorResource(id = R.color.black),
            disabledLabelColor = colorResource(id = R.color.transparent),
            focusedIndicatorColor = colorResource(id = R.color.transparent),
            unfocusedIndicatorColor = colorResource(id = R.color.transparent),
            unfocusedLabelColor = colorResource(id = R.color.transparent)
        ),
        onValueChange = {
            textValue = it
            onTextChange(textValue)
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        enabled = click
    )
}
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TextFiledItem1(
    text: String,
    click: Boolean,
    value: String? = null,
    onTextChange: (String) -> Unit
) {

    var textValue by remember { mutableStateOf("") }
    var hint by remember { mutableStateOf(text) }

    val keyboardOptions: KeyboardOptions =
        when (text) {
            stringResource(id = R.string.mobile)-> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            }
            (stringResource(id = R.string.mobile)+"*") -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            }


            stringResource(id = R.string.filter) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                )
            }

            stringResource(R.string.caller_called_number) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
            }

            stringResource(R.string.note) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            }

            stringResource(id = R.string.budget) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done
                )
            }

            stringResource(id = R.string.max_price) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done
                )
            }

            stringResource(id = R.string.min_price) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next
                )
            }

            stringResource(id = R.string.name) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
            }

            stringResource(id = R.string.e_mail) -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                )
            }

            else -> {
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
            }
        }

    if (value != null)
        hint = value

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        value = textValue,
        placeholder = {
            Text(
                text = hint, style = TextStyle(fontSize = 13.sp)
            )
        },
        keyboardOptions = keyboardOptions,
        //  keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),

        textStyle = TextStyle(fontSize = 13.sp),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = colorResource(id = R.color.black),
            disabledLabelColor = colorResource(id = R.color.transparent),
            focusedIndicatorColor = colorResource(id = R.color.transparent),
            unfocusedIndicatorColor = colorResource(id = R.color.transparent),
            unfocusedLabelColor = colorResource(id = R.color.transparent)
        ),
        onValueChange = {
            textValue = it.filter {
                it.isLetterOrDigit() || it == '.'
            }
            onTextChange(textValue)
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        enabled = click
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    options: List<String>,
    textAlign: TextAlign = TextAlign.Start,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .menuAnchor(),
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 13.sp,
                textAlign = textAlign
            ),
            value = selectedOptionText,
            onValueChange = { },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropDown
                    else Icons.Default.ArrowDropDown, contentDescription = null
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                cursorColor = colorResource(id = R.color.black),
                disabledLabelColor = colorResource(id = R.color.blue),
                focusedIndicatorColor = colorResource(id = R.color.transparent),
                unfocusedIndicatorColor = colorResource(id = R.color.transparent)
            ),
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(text = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = selectionOption,
                        style = TextStyle(
                            fontSize = 13.sp,
                            textAlign = textAlign
                        )
                    )
                }, onClick = {
                    selectedOptionText = selectionOption
                    expanded = false
                    onOptionSelected(selectionOption)
                })
            }
        }
    }
}

@Composable

fun ReminderItem() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 9.dp, horizontal = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.after_two_hour),
                style = TextStyle(
                    fontSize = 14.sp, color = colorResource(id = R.color.black)
                ),
            )
        }
    }
}

@Composable
fun RadioButtonGroup() {
    var selectedOption by remember { mutableStateOf("Not interested") }
    val options = listOf(
        stringResource(R.string.not_interested),
        stringResource(R.string.low_budget),
        stringResource(R.string.wrong_number),
        stringResource(R.string.another_location),
        stringResource(R.string.another_reasons)
    )

    Column() {

        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    modifier = Modifier.height(38.dp),
                    selected = (option == selectedOption),
                    onClick = { selectedOption = option },
                    colors = RadioButtonDefaults.colors(
                        colorResource(id = R.color.blue)
                    )
                )
                Text(
                    text = option,

                    )
            }
        }
    }
}


