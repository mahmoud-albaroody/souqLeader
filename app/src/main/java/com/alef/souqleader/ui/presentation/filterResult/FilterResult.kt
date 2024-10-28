package com.alef.souqleader.ui.presentation.filterResult

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
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
    val context = LocalContext.current

    var lead by remember { mutableStateOf(LeadsByStatusResponse()) }
    var page by remember { mutableIntStateOf(1) }
    var name: String? = null
    var status: String? = null
    var channel: String? = null
    var project: String? = null
    var communicationWay: String? = null
    var budgetFrom: String? = null
    var budgetTo: String? = null

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
        if (obj.has("budget_from")) {
            budgetFrom = obj.getString("budget_from")
        }
        if (obj.has("budget_to")) {
            budgetTo = obj.getString("budget_to")
        }
    }
    LaunchedEffect(key1 = true) {
        if (budgetFrom == context.getString(R.string.amount)) {
            budgetFrom = null
        }
        if (budgetTo == context.getString(R.string.amount)) {
            budgetTo = null
        }
        viewModel.leadsFilter(
            FilterRequest(
                searchField = name, status = status,
                project = project, communication_way = communicationWay,
                channel = channel, budget_from = budgetFrom, budget_to = budgetTo, page = page
            )
        )


        viewModel.viewModelScope.launch {
            viewModel.stateFilterLeads.collect {
                it.data?.let { leadResponse ->
                    lead = leadResponse
                }
            }
        }
    }
    Screen(navController, lead, page, loadMore = {
        viewModel.leadsFilter(
            FilterRequest(
                status = status,
                project = project, communication_way = communicationWay,
                channel = channel, budget_from = budgetFrom, budget_to = budgetTo, page = ++page,
                searchField = name
            )
        )
    })

}


@Composable
fun Screen(
    navController: NavController, lead: LeadsByStatusResponse,
    page: Int,
    loadMore: () -> Unit
) {
    var selectedId by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val selectedIdList = remember { mutableStateListOf<String>() }
    val leadList = remember { mutableStateListOf<Lead>() }
    lead.data?.let { it1 ->
        if (page == 1) {
            leadList.clear()
            leadList.addAll(it1)
        } else {
            leadList.addAll(it1)
        }
    }
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
                items(leadList) {
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
                if (lead.info?.pages != null && lead.info.pages != 0)
                    if (lead.info.pages > page) {
                        item {
                            if (leadList.isNotEmpty()) {
                                loadMore()
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.width(16.dp),
                                    color = MaterialTheme.colorScheme.secondary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
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
    selected = lead.selected == true
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3f)
            .padding(6.dp)
            .clickable {
                lead.selected = !lead.selected!!
                selected = lead.selected!!
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
