package com.alef.souqleader.ui.presentation.leadUpdate

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.LeadStatus
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Grey
import com.alef.souqleader.ui.theme.White


@Composable
fun LeadUpdateScreen(navController: NavController, modifier: Modifier) {
    val viewModel: LeadUpdateViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.getLeads("0")
        //    viewModel.updateMulti()

    }
    viewModel.stateListOfLeads.value?.let { it1 -> LeadUpdate(it1) }
    //
}


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun LeadUpdate(leads: List<Lead>) {
    Column(
        Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        DynamicSelectTextField(leads)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = "",
            placeholder = {
                Text(text = stringResource(R.string.notes))
            },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                disabledLabelColor = Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {

            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )

        Card(
            Modifier
                .fillMaxWidth()
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
                    text = "Date / Time",
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

        Text(
            text = "Reminder", modifier =
            Modifier.padding(top = 16.dp, bottom = 8.dp)
        )
        RadioButtonGroup()
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            value = "",
            placeholder = {
                Text(
                    text = "Write another reason", style = TextStyle(
                        color = Grey
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                disabledLabelColor = Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            onValueChange = {

            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
        )


        LazyRow(content = {
            items(5) {
                ReminderItem()
            }
        })

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(Blue),
            onClick = { /*TODO*/ }) {
            Text(text = "UPDATE", Modifier.padding(vertical = 8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    options: List<Lead>,
    onOptionSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0].name) }

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
                cursorColor = Color.Black,
                disabledLabelColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
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
                    text = { Text(text = selectionOption.name) },
                    onClick = {
                        selectedOptionText = selectionOption.name
                        expanded = false
                        onOptionSelected(selectionOption.name)
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
                text = "After Two Hour",
                style = TextStyle(
                    fontSize = 14.sp, color = Color.Black
                ),
            )
        }
    }
}

@Composable
fun RadioButtonGroup() {
    var selectedOption by remember { mutableStateOf("Not interested") }
    val options = listOf(
        "Not interested",
        "Low Budget",
        "Wrong Number",
        "Another Location",
        "Another Reasons"
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
                        Blue
                    )
                )
                Text(
                    text = option,

                    )
            }
        }
    }
}


