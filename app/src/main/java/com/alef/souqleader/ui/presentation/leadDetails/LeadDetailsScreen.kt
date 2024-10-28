package com.alef.souqleader.ui.presentation.leadDetails


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.Action
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.LeadDetailsResponse
import com.alef.souqleader.ui.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun LeadDetailsScreen(
    navController: NavController, leadId: String?, mainViewModel: MainViewModel
) {
    val leadDetailsViewModel: LeadDetailsViewModel = hiltViewModel()
    var lead by remember { mutableStateOf(LeadDetailsResponse()) }


    LaunchedEffect(key1 = true) {
        leadId?.let { leadDetailsViewModel.getLeadDetails(it) }
        leadDetailsViewModel.viewModelScope.launch {
            leadDetailsViewModel.leadDetails.collect {
                when (it) {
                    is Resource.Success -> {
                        lead = it.data!!
                        mainViewModel.showLoader = false
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
    }

    Column {
        lead.data?.lead?.let { TopElement(it) }
        Text(
            modifier = Modifier
                .padding(start = 24.dp),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            ),
            text = stringResource(R.string.previous_actions)
        )
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            lead.data?.actions?.let {
                items(it) { action ->
                    LeadItem(action)
                }

            }
        }
    }
}


@Composable
fun TopElement(leadDetails: Lead) {
    val configuration = LocalConfiguration.current
    val ctx = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .clickable {
//                lead.selected = !lead.selected
//                selected = lead.selected
//                onItemClick(lead)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_menu_icon),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.name ?: "", fontSize = 11.sp
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_phone_24),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.phone ?: "", fontSize = 11.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.sales_name_icon),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.sales_name ?: "", fontSize = 11.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.project_icon), contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.project_name ?: "", fontSize = 11.sp
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.coin), contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.budget ?: "", fontSize = 11.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vuesax_linear_calendar),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.action_date ?: "", fontSize = 11.sp
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_email_24),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.email ?: "", fontSize = 11.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.message_text),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.note ?: "", fontSize = 11.sp
                    )
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.sales_man), fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.sales_name ?: "", fontSize = 11.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.communication_way), fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.communication_way ?: "", fontSize = 11.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.cancel_reason), fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = leadDetails.cancel_reason ?: "", fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun LeadItem(action: Action) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
        ,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp, horizontal = 16.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.element_1), contentDescription = ""
                )
                action.status?.let {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = it, fontSize = 11.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sales_name_icon),
                    contentDescription = ""
                )
                action.sales?.let {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = it, fontSize = 11.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.message_text), contentDescription = ""
                )
                action.note?.let {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = it, fontSize = 11.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp, bottom = 8.dp
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.vuesax_linear_calendar),
                    contentDescription = ""
                )
                action.created_at?.let {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = it, fontSize = 11.sp
                    )
                }
            }

        }
    }
}