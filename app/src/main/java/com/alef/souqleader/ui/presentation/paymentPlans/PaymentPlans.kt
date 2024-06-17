package com.alef.souqleader.ui.presentation.paymentPlans

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Plan
import com.alef.souqleader.ui.presentation.dashboardScreen.DashboardViewModel
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Grey

@Composable
fun PaymentPlansScreen(modifier: Modifier) {
    val viewModel: PaymentPlanViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.getPaymentPlan()
    }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        items(viewModel.statePaymentPlan) {
            PaymentPlansItem(it)
        }
    }
}


@Composable
fun PaymentPlansItem(plan: Plan) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 4.8f)
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .weight(2f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .weight(2f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(
                            vertical = 16.dp,
                            horizontal = 16.dp
                        )
                ) {
                    Text(
                        text = plan.currency + plan.price + "/" + plan.trail_days,
                        style = TextStyle(
                            fontSize = 18.sp, color = Blue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = plan.getPlanName(), style = TextStyle(
                            fontSize = 13.sp, fontWeight = FontWeight.SemiBold
                        )
                    )
                }
//                Image(
//                    painterResource(R.drawable.select_box),
//                    contentDescription = "",
//                )
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "You will get unlimit access to every module you want, " + plan.frequency + " user",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Grey
                    )

                )

            }
        }
    }
}
