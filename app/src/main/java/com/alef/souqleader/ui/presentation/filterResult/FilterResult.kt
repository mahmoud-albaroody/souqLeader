package com.alef.souqleader.ui.presentation.filterResult

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.allLeads.AllLeadViewModel
import com.alef.souqleader.ui.theme.*
import kotlinx.coroutines.launch
import org.json.JSONObject


@Composable
fun FilterResultScreen(
    navController: NavController, modifier: Modifier, obj: JSONObject?
) {

    val viewModel: AllLeadViewModel = hiltViewModel()
    //   viewModel.updateBaseUrl(AccountData.BASE_URL)
    val leadList by remember { mutableStateOf(ArrayList<Lead>()) }
    var name: String? = null
    var status: String? = null
    var channel: String? = null
    var project: String? = null
    var communicationWay: String? = null
    var budget: String? = null

    obj?.let {
        if (obj.has("name")) {
            name = obj.getString("name")
        }
        if (obj.has("status")) {
            status = obj.getString("status")
        }
        if (obj.has("channel")) {
            channel = obj.getString("channel")
        }
        if (obj.has("project")) {
            project = obj.getString("project")
        }
        if (obj.has("communication_way")) {
            communicationWay = obj.getString("communication_way")
        }
        if (obj.has("budget")) {
            budget = obj.getString("budget")
        }

    }
    LaunchedEffect(key1 = true) {
        viewModel.leadsFilter(
            FilterRequest(
                name = name, status = status,
                project = project, communication_way = communicationWay,
                channel = channel, budget = budget
            )
        )


        viewModel.viewModelScope.launch {
            viewModel.stateListOfLeads.collect {
                leadList.clear()
                it.data?.data?.let { it1 -> leadList.addAll(it1) }
            }
        }
    }
    Screen(navController, leadList)

}


@Composable
fun Screen(navController: NavController, stateListOfLeads: List<Lead>) {
    var selectedId by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val selectedIdList = remember { mutableStateListOf<String>() }
    Box(
        Modifier
            .background(colorResource(id = R.color.white))
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxSize()
    ) {
        Column(Modifier.height(screenHeight)) {


            LazyColumn(
                Modifier.fillMaxWidth()
            ) {
                items(stateListOfLeads) {
                    AllLeadsItem(it) { lead ->
//                        stateListOfLeads.forEach {
//                            it.selected = false
//                        }
                        if (selectedIdList.find { it.toInt() == lead.id } == null) {
                            selectedIdList.add(lead.id.toString())
                        } else {
                            selectedIdList.remove(lead.id.toString())
                        }
                    }
                }
            }
        }
        if (selectedIdList.isNotEmpty())
            Button(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue2)),
                onClick = {

                    val joinedString = selectedIdList.joinToString(separator = ",")
                    navController.navigate(Screen.LeadUpdateScreen.route.plus("/${joinedString}"))
                }) {
                Text(
                    text = stringResource(R.string.add_action),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
    }
}

@Composable
fun AllLeadsItem(lead: Lead, onItemClick: (Lead) -> Unit) {
    var selected by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    selected = lead.selected
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3f)
            .padding(6.dp)
            .clickable {
                lead.selected = !lead.selected
                selected = lead.selected
                onItemClick(lead)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (selected)
                Image(
                    painterResource(R.drawable.select_box),
                    contentDescription = "",
                    Modifier.align(Alignment.TopEnd)
                )

            Column(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween

            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp
                        )
                        .padding(end = 50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Image(
                        painterResource(R.drawable.client_placeholder),
                        contentDescription = "",
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .padding(top = 6.dp)
                    ) {
                        Text(
                            text = lead.name ?: "", style = TextStyle(
                                fontSize = 16.sp, color = colorResource(id = R.color.blue)
                            )
                        )
                        Text(
                            text = lead.phone ?: "", style = TextStyle(
                                fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                }

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painterResource(R.drawable.sales_name_icon),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = lead.sales_name ?: "", style = TextStyle(
                                fontSize = 14.sp,
                            ), modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    lead.project_name.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                painterResource(R.drawable.project_icon),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = it ?: "", style = TextStyle(
                                    fontSize = 14.sp,
                                ), modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    lead.budget?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                painterResource(R.drawable.coin),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp)
                            )

                            Text(
                                text = it, style = TextStyle(
                                    fontSize = 14.sp,
                                ), modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.notes_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(20.dp)
                    )
                    lead.note?.let {
                        Text(
                            text = it, style = TextStyle(
                                fontSize = 11.sp, color = colorResource(id = R.color.gray)
                            ), modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painterResource(R.drawable.call_icon),
                        contentDescription = "",
                        Modifier.size(60.dp, 40.dp)
                    )
                    Image(
                        painterResource(R.drawable.sms_icon),
                        contentDescription = "",
                        Modifier.size(60.dp, 40.dp)
                    )
                    Image(
                        painterResource(R.drawable.mail_icon),
                        contentDescription = "",
                        Modifier.size(60.dp, 40.dp)
                    )
                    Image(
                        painterResource(R.drawable.whats_icon),
                        contentDescription = "",
                        Modifier.size(60.dp, 40.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(text: String, onFilterClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painterResource(R.drawable.search_icon),
                modifier = Modifier
                    .size(30.dp)
                    .weight(0.5f),
                contentDescription = "",
            )
            TextField(
                modifier = Modifier.weight(3f),
                value = "",
                placeholder = {
                    Text(text = text)
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = colorResource(id = R.color.black),
                    disabledLabelColor = colorResource(id = R.color.blue),
                    focusedIndicatorColor = colorResource(id = R.color.transparent),
                    unfocusedIndicatorColor = colorResource(id = R.color.transparent)
                ),
                onValueChange = {

                },
//                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )
            Image(painterResource(R.drawable.filter_icon),
                contentDescription = "",
                Modifier
                    .size(30.dp)
                    .weight(0.5f)
                    .clickable {
                        onFilterClick.invoke()
                    })
        }
    }

}
