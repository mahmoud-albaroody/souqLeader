package com.alef.souqleader.ui.presentation.leadUpdate

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import com.alef.souqleader.data.remote.dto.CancelationReason
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.SharedViewModel
import com.alef.souqleader.ui.presentation.mainScreen.MainScreen
import com.alef.souqleader.ui.theme.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun LeadUpdateScreen(
    navController: NavHostController,
    modifier: Modifier,
    mainViewModel: MainViewModel,
    sharedViewModel: SharedViewModel,
    leadIds: Array<String>
) {
    val viewModel: LeadUpdateViewModel = hiltViewModel()
    val allLead = remember { mutableStateListOf<AllLeadStatus>() }
    val cancelationReason = remember { mutableStateListOf<CancelationReason>() }
    val cancelationTitleReason = remember { mutableStateListOf<String>() }
    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        viewModel.getLeads()
        viewModel.cancelationReason()
        viewModel.viewModelScope.launch {
            viewModel.allLead.collect {
                when (it) {
                    is Resource.Success -> {
                        allLead.clear()
                        allLead.add(
                            AllLeadStatus(
                                title_ar = "Lead Stage",
                                title_en = "Lead Stage"
                            )
                        )
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
        viewModel.viewModelScope.launch {
            viewModel.cancelationReason.collect {
                it.data?.let { it1 -> cancelationReason.addAll(it1) }
                cancelationReason.forEach { cancelationReason ->
                    cancelationTitleReason.add(cancelationReason.getTitle())
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.updateLead.collect {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
                if(it.status) {
                    navController.popBackStack()
                }
            }
        }

    }
    LeadUpdate(
        allLead,
        cancelationTitleReason,
        onUpdateClick = { leadSelected, note, cancelationTitle, selectedDate ->
            var status: String? = allLead.find { it.getTitle() == leadSelected }?.id.toString()
            var cancelReason: String? =
                cancelationReason.find { it.getTitle() == cancelationTitle }?.id.toString()
            var date: String? = selectedDate
            var leadNote: String? = note
            if (status == "0" || status == "null") {
                status = null
            }
            if (selectedDate == "Date / Time") {
                date = null
            }
            if (cancelReason.isNullOrEmpty() || cancelReason == "null") {
                cancelReason = null
            }
            if (note.isEmpty()) {
                leadNote = null
            }

//            viewModel.updateLead(
//                id = leadId,
//                status = status,
//                note = leadNote,
//                reminderTime = date,
//                cancelReason = cancelReason
//            )
            viewModel.updateMulti(
                ids = leadIds,
                status = status,
                note = leadNote,
                reminderTime = date,
                cancelReason = cancelReason
            )
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadUpdate(
    leads: SnapshotStateList<AllLeadStatus>,
    cancelationTitleReason: SnapshotStateList<String>,
    onUpdateClick: (String, String, String, String) -> Unit
) {
    var note by remember { mutableStateOf("") }
    var leadSelected by remember { mutableStateOf("") }
    var cancelationTitle by remember { mutableStateOf("") }
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var showCancllation by remember {
        mutableStateOf(false)
    }


    var showTimerPicker by remember {
        mutableStateOf(false)
    }
    var showReminder by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableStateOf("Date / Time")
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        if (!leads.isEmpty())
            DynamicSelectTextField(leads, onOptionSelected = { selectedDate ->
                if (leads.find { it.getTitle() == selectedDate }?.id == 8) {
                    showCancllation = true
                } else {
                    cancelationTitle = ""
                    showCancllation = false
                }
                leadSelected = selectedDate
            })
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(vertical = 8.dp),
            value = note,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Start),
            placeholder = {
                Text(
                    text = stringResource(R.string.notes),
                    style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Start)
                )
            },

            colors = TextFieldDefaults.textFieldColors(
                cursorColor = colorResource(id = R.color.black),
                disabledLabelColor = colorResource(id = R.color.blue),
                focusedIndicatorColor = colorResource(id = R.color.transparent),
                unfocusedIndicatorColor = colorResource(id = R.color.transparent)
            ),
            onValueChange = {
                note = it
            },
            shape = RoundedCornerShape(8.dp),
        )
        if (!showCancllation)
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.AddAlert,
                        contentDescription = null,
                        tint = colorResource(id = R.color.blue)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.set_reminder))
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = showReminder,
                        onCheckedChange = { showReminder = it }
                    )
                }
                if (showReminder) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDatePicker = true
                            }
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = selectedDate,
                                style = TextStyle(
                                    fontSize = 16.sp
                                ),
                            )
                            Image(
                                painter = painterResource(R.drawable.vuesax_linear_calendar),
                                contentDescription = "",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }


        if (showCancllation)
            Column {
                Text(
                    text = stringResource(R.string.cancellation_reason), modifier =
                    Modifier.padding(top = 16.dp),
                    style = TextStyle(fontSize = 14.sp)
                )
                if (!cancelationTitleReason.isEmpty())
                    RadioButtonGroup(cancelationTitleReason, onCancelationResionSelect = {
                        cancelationTitle = it
                    })
            }

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue)),
            onClick = {
                if (selectedDate == "Date / Time") {
                    onUpdateClick(leadSelected, note, cancelationTitle, selectedDate)
                } else {
                    val inputFormat = SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.US)
                    val outputFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US)
                    val date = inputFormat.parse(selectedDate)

                    date?.let {
                        onUpdateClick(leadSelected, note, cancelationTitle, outputFormat.format(it))
                    }
                }

            }) {
            Text(text = stringResource(R.string.update), Modifier.padding(vertical = 8.dp))
        }
    }
    if (showDatePicker)
        DatePickerModal(onDateSelected = {
            selectedDate = it
            showDatePicker = false
            showTimerPicker = true
        }, onDismiss = {
            showDatePicker = false
        })

    if (showTimerPicker)
        TimePickerDialog(onTimeSelected = {
            showTimerPicker = false
            selectedDate += " $it"
        }, onDismiss = {
            showTimerPicker = false
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    options: SnapshotStateList<AllLeadStatus>,
    onOptionSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0].getTitle()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropDown,
                    contentDescription = null
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
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = selectionOption.getTitle(),
                            style = TextStyle(fontSize = 14.sp)
                        )
                    },
                    onClick = {
                        selectedOptionText = selectionOption.getTitle()
                        expanded = false
                        onOptionSelected(selectionOption.getTitle())
                    }
                )
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
                text = stringResource(id = R.string.after_two_hour),
                style = TextStyle(
                    fontSize = 14.sp, color = colorResource(id = R.color.black)
                ),
            )
        }
    }
}

