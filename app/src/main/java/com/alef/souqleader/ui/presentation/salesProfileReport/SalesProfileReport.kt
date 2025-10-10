package com.alef.souqleader.ui.presentation.salesProfileReport

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.SalesProfileReport
import com.alef.souqleader.data.remote.dto.StatusCounter
import com.alef.souqleader.domain.model.AccountData

import com.alef.souqleader.ui.presentation.meetingReport.MyBarChart

import kotlin.system.exitProcess

@Composable
fun SalesProfileReportScreen(userId: String?) {
    val viewModel: SalesProfileReportViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        userId?.let {
            viewModel.getSalesProfileReport(it)
        }

    }
    viewModel.salesProfileReport?.let { SalesProfileReportItem(it) }
}

class SalesProfileReportPreviewParamProvider(override val values: Sequence<SalesProfileReport>) :
    PreviewParameterProvider<SalesProfileReport>


@Composable
fun SalesProfileReportItem(
    @PreviewParameter(SalesProfileReportPreviewParamProvider::class) salesProfileReport: SalesProfileReport
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val ctx = LocalContext.current
    var stageHeight by remember { mutableIntStateOf(200) }
    var size by remember { mutableIntStateOf(5) }
    var text by remember { mutableStateOf("") }
    text = ctx.getString(R.string.view_all)

    LazyColumn(
        // and not having a Modifier that could return non-infinite max height contraint
        modifier = Modifier.fillMaxSize()
    ) {

        item {
            Column(
                modifier = Modifier
                    .background(colorResource(id = R.color.white))
                    .fillMaxSize()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
            ) {

                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // ,

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(160.dp)
                    ) {
                        Column(Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    if (salesProfileReport.user.image?.isNotEmpty() == true) {
                                        AccountData.BASE_URL + salesProfileReport.user.image
                                    } else {
                                        R.drawable.user_profile_placehoder
                                    }
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .border(
                                        2.dp,
                                        colorResource(id = R.color.lightGray),
                                        CircleShape
                                    )
                            )
                            Text(
                                modifier = Modifier.padding(top = 16.dp),
                                text = salesProfileReport.user.name ?: "",
                                style = TextStyle(
                                    fontSize = 16.sp, color = colorResource(id = R.color.blue)
                                ),
                            )
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = salesProfileReport.user.role ?: "",
                                    style = TextStyle(
                                        fontSize = 13.sp, fontWeight = FontWeight.SemiBold
                                    ),
                                )
                            }

                        }
                    }

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(160.dp)
                    ) {
                        Column(
                            Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = salesProfileReport.total_calls ?: "",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = colorResource(id = R.color.blue),
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            Text(
                                modifier = Modifier.padding(top = 4.dp),
                                text = stringResource(id = R.string.total_of_calls),
                                style = TextStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                ),
                            )
                            Column(
                                Modifier.padding(top = 24.dp),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.answer),
                                        style = TextStyle(
                                            fontSize = 13.sp
                                        ),
                                    )
                                    Text(
                                        text = salesProfileReport.answer ?: "",
                                        style = TextStyle(
                                            fontSize = 13.sp
                                        ),
                                    )
                                }
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,

                                    ) {
                                    Text(
                                        text = stringResource(id = R.string.no_answer),
                                        style = TextStyle(
                                            fontSize = 13.sp
                                        ),
                                    )
                                    Text(
                                        text = salesProfileReport.no_answer ?: "",
                                        style = TextStyle(
                                            fontSize = 13.sp
                                        ),
                                    )
                                }
                            }

                        }
                    }

                }


                Card(Modifier.padding(top = 16.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(
                            Modifier
                                .padding(horizontal = 12.dp)
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = salesProfileReport.arrange_meeting ?: "", style = TextStyle(
                                    fontSize = 20.sp,
                                    color = colorResource(id = R.color.blue),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth(),
                                text = stringResource(R.string.arrange_meeting),
                                style = TextStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        VerticalDivider()
                        Column(
                            Modifier
                                .padding(16.dp)
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = salesProfileReport.done_meeting ?: "", style = TextStyle(
                                    fontSize = 20.sp,
                                    color = colorResource(id = R.color.blue),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = stringResource(R.string.done_meeting),
                                style = TextStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
                Card(Modifier.padding(top = 16.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            Modifier
                                .padding(12.dp)
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = salesProfileReport.created_today_lead ?: "",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = colorResource(id = R.color.blue),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .fillMaxWidth(),
                                text = stringResource(R.string.today_leads),
                                style = TextStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        VerticalDivider()
                        Column(
                            Modifier
                                .padding(16.dp)
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = salesProfileReport.avg_response + "%", style = TextStyle(
                                    fontSize = 20.sp,
                                    color = colorResource(id = R.color.blue),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = stringResource(R.string.avg_response),
                                style = TextStyle(
                                    fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
                Card(Modifier.padding(top = 16.dp)) {
                    Column(
                        Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.action_per_day),
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(top = 16.dp),
                            style = TextStyle(fontWeight = FontWeight.SemiBold)
                        )
                        MyBarChart(
                            salesProfileReport.action_chart,
                            stringResource(R.string.sales_profile_report)
                        )
                    }
                }


            }
        }

        item {
            Card(
                Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.stages_per_lead),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {

                            Image(
                                painter = painterResource(
                                    R.drawable.ellipse
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)

                            )
                            Text(
                                text = stringResource(R.string.normal),
                                modifier = Modifier.padding(horizontal = 8.dp),
                                style = TextStyle(fontWeight = FontWeight.SemiBold)
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        ) {

                            Image(
                                painter = painterResource(
                                    R.drawable.ellipse_red
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)

                            )
                            Text(
                                text = stringResource(R.string.delayed),
                                modifier = Modifier.padding(horizontal = 8.dp),
                                style = TextStyle(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = stageHeight.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
//                        for (statusCounters in 0 until size) {
//                            StagesPerLead(salesProfileReport.status_counters[statusCounters])
//                        }

                        (salesProfileReport.status_counters).forEach { statusCounters ->
                            StagesPerLead(statusCounters)
                        }
                    }
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .clickable {
                                stageHeight = if (stageHeight == 200) {
                                    text = ctx.getString(R.string.hide_all)
                                    500
                                } else {
                                    text = ctx.getString(R.string.view_all)
                                    200
                                }
//                                size = if (size == 5) {
//                                    salesProfileReport.status_counters.size
//                                } else {
//                                    5
//                                }

                            },
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(id = R.color.blue)
                        )
                    )
                }
            }

        }
    }


}

@Composable
fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .height(55.dp)
            .width(1.dp)  // Set the thickness of the vertical line
            .background(colorResource(id = R.color.lightGray))  // Set the color of the vertical line
    )
}

@Composable
fun HorizontalDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)  // Set the thickness of the vertical line
            .background(colorResource(id = R.color.lightGray))  // Set the color of the vertical line
    )
}

@Composable
fun StagesPerLead(statusCounters: StatusCounter) {

    Column {
        Row(
            Modifier
                .onGloballyPositioned {
                    //    size
                }
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 10.dp)
            ) {
                val statusIcon: Int = if (statusCounters.has_pending) {
                    R.drawable.ellipse_red
                } else {
                    R.drawable.ellipse
                }
                Image(
                    painter = painterResource(
                        statusIcon
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)

                )
                Text(
                    text = statusCounters.getTitle(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.SemiBold)
                )
            }
            Text(
                text = statusCounters.actions_count,
                style = TextStyle(
                    color = colorResource(id = R.color.blue),
                    fontWeight = FontWeight.SemiBold
                )
            )

        }
        HorizontalDivider()
    }
}

@Composable
fun MyScreen(navController: NavHostController) {
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentEntry?.destination

    // Handle back button press
    BackHandler {
        if (navController.currentBackStackEntry?.destination?.route == "home_screen") {
            // If we're on the home screen, exit the app
            exitProcess(0)
        } else {
            // Otherwise, go back in the navigation stack
            navController.popBackStack()
        }
    }

    // Your screen content goes here
    // ...
}