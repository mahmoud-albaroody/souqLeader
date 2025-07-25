package com.alef.souqleader.ui.presentation.meetingReport

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Chart
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.MeetingReport
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.domain.model.CustomBarChartRender
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.presentation.allLeads.AllLeadViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MeetingScreen(modifier: Modifier, mainViewModel: MainViewModel) {
    val viewModel: MeetingReportViewModel = hiltViewModel()

    val meeting = remember { mutableStateListOf<Lead>() }
    var someKey by remember { mutableStateOf(false) }
    var loadMore by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = someKey) {
        viewModel.getMeetingReport()
    }
    viewModel.meetingReports?.let {
        if(viewModel.page==1){
            meeting.clear()
            meeting.addAll(it.leads)
        }else {
            meeting.addAll(it.leads)
        }
        loadMore = true

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
                                fontSize = 18.sp, color = colorResource(id = R.color.black),
                                fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .heightIn(200.dp, 500.dp),
                                content = {
                                    items(meeting) { lead ->
                                        MeetingLeads(lead, mainViewModel)
                                    }

                                    if (it.pagination.pages != null && loadMore)
                                        if (it.pagination.pages >= viewModel.page && it.leads.size > 10) {
                                            item {
                                               viewModel.viewModelScope.launch {
                                                   delay(2000)
                                                   loadMore = false
                                                   someKey=!someKey
                                               }

                                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                                    CircularProgressIndicator(
                                                        modifier = Modifier.width(16.dp),
                                                        color = MaterialTheme.colorScheme.secondary,
                                                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                                    )
                                                }
                                            }
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
            .background(colorResource(id = R.color.white))
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

            ) {
                Column(Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            if (meetingReport.the_best.user_image?.isNotEmpty() == true) {
                                AccountData.BASE_URL + meetingReport.the_best.user_image
                            } else {
                                R.drawable.user_profile_placehoder
                            }
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(2.dp, colorResource(id = R.color.lightGray), CircleShape)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        text = meetingReport.the_best.user_name,
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.blue)
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

            ) {
                Column(
                    Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = meetingReport.total_meeting.toString(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.blue),
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = stringResource(R.string.total_of_meeting),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
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
                                text = meetingReport.total_activity.toString(),
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
                                text = meetingReport.total_actions.toString(),
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
            MyBarChart(meetingReport.chart, stringResource(R.string.meeting_per_day))
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)

        ) {
            val arr: ArrayList<PieChartData> = arrayListOf()
            meetingReport.project_chart.forEachIndexed { index, projectChart ->
                arr.add(PieChartData(projectChart.title, projectChart.lead_percentage))
            }
            PieChartView(arr, stringResource(R.string.meeting_report))
        }
        Card(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)


        ) {
            val arr1: ArrayList<PieChartData> = arrayListOf()
            meetingReport.channel_chart.forEachIndexed { index, projectChart ->
                arr1.add(PieChartData(projectChart.title(), projectChart.lead_count))
            }
            PieChartView(arr1, stringResource(R.string.channels))
        }
    }

}