@Composable
fun RadioButtonGroup(
    cancelationTitleReason: SnapshotStateList<String>,
    onCancelationResionSelect: (String) -> Unit
) {
    var selectedOption by remember { mutableStateOf(cancelationTitleReason[0]) }
    val options = cancelationTitleReason
    onCancelationResionSelect(selectedOption)
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    modifier = Modifier.height(38.dp),
                    selected = (option == selectedOption),
                    onClick = {
                        selectedOption = option
                        onCancelationResionSelect(selectedOption)
                    },
                    colors = RadioButtonDefaults.colors(
                        colorResource(id = R.color.blue)
                    )
                )
                Text(
                    style = TextStyle(fontSize = 14.sp),
                    text = option,
                )
            }
        }
    }

}

@Composable
fun DatePickerModal(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()
    val mDate = remember { mutableStateOf("") }
    val mDatePickerDialog = DatePickerDialog(
        mContext, R.style.DialogTheme,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mYear/${mMonth + 1}/$mDayOfMonth"
            onDateSelected(mDate.value)
        }, mYear, mMonth, mDay
    )
    mDatePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
    mDatePickerDialog.setOnDismissListener {
        onDismiss.invoke()
    }
    mDatePickerDialog.show()

}

@Composable
fun TimePickerDialog(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    val timePickerDialog = TimePickerDialog(
        mContext, R.style.DialogTheme,
        { _, hourOfDay: Int, minute: Int ->
            // Format to 12-hour with AM/PM
            val cal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val formattedTime = format.format(cal.time)
            onTimeSelected(formattedTime)
        }, mHour, mMinute, false // `false` for 12-hour format
    )

    timePickerDialog.setOnDismissListener {
        onDismiss()
    }

    timePickerDialog.show()
}

//@Composable
//fun TimePickerDialog(
//    onTimeSelected: (String) -> Unit,
//    onDismiss: () -> Unit
//) {
//    val mContext = LocalContext.current
//    val mCalendar = Calendar.getInstance()
//    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
//    val mMinute = mCalendar[Calendar.MINUTE]
//    val mTime = remember { mutableStateOf("") }
//    val timePickerDialog = TimePickerDialog(
//        mContext, R.style.DialogTheme,
//        { _, mHour: Int, mMinute: Int ->
//            mTime.value = "$mHour:$mMinute"
//            onTimeSelected(mTime.value)
//        }, mHour, mMinute, false
//    )
//    timePickerDialog.setOnDismissListener {
//        onDismiss.invoke()
//    }
//    timePickerDialog.show()
//}
