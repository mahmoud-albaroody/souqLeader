package com.alef.souqleader.ui.presentation.addlead

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alef.souqleader.R
import com.alef.souqleader.ui.presentation.login.LoginItem
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Grey
import com.alef.souqleader.ui.theme.LightGrey
import com.alef.souqleader.ui.theme.White


@Composable
fun AddLeadScreen(modifier: Modifier) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    AddLead()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddLead() {
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 16.dp, horizontal = 32.dp)
    ) {
        Column(
            Modifier
                .verticalScroll(scrollState)
                .weight(12f)
        ) {
            TextFiledItem("Name")
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(Modifier.weight(1f)) {
                    TextFiledItem("+2")
                }
                Box(Modifier.weight(3f)) {
                    TextFiledItem("Mobile")
                }
            }
            DynamicSelectTextField()
            TextFiledItem("E-mail")
            DynamicSelectTextField()
            DynamicSelectTextField()
            TextFiledItem("E-mail")
            DynamicSelectTextField()
            TextFiledItem("E-mail")
            TextFiledItem("E-mail")
            TextFiledItem("E-mail")
        }
        Button(modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(Blue),
            onClick = { /*TODO*/ }) {
            Text(text = "ADD LEAD", Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TextFiledItem(text: String) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        value = "",
        placeholder = {
            Text(text = text)
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    options: List<String> = listOf("Lead Stage", "Option 2", "Option 3", "Option 4", "Option 5"),
    onOptionSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

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
                    text = { Text(text = selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onOptionSelected(selectionOption)
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


