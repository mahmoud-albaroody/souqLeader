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
    val viewModel: JobApplicationViewModel = hiltViewModel()
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
fun JobList(){
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxSize(),

        elevation = CardDefaults.cardElevation(4.dp)
    ) {
    LazyColumn(
        modifier = Modifier.background(Color.White)
            .fillMaxSize(),
        content = {
            items(4) {
                JobItem()
            }
        })
}
}

@Preview(showBackground = true)
@Composable
fun JobItem() {

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
                text = "Mahmoud albaroody", fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Image(painter = painterResource(id = R.drawable.ic_lock_open), contentDescription = "")
        }
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
                    text = "01020343274",
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
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = R.drawable.ic_email), contentDescription = "")
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = "mahmoud.elbaroody@gmail.com",
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
                .fillMaxWidth() // Set the thickness of the vertical line
                .background(colorResource(id = R.color.lightGray))  // Set the color of the vertical line
        )
    }
}

