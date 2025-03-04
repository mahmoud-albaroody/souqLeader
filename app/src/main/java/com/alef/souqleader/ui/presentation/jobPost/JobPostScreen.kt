package com.alef.souqleader.ui.presentation.jobPost

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alef.souqleader.R


@Composable
fun JobApplicationScreen(navController: NavController) {
    val viewModel: JobPostViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        //  viewModel.getPaymentPlan()
//        viewModel.allUsers()
//        viewModel.viewModelScope.launch {
//            viewModel.allUsers.collect {
//                userList.addAll(it)
//            }
//        }
    }
}


@Preview
@Composable
fun JobPostList(){
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
            items(4) {
                JobPostItem()
            }
        })
}
}

@Preview(showBackground = true)
@Composable
fun JobPostItem() {

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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier,
                text = "Mahmoud albaroody", fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.blue)
            )
            Text(
                modifier = Modifier,
                text = "Remote", fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Senior Sales"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(text = "Egypt cairo")
            Text(text = "Exp Salary")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top =8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    modifier = Modifier.background( colorResource(id = R.color.light_blue_400))
                        .padding(horizontal = 4.dp, vertical = 3.dp),
                    text = "sales",
                    color = colorResource(id = R.color.blue2)
                )
                Text(
                    modifier = Modifier.background( colorResource(id = R.color.light_blue_400))
                        .padding(horizontal = 4.dp, vertical = 3.dp),
                    text = "marketing",
                    color = colorResource(id = R.color.blue2)
                )

            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    modifier = Modifier.background( colorResource(id = R.color.light_blue_400))
                        .padding(horizontal = 4.dp, vertical = 3.dp),
                    text = "sales",
                    color = colorResource(id = R.color.blue2)
                )
                Text(
                    modifier = Modifier.background( colorResource(id = R.color.light_blue_400))
                        .padding(horizontal = 4.dp, vertical = 3.dp),
                    text = "marketing",
                    color = colorResource(id = R.color.blue2)
                )

            }

        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp)
                .height(1.dp)
                .fillMaxWidth() // Set the thickness of the vertical line
                .background(colorResource(id = R.color.lightGray))  // Set the color of the vertical line
        )
    }
}

