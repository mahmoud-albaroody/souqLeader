package com.alef.souqleader.ui.presentation.jobPost

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.JopPersion
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.navigation.Screen
import kotlinx.coroutines.launch


@Composable
fun JobPostScreen(navController: NavController) {
    val viewModel: JobPostViewModel = hiltViewModel()
    val jobList = remember { mutableStateListOf<JopPersion>() }

    LaunchedEffect(key1 = true) {

        viewModel.allJob()
        viewModel.viewModelScope.launch {
            viewModel.allJob.collect {
                jobList.addAll(it)
            }
        }
    }
    JobPostList(jobList, onItemClick = {
        navController.navigate(Screen.JobApplicationScreen.route.plus("/${it.id}"))
    })
}


@Composable
fun JobPostList(jobs: SnapshotStateList<JopPersion>,onItemClick:(JopPersion)->Unit) {


    Card(
        shape = RoundedCornerShape(16.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        LazyColumn(modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(), content = {
            items(jobs) { jobPerson ->
                JobPostItem(jobPerson, jobs.last()){
                   onItemClick(jobPerson)
                }
            }
        })
    }
}


@Composable
fun JobPostItem(jobPerson: JopPersion, lastPerson: JopPersion,onItemClick: (JopPersion) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth().clickable {
                onItemClick(jobPerson)
            }
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 4.dp), verticalArrangement = Arrangement.Center
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
                text = jobPerson.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.blue2)
            )
            Text(
                modifier = Modifier,
                text = jobPerson.workplace,
                fontSize = 11.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                color = colorResource(id = R.color.gray),
                text = jobPerson.country.getCountry() + " , " +
                        jobPerson.city.getCity() + " , " +
                        jobPerson.area.getArea()
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            fontSize = 11.sp,
            color = colorResource(id = R.color.gray),
            text = jobPerson.career_level.careerLevel()
        )


        Row(
            modifier = Modifier
                .fillMaxWidth().padding(top = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            LazyRow(content = {
                items(jobPerson.category) { category ->
                    Box(modifier = Modifier.padding(end = 4.dp)) {
                        Text(
                            modifier = Modifier
                                .background(colorResource(id = R.color.light_blue_400))
                                .padding(horizontal = 4.dp),
                            text = category.getCategory(),
                            fontSize = 10.sp,
                            color = colorResource(id = R.color.blue)
                        )
                    }
                }
            })
            LazyRow(content = {
                items(jobPerson.type) { type ->
                    Box(modifier = Modifier.padding(end = 4.dp)) {
                        Text(
                            modifier = Modifier
                                .background(colorResource(id = R.color.blue1))
                                .padding(horizontal = 4.dp),
                            text = type.getTitle(),
                            fontSize = 10.sp,
                            color = colorResource(id = R.color.purple_700)
                        )
                    }
                }
            })
        }

        if (lastPerson != jobPerson)
            Divider(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.lightGray))
            )
    }
}

