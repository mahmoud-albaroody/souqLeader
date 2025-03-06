package com.alef.souqleader.ui.presentation.jobApplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.JobAppRequest
import com.alef.souqleader.data.remote.dto.Jobapps
import kotlinx.coroutines.launch


@Composable
fun JobApplicationScreen(navController: NavController,jobId:String) {
    val viewModel: JobApplicationViewModel = hiltViewModel()
    val jobAppList = remember { mutableStateListOf<Jobapps>() }

    LaunchedEffect(key1 = true) {
        viewModel.jobApp(JobAppRequest(jobId = jobId))
        viewModel.viewModelScope.launch {
            viewModel.jobAppResponse.collect {
                jobAppList.addAll(it)
            }
        }
    }

    JobList(jobAppList)
}


@Composable
fun JobList(jobAppList: SnapshotStateList<Jobapps>) {
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
                items(jobAppList) {
                    JobItem(it)
                }
            })
    }
}

@Composable
fun JobItem(jobapps: Jobapps) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = jobapps.name, fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Image(
                painter =
                if (jobapps.is_locked == 0)
                    painterResource(id = R.drawable.ic_lock_open)
                else painterResource(id = R.drawable.ic_lock_outline), contentDescription = ""
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if(jobapps.work_experience.isEmpty())"" else jobapps.work_experience[0].job_title
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(    text = jobapps.country.getCountry() + " , " +
                    jobapps.city.getCity() + " , " +
                    jobapps.area.getArea())
            Text(text = jobapps.expected_salary)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_call_24),
                    contentDescription = ""
                )

                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = jobapps.phone,
                    color = colorResource(id = R.color.blue)
                )
            }
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.call_icon),
                contentDescription = ""
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.ic_email), contentDescription = "")
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = jobapps.email,
                    color = colorResource(id = R.color.blue)
                )
            }
            Image(
                modifier = Modifier.size(30.dp),
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

