package com.alef.souqleader.ui.presentation.allLeads

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.PostData
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.SharedViewModel
import kotlinx.coroutines.launch


@Composable
fun AllLeadsScreen(
    navController: NavController,
    modifier: Modifier,
    leadId: String?,
    mainViewModel: MainViewModel
) {
    val viewModel: AllLeadViewModel = hiltViewModel()
    viewModel.updateBaseUrl(AccountData.BASE_URL)
    var lead by remember { mutableStateOf(LeadsByStatusResponse()) }
    var page by remember { mutableIntStateOf(1) }

    LaunchedEffect(key1 = true) {
        when (leadId) {
            "100" -> {
                viewModel.delayLeads(page)
            }

            "200" -> {
                viewModel.duplicated(page)
            }

            else -> {
                leadId?.let {
                    viewModel.getLeadByStatus(it, page)
                }
            }
        }


        viewModel.viewModelScope.launch {
            viewModel.stateListOfLeads.collect {
                when (it) {
                    is Resource.Success -> {
                        lead = it.data!!
                        mainViewModel.showLoader = false
                    }

                    is Resource.Loading -> {
                        if (page == 1)
                            mainViewModel.showLoader = true
                    }

                    is Resource.DataError -> {
                        mainViewModel.showLoader = false
                    }
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.stateFilterLeads.collect {
                when (it) {
                    is Resource.Success -> {
                        lead = it.data!!
                        mainViewModel.showLoader = false
                    }

                    is Resource.Loading -> {

                    }

                    is Resource.DataError -> {
                        mainViewModel.showLoader = false
                    }
                }

            }
        }

    }

    Screen(navController, setKeyword = {
        if (it.isEmpty()) {
            page = 1
            leadId?.let { viewModel.getLeadByStatus(it, page = page) }
        } else {
                viewModel.leadsFilter(
                    FilterRequest(
                        name = it,status = leadId
                    )
                )
        }
    }, lead, leadId,page, loadMore = {
        leadId?.let {
            viewModel.getLeadByStatus(it, ++page)
        }
    })

}


@Composable
fun Screen(
    navController: NavController,
    setKeyword: (String) -> Unit,
    lead: LeadsByStatusResponse,
    leadId:String?,
    page: Int,
    loadMore: () -> Unit
) {
    val selectedIdList = remember { mutableStateListOf<String>() }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
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
            Search(stringResource(R.string.search), setKeyword = {
                setKeyword(it)
            }, onFilterClick = {
                Log.e("ss",leadId.toString())
                navController.navigate(Screen.FilterScreen.route.plus("/${leadId}")) {
                    launchSingleTop = true
                }
            })

            LazyColumn(
                Modifier.fillMaxWidth()
            ) {
                items(leadList) {
                    AllLeadsItem(it) { lead ->
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
    val ctx = LocalContext.current
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
                        Modifier
                            .size(60.dp, 40.dp)
                            .clickable {
                                val u = Uri.parse(
                                    "tel:"
                                            + lead.phone.toString()
                                )

                                // Create the intent and set the data for the
                                // intent as the phone number.
                                val i = Intent(Intent.ACTION_DIAL, u)
                                try {

                                    // Launch the Phone app's dialer with a phone
                                    // number to dial a call.
                                    ctx.startActivity(i)
                                } catch (s: SecurityException) {

                                    // show() method display the toast with
                                    // exception message.
                                    Toast
                                        .makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
                                        .show()
                                }

                            }
                    )
                    Image(
                        painterResource(R.drawable.sms_icon),
                        contentDescription = "",
                        Modifier.size(60.dp, 40.dp)
                    )
                    Image(
                        painterResource(R.drawable.mail_icon),
                        contentDescription = "",
                        Modifier
                            .size(60.dp, 40.dp)
                            .clickable {
                                ctx.sendMail(
                                    to = lead.email,
                                    subject = ""
                                )

                            }
                    )
                    Image(
                        painterResource(R.drawable.whats_icon),
                        contentDescription = "",
                        Modifier
                            .size(60.dp, 40.dp)
                            .clickable {
                                ctx.startActivity(
                                    // on below line we are opening the intent.
                                    Intent(
                                        // on below line we are calling
                                        // uri to parse the data
                                        Intent.ACTION_VIEW,
                                        Uri.parse(
                                            // on below line we are passing uri,
                                            // message and whats app phone number.
                                            java.lang.String.format(
                                                "https://api.whatsapp.com/send?phone=%s&text=%s",
                                                lead.phone,
                                                ""
                                            )
                                        )
                                    )
                                )
                            }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(text: String, setKeyword: (String) -> Unit, onFilterClick: () -> Unit) {
    var keyword by remember { mutableStateOf("") }
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
                value = keyword,
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
                    keyword = it
                    setKeyword(keyword)
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

fun Context.sendMail(to: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // TODO: Handle case where no email app is available
    } catch (t: Throwable) {
        // TODO: Handle potential other type of exceptions
    }
}

