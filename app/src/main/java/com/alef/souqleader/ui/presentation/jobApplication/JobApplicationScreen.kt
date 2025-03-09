package com.alef.souqleader.ui.presentation.jobApplication

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.JobAppRequest
import com.alef.souqleader.data.remote.dto.Jobapps
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.allLeads.sendMail
import com.alef.souqleader.ui.presentation.jobFilter.JobFilter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun JobApplicationScreen(
    navController: NavController,
    jobId: String,
    mainViewModel: MainViewModel
) {
    val viewModel: JobApplicationViewModel = hiltViewModel()
    val jobAppList = remember { mutableStateListOf<Jobapps>() }
    var index by remember { mutableIntStateOf(0) }
    val ctx = LocalContext.current

    mainViewModel.showFilterIcon = true
    LaunchedEffect(key1 = true) {
        viewModel.jobApp(JobAppRequest(jobId = jobId))
        viewModel.viewModelScope.launch {
            viewModel.jobAppResponse.collect {
                jobAppList.clear()
                jobAppList.addAll(it)
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.unLockResponse.collect {

                if (it.data == null) {
                    Toast.makeText(ctx, it.message.toString(), Toast.LENGTH_LONG).show()
                } else {
                    jobAppList.removeAt(index)
                    jobAppList.add(index, it.data!!)
                }
            }
        }

    }

    DisposableEffect(Unit) {
        onDispose {
            mainViewModel.showFilterIcon = false
        }
    }
    Column {

        JobList(jobAppList, viewModel, mainViewModel, onFilterClick = {
            it.jobId = jobId
            viewModel.jobApp(it)
        }, onCancelClick = {

        }, onRestClick = {
            viewModel.jobApp(JobAppRequest(jobId = jobId))
        }, onItemClick = {
            val postJson = it.toJson()
            navController.navigate(
                Screen.JobApplicationDetailsScreen.route.plus("?" + Screen.JobApplicationDetailsScreen.objectName + "=${postJson}")
            )
        }, onUnlock = {
            index = jobAppList.indexOf(it)
            viewModel.unlock(it.id.toString())
        })
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JobList(
    jobAppList: SnapshotStateList<Jobapps>,
    viewModel: JobApplicationViewModel,
    mainViewModel: MainViewModel,
    onItemClick: (Jobapps) -> Unit,
    onCancelClick: () -> Unit,
    onRestClick: () -> Unit,
    onFilterClick: (JobAppRequest) -> Unit,
    onUnlock: (Jobapps) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            content = {
                item {
                    if (mainViewModel.showFilter) {
                        AnimatedVisibility(
                            visible = mainViewModel.showFilter,
                            enter = slideInVertically { it } + fadeIn(),
                            exit = slideOutVertically { it } + fadeOut()
                        ) {
                            JobFilter(viewModel = viewModel,
                                onRestClick = {
                                    onRestClick()
                                    mainViewModel.showFilter = false
                                }, onCancelClick = {
                                    onCancelClick()
                                    mainViewModel.showFilter = false
                                }, onFilterClick = {
                                    onFilterClick(it)
                                    mainViewModel.showFilter = false
                                })
                        }
                    }
                }
                items(jobAppList) {
                    val dismissState = rememberDismissState()
                    LaunchedEffect(dismissState.currentValue) {
                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
//                            delay(600) // Wait for animation before removing item
//                            jobAppList.remove(it)
                            onUnlock(it)
                            dismissState.reset()
                        }
                    }
                    if (!dismissState.isDismissed(DismissDirection.EndToStart)) {
                        SwipeToDismiss(
                            state = dismissState,
                            directions = if (it.is_locked == 1) setOf(DismissDirection.EndToStart) else emptySet(),
                            background = {
                                if (it.is_locked == 1)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp)
                                            .background(colorResource(id = R.color.blue))
                                            .alpha(if (dismissState.targetValue == DismissValue.Default) 0.5f else 1f)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 8.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_lock_open),
                                                    colorFilter = ColorFilter.tint(Color.White),
                                                    contentDescription = ""
                                                )
                                                Text(
                                                    modifier = Modifier.padding(top = 8.dp),
                                                    fontSize = 11.sp,
                                                    text = stringResource(R.string.unlock_cv),
                                                    color = colorResource(id = R.color.white)
                                                )
                                            }
                                        }
                                    }
                            },
                            dismissContent = {
                                JobItem(it) {
                                    onItemClick(it)
                                }
                            }

                        )
                    }
                }
            })
    }
}

@Composable
fun JobItem(jobapps: Jobapps, onItemClick: (Jobapps) -> Unit) {
    val ctx = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(jobapps)
            }
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = jobapps.name, fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter =
                if (jobapps.is_locked == 0)
                    painterResource(id = R.drawable.ic_lock_open)
                else painterResource(id = R.drawable.ic_lock_outline), contentDescription = ""
            )
        }


        if (jobapps.work_experience.isNotEmpty())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(

                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    text = if (jobapps.work_experience.isEmpty()) "" else jobapps.work_experience[0].job_title
                )
            }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                text = jobapps.country.getCountry() + " , " +
                        jobapps.city.getCity() + " , " +
                        jobapps.area.getArea()
            )
            Text(
                text = stringResource(R.string.exp_salary) + " " + jobapps.expected_salary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.baseline_call_24),
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = jobapps.phone,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.blue)
                )
            }
            if (jobapps.is_locked == 0)
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            val u = Uri.parse(
                                "tel:"
                                        + jobapps.phone.toString()
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
                        },
                    painter = painterResource(id = R.drawable.call_icon),
                    contentDescription = ""
                )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_email), contentDescription = ""
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = jobapps.email,
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.blue)
                )
            }
            if (jobapps.is_locked == 0)
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            ctx.sendMail(
                                to = jobapps.email,
                                subject = ""
                            )
                        },
                    painter = painterResource(id = R.drawable.mail_icon),
                    contentDescription = ""
                )

        }
        Divider(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.lightGray))
        )
    }
}

