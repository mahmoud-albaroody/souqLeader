package com.alef.souqleader.ui.presentation.meetingReport

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.marginStart
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Chart
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.MeetingReport
import com.alef.souqleader.domain.model.CustomBarChartRender
import com.alef.souqleader.ui.constants.Constants
import com.alef.souqleader.ui.presentation.crmSystem.CommentItem
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue2
import com.alef.souqleader.ui.theme.White
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import org.xmlpull.v1.sax2.Driver

@Composable
fun MeetingScreen(modifier: Modifier) {
    val viewModel: MeetingReportViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        viewModel.getMeetingReport()
    }
    viewModel.meetingReports?.let {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            content = {
                item {
                    MeetingItem(it)
                }
                item {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp),
                    ) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                                text = stringResource(R.string.meeting_leads),
                                fontSize = 18.sp, color = Color.Black,
                                fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .heightIn(200.dp, 500.dp), content = {
                                    items(it.leads) { lead ->
                                        MeetingLeads(lead)
                                    }
                                })
                        }
                    }
                }
            })
    }
}

@Composable
fun MeetingItem(meetingReport: MeetingReport) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 8.dp, horizontal = 16.dp),
//            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
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
                        painter = rememberAsyncImagePainter(
                            if (meetingReport.the_best.user_image?.isNotEmpty() == true) {
                                Constants.BASE_URL + meetingReport.the_best.user_image
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
                        modifier = Modifier.padding(top = 24.dp),
                        text = meetingReport.the_best.user_name,
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
                            text = stringResource(R.string.sales_director),
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
                        text = meetingReport.the_best.meetings_count,
                        style = TextStyle(
                            fontSize = 20.sp, color = Blue, fontWeight = FontWeight.Bold
                        ),
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = stringResource(R.string.total_of_meeting),
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
                                text = stringResource(R.string.total_activities),
                                style = TextStyle(
                                    fontSize = 13.sp
                                ),
                            )
                            Text(
                                text = meetingReport.the_best.total_activity_count,
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
                                text = stringResource(R.string.total_actions),
                                style = TextStyle(
                                    fontSize = 13.sp
                                ),
                            )
                            Text(
                                text = meetingReport.the_best.total_actions_count,
                                style = TextStyle(
                                    fontSize = 13.sp
                                ),
                            )
                        }
                    }

                }
            }
        }

        Card(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)

        ) {
            MyBarChart(meetingReport.chart)
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)

        ) {
            PieChart(meetingReport, stringResource(R.string.projects))
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)


        ) {
            PieChart(meetingReport, stringResource(R.string.channels))
        }


    }

}

@Composable
fun MyBarChart(chart: List<Chart>) {
    // Sample data
    val barEntries: ArrayList<BarEntry> = arrayListOf()
    val labels: ArrayList<String> = arrayListOf()
    chart.forEachIndexed { index, chart ->
        labels.add(chart.date)
        barEntries.add(BarEntry(index.toFloat(), chart.count))
    }
    Text(
        text = stringResource(R.string.meeting_per_day),
        Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 18.sp, color = Color.Black,
            fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
        )
    )

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .size(220.dp)
            .padding(vertical = 8.dp),
        factory = { context ->
            BarChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                // Configure bar chart
                this.description.isEnabled = false
                this.setFitBars(true)
                this.setDrawGridBackground(false)

                animateXY(2000, 2000)
                //
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)

                xAxis.textColor = android.graphics.Color.BLACK
                xAxis.textSize = 12f
                xAxis.axisLineColor = android.graphics.Color.WHITE
                xAxis.granularity = 1f
                //  xAxis.isGranularityEnabled = true
                //   xAxis.setCenterAxisLabels(true)
                //  xAxis.setAvoidFirstLastClipping(true)
                // xAxis.setDrawGridLines(true)
                axisRight.isEnabled = false
                legend.isEnabled = false
                xAxis.valueFormatter = IndexAxisValueFormatter(labels);
                //   xAxis.valueFormatter = CustomValueFormatter(labels)
                // data.barWidth = 0.5f
                //   data.isHighlightEnabled = true
                setScaleEnabled(false)
                setVisibleXRangeMaximum(4f)
                val barChartRender = CustomBarChartRender(
                    this,
                    animator,
                    viewPortHandler
                )
                barChartRender.setRadius(30)

                renderer = barChartRender
                // Create bar data set
                val barDataSet = BarDataSet(barEntries, "Sample Data").apply {
                    colors = ColorTemplate.MATERIAL_COLORS.asList()
                    valueTextColor = R.color.black
                    valueTextSize = 16f
                }
                barDataSet.setDrawValues(false)
                barDataSet.isHighlightEnabled = false

                // Set data to the chart
                val datad = BarData(barDataSet)
                datad.barWidth = 0.6f
                datad.isHighlightEnabled = false
                data = datad
                this.invalidate() // Refresh chart
            }
        }
    )
}

