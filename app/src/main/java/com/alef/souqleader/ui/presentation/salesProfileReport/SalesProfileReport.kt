package com.alef.souqleader.ui.presentation.salesProfileReport

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alef.souqleader.R
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.White

@Composable
fun SalesProfileReportScreen(modifier: Modifier) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    salesProfileReportItem()
}

@Preview
@Composable
fun salesProfileReportItem() {
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
            Card(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(140.dp)
            ) {
                Column(Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
                    Image(
                        painter = painterResource(R.drawable.user_profile_placehoder),
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        modifier = Modifier.padding(top = 24.dp),
                        text = "Mahmoud Ali",
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
                            text = "Sales Director",
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
                        text = "4",
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
                                text = "4",
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
                                text = "5",
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
                        text = "11", style = TextStyle(
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
                        text = "11", style = TextStyle(
                            fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(R.string.today_leads),
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
                        text = "11", style = TextStyle(
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
                        text = "100%", style = TextStyle(
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