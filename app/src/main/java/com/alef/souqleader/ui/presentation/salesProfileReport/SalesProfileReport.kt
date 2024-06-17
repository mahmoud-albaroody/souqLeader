package com.alef.souqleader.ui.presentation.salesProfileReport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.SalesProfileReport
import com.alef.souqleader.data.remote.dto.StatusCounter
import com.alef.souqleader.ui.constants.Constants
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.White

@Composable
fun SalesProfileReportScreen(modifier: Modifier) {
    val viewModel: SalesProfileReportViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.getSalesProfileReport()
    }
    viewModel.salesProfileReport?.let { SalesProfileReportItem(it) }
}

class SalesProfileReportPreviewParamProvider(override val values: Sequence<SalesProfileReport>) :
    PreviewParameterProvider<SalesProfileReport>


@Composable
fun SalesProfileReportItem(
    @PreviewParameter(SalesProfileReportPreviewParamProvider::class)
    salesProfileReport: SalesProfileReport
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ,

            Card(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(140.dp)
            ) {
                Column(Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            if (salesProfileReport.user.image?.isNotEmpty() == true) {
                                Constants.BASE_URL + salesProfileReport.user.image
                            } else {
                                R.drawable.user_profile_placehoder
                            }
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.LightGray, CircleShape)
                    )
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = salesProfileReport.user.name,
                        style = TextStyle(
                            fontSize = 16.sp, color = Blue
                        ),
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = salesProfileReport.user.role,
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
                    .height(140.dp)
            ) {
                Column(
                    Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = salesProfileReport.total_calls,
                        style = TextStyle(
                            fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold
                        ),
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "Total of Calls",
                        style = TextStyle(
                            fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                        ),
                    )
                    Column(
                        Modifier.padding(top = 24.dp), verticalArrangement = Arrangement.Bottom
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Answer",
                                style = TextStyle(
                                    fontSize = 13.sp
                                ),
                            )
                            Text(
                                text = salesProfileReport.answer,
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
                                text = "No Answer",
                                style = TextStyle(
                                    fontSize = 13.sp
                                ),
                            )
                            Text(
                                text = salesProfileReport.no_answer,
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
                        text = salesProfileReport.arrange_meeting, style = TextStyle(
                            fontSize = 20.sp, color = Blue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        text = stringResource(R.string.arrange_meeting),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
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
                        text = salesProfileReport.done_meeting, style = TextStyle(
                            fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold
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
                        text = salesProfileReport.created_today_lead, style = TextStyle(
                            fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold
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
                            fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold
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
            Column(Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.stages_per_lead),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    style = TextStyle(fontWeight = FontWeight.SemiBold)
                )
                LazyColumn {
                    items(salesProfileReport.status_counters) { statusCounters ->
                        StagesPerLead(statusCounters)
                    }
                }
                Text(
                    text = stringResource(R.string.view_all),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    style = TextStyle(fontWeight = FontWeight.SemiBold, color = Blue)
                )
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
            .background(Color.LightGray)  // Set the color of the vertical line
    )
}

@Composable
fun HorizontalDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)  // Set the thickness of the vertical line
            .background(Color.LightGray)  // Set the color of the vertical line
    )
}

@Composable
fun StagesPerLead(statusCounters: StatusCounter) {

    Column {
        Row(
            Modifier
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
                val statusIcon: Int = if(statusCounters.has_pending){
                    R.drawable.ellipse_red
                }else{
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
                style = TextStyle(color = Blue, fontWeight = FontWeight.SemiBold)
            )

        }
        HorizontalDivider()
    }
}