@Composable
fun PieChart(meetingReport: MeetingReport, title: String) {
    // on below line we are creating a column
    // and specifying a modifier as max size.

    val arr: ArrayList<PieChartData> = arrayListOf()

    if (title == stringResource(R.string.projects)) {
        meetingReport.project_chart.forEachIndexed { index, projectChart ->
            arr.add(PieChartData(projectChart.title, projectChart.lead_percentage))
        }
    } else {
        meetingReport.channel_chart.forEachIndexed { index, channelChart ->
            arr.add(PieChartData(channelChart.title(), channelChart.lead_count))
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        // on below line we are again creating a column
        // with modifier and horizontal and vertical arrangement
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // on below line we are creating a simple text
            // and specifying a text as Web browser usage share
            Text(
                text = title,
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 18.sp, color = Color.Black,
                fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
            )

            // on below line we are creating a column and
            // specifying the horizontal and vertical arrangement
            // and specifying padding from all sides.
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .size(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // on below line we are creating a cross fade and
                // specifying target state as pie chart data the
                // method we have created in Pie chart data class.
                Crossfade(targetState = arr, label = "") { pieChartData ->
                    // on below line we are creating an
                    // android view for pie chart.
                    AndroidView(factory = { context ->
                        // on below line we are creating a pie chart
                        // and specifying layout params.
                        PieChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                // on below line we are specifying layout
                                // params as MATCH PARENT for height and width.
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )


                            // on below line we are setting description
                            // enables for our pie chart.
                            this.description.isEnabled = false

                            // on below line we are setting draw hole
                            // to false not to draw hole in pie chart
                            this.isDrawHoleEnabled = false

                            // on below line we are enabling legend.
                            this.legend.isEnabled = true

                            // on below line we are specifying
                            // text size for our legend.
                            this.legend.textSize = 9F

                            // on below line we are specifying
                            // alignment for our legend.
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER
                            // on below line we are specifying entry label color as white.
                            this.setEntryLabelColor(resources.getColor(R.color.white))
                        }
                    },
                        // on below line we are specifying modifier
                        // for it and specifying padding to it.
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(16.dp),
                        update = {
                            // on below line we are calling update pie chart
                            // method and passing pie chart and list of data.
                            updatePieChartWithData(it, pieChartData)
                        })
                }
            }
        }
    }
}

data class PieChartData(
    var browserName: String?,
    var value: Float?
)


// on below line we are adding different colors.
val greenColor = Color(0xFF0F9D58)
val blueColor = Color(0xFF2196F3)
val yellowColor = Color(0xFFFFC107)
val redColor = Color(0xFFF44336)
val broundColor = Color(0xFF6A2E2A)
val greenColor1 = Color(0xFF949C98)
val blueColor2 = Color(0xFF0D5995)
val yellowColor3 = Color(0xFF5F5024)
val redColor4 = Color(0xFFF6B0AC)
val broundColor5 = Color(0xFF535151)

// on below line we are creating a update pie
// chart function to update data in pie chart.
fun updatePieChartWithData(
    // on below line we are creating a variable
    // for pie chart and data for our list of data.
    chart: PieChart,
    data: List<PieChartData>
) {
    // on below line we are creating
    // array list for the entries.
    val entries = ArrayList<PieEntry>()

    // on below line we are running for loop for
    // passing data from list into entries list.
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.browserName ?: ""))
    }

    // on below line we are creating
    // a variable for pie data set.
    val ds = PieDataSet(entries, "")

    // on below line we are specifying color
    // int the array list from colors.
    ds.colors = arrayListOf(
        greenColor.toArgb(),
        blueColor.toArgb(),
        redColor.toArgb(),
        yellowColor.toArgb(),
        broundColor.toArgb(),
        greenColor1.toArgb(),
        blueColor2.toArgb(),
        yellowColor3.toArgb(),
        redColor4.toArgb(),
        broundColor5.toArgb(),
    )
    // on below line we are specifying position for value
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying position for value inside the slice.
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying
    // slice space between two slices.
    ds.sliceSpace = 2f

    // on below line we are specifying text color
    ds.valueTextColor = R.color.white

    // on below line we are specifying
    // text size for value.
    ds.valueTextSize = 10f

    // on below line we are specifying type face as bold.
    ds.valueTypeface = Typeface.DEFAULT

    // on below line we are creating
    // a variable for pie data
    val d = PieData(ds)

    // on below line we are setting this
    // pie data in chart data.
    chart.data = d

    // on below line we are
    // calling invalidate in chart.
    chart.invalidate()
}


@Composable
fun MeetingLeads(lead: Lead) {

    Column {
        Row(
            Modifier
                .padding(vertical = 4.dp, horizontal = 6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = lead.name, style = TextStyle(), modifier = Modifier.weight(1f))
            Text(
                text = lead.phone,
                style = TextStyle(color = Blue2),
                modifier = Modifier.weight(1f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.5f)
            ) {
                Image(
                    modifier = Modifier.padding(end = 4.dp),
                    painter = painterResource(id = R.drawable.baseline_call_24),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_assistant_navigation_24),
                    contentDescription = "",
                    contentScale = ContentScale.Fit
                )
            }
            if (lead.project_name.isEmpty()) {
                lead.project_name = "Un Specified"
            }
            Text(
                text = lead.project_name,
                style = TextStyle(textAlign = TextAlign.End),
                modifier = Modifier.weight(1f)
            )
        }
        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()  // Set the thickness of the vertical line
                .background(Blue2)
                .padding(top = 16.dp) // Set the color of the vertical line
        )
    }
}