@Composable
fun MyBarChart(chart: List<Chart>, title: String) {
    // Sample data

    val barEntries: ArrayList<BarEntry> = arrayListOf()
    val labels: ArrayList<String> = arrayListOf()
    chart.forEachIndexed { index, chart ->
        if (chart.date.isNullOrEmpty()) {
            chart.getTitle().let { it?.let { it1 -> labels.add(it1) } }
        } else {
            chart.date.let { labels.add(it) }
        }

        BarEntry(index.toFloat(), chart.getCount()).let { barEntries.add(it) }
    }
    Text(
        text = title,
        Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        style = TextStyle(
            fontSize = 18.sp, color = colorResource(id = R.color.black),
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

                axisLeft.granularity = 1f
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                // Configure bar chart
                this.description.isEnabled = false
                this.setFitBars(true)
                this.setDrawGridBackground(false)

                animateXY(2000, 2000)

                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                xAxis.textColor = android.graphics.Color.BLACK
                xAxis.textSize = 10f
                xAxis.axisLineColor = android.graphics.Color.WHITE
                xAxis.granularity = 1f


                //  xAxis.isGranularityEnabled = true
                //   xAxis.setCenterAxisLabels(true)
                //  xAxis.setAvoidFirstLastClipping(true)
                xAxis.labelRotationAngle = 80f
                xAxis.granularity = 1f;
                // xAxis.setDrawGridLines(true)
                axisRight.isEnabled = false
                legend.isEnabled = false
                xAxis.valueFormatter = IndexAxisValueFormatter(labels);
                xAxis.labelCount = labels.size
                //   xAxis.valueFormatter = CustomValueFormatter(labels)
                // data.barWidth = 0.5f
                //   data.isHighlightEnabled = true
                setScaleEnabled(false)
                setVisibleXRangeMaximum(4f)
                //  extraBottomOffset = 100F
                val barChartRender = CustomBarChartRender(
                    this,
                    animator,
                    viewPortHandler
                )

                barChartRender.setRadius(30)
                //  xAxis.valueFormatter = MultiLineValueFormatter()
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
fun PieChartView(arr: ArrayList<PieChartData>, title: String) {
    // on below line we are creating a column
    // and specifying a modifier as max size.


//    if (title == stringResource(R.string.meeting_report)) {
//        arr.clear()
//        meetingReport.project_chart.forEachIndexed { index, projectChart ->
//            arr.add(PieChartData(projectChart.title, projectChart.lead_percentage))
//        }
//    }
//    if (title == stringResource(R.string.projects)) {
//        arr.clear()
//        meetingReport.projectChart.forEachIndexed { index, projectChart ->
//            arr.add(PieChartData(projectChart.title, projectChart.lead_percentage))
//        }
//    } else {
//        arr.clear()
//        meetingReport.channel_chart.forEachIndexed { index, channelChart ->
//            arr.add(PieChartData(channelChart.title(), channelChart.lead_count))
//        }
//    }
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
                fontSize = 18.sp, color = colorResource(id = R.color.black),
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
                            this.setDrawEntryLabels(false)
                            this.setDrawSliceText(false); // To remove slice text
                            this.setDrawMarkers(false); // To remove markers when click
                            // on below line we are setting description
                            // enables for our pie chart.
                            this.description.isEnabled = false
                            this.setDrawCenterText(false)

                            // on below line we are setting draw hole
                            // to false not to draw hole in pie chart
                            this.isDrawHoleEnabled = true

                            // on below line we are enabling legend.
                            this.legend.isEnabled = false

                            // on below line we are specifying
                            // text size for our legend.
                            this.legend.textSize = 9F
                            // on below line we are specifying
                            // alignment for our legend.
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER
                            // on below line we are specifying entry label color as white.
                            this.setEntryLabelColor(resources.getColor(R.color.white))

                            val l: Legend = this.legend


                            l.setDrawInside(false);
                            l.yEntrySpace = 10f

                            l.isWordWrapEnabled = true
                            l.isEnabled = true

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
    ds.setDrawValues(false)
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
fun MeetingLeads(lead: Lead, mainViewModel: MainViewModel) {
    val ctx = LocalContext.current

    Column {
        Row(
            Modifier
                .padding(vertical = 4.dp, horizontal = 6.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = lead.name ?: "", style = TextStyle(), modifier = Modifier.weight(1f))
            if ( lead.phone?.length!! > 3) {
                Text(
                    text = (lead.phone.substring(
                        0,
                        3
                    ) + "*".repeat(lead.phone.length - 3)),
                    style = TextStyle(color = colorResource(id = R.color.blue2)),
                    modifier = Modifier.weight(1f)
                )
            } else {
                Text(
                    text = lead.phone,
                    style = TextStyle(color = colorResource(id = R.color.blue2)),
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.5f)
            ) {
                Image(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clickable {
                            val u = Uri.parse(
                                "tel:" + lead.phone.toString()
                            )

                            // Create the intent and set the data for the
                            // intent as the phone number.
                            val i = Intent(Intent.ACTION_DIAL, u)
                            try {
                                // Launch the Phone app's dialer with a phone
                                // number to dial a call.
                                ctx.startActivity(i)
                                mainViewModel.showDialog = true
                            } catch (s: SecurityException) {

                                // show() method display the toast with
                                // exception message.
                                Toast
                                    .makeText(ctx, "An error occurred", Toast.LENGTH_LONG)
                                    .show()
                            }
                        },
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
            if (lead.project_name?.isEmpty() == true) {
                lead.project_name = "Un Specified"
            }
            Text(
                text = lead.project_name ?: "",
                style = TextStyle(textAlign = TextAlign.End),
                modifier = Modifier.weight(1f)
            )
        }
        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()  // Set the thickness of the vertical line
                .background(colorResource(id = R.color.blue2))
                .padding(top = 16.dp) // Set the color of the vertical line
        )
    }
}


class MultiLineValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val label = "Your label here" // Replace with your label logic
        // Split the label into two lines
        return if (label.length > 10) """
     ${label.substring(0, 10)}
     ${label.substring(10)}
     """.trimIndent() else label
    }
}