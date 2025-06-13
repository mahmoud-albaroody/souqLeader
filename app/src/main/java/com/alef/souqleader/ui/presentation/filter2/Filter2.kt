package com.alef.souqleader.ui.presentation.filter2

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alef.souqleader.R
import com.alef.souqleader.ui.presentation.addlead.TextFiledItem
import com.alef.souqleader.ui.presentation.addlead.TextFiledItem1
import com.alef.souqleader.ui.theme.*


@Composable
fun Filter2Screen(modifier: Modifier) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    Filter2()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Filter2() {
    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(horizontal = 24.dp)
    ) {

        Column() {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.budget),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
            )
            Budget()
            LazyColumn(content = {
                items(4) {
                    FilterChoose()
                }
            })
        }
        Row(
            Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedButton(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                onClick = { /*TODO*/ }) {
                Text(text = stringResource(R.string.reset), Modifier.padding(vertical = 8.dp))
            }
            Button(modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue)),
                onClick = { /*TODO*/ }) {
                Text(
                    text = stringResource(R.string.show),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun Budget() {
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(Modifier.weight(2f)) {
            TextFiledItem1("0",true){

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
            TextFiledItem1(stringResource(R.string.any),true){

            }
        }
    }
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
                cursorColor = colorResource(id = R.color.black),
                disabledLabelColor =colorResource(id = R.color.blue),
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

@Preview
@Composable
fun FilterChoose() {
    Column(Modifier.padding(top = 8.dp)) {
        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth() // Set the thickness of the vertical line
                .background(colorResource(id = R.color.lightGray))  // Set the color of the vertical line
        )
        Text(
            text = stringResource(R.string.budget), Modifier.padding(top = 10.dp),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp)
        )


        LazyRow(modifier = Modifier.padding(vertical = 16.dp), content = {
            items(6) {
                BudgetItem()
            }
        })

    }

}

@Preview
@Composable
fun BudgetItem() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.gray)),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.transparent))
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 9.dp, horizontal = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.dev_one),
                style = TextStyle(
                    fontSize = 14.sp, color = colorResource(id = R.color.black)
                ),
            )
        }
    }
}

@Preview
@Composable
fun RangeSliderExample() {
    var sliderPosition by remember { mutableStateOf(0f..100f) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RangeSlider(
            value = sliderPosition,
            steps = 5,
            onValueChange = { range -> sliderPosition = range },
            valueRange = 0f..100f,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.blue),
                activeTrackColor = colorResource(id = R.color.blue),
                activeTickColor = colorResource(id = R.color.blue), inactiveTickColor = colorResource(id = R.color.lightGray),
                inactiveTrackColor = colorResource(id = R.color.lightGray)
            )
        )
//        Text(text = sliderPosition.toString())
    }
}
