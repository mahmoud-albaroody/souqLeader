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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.MeetingReport
import com.alef.souqleader.domain.model.CustomBarChartRender
import com.alef.souqleader.ui.constants.Constants
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

@Composable
fun MeetingScreen(modifier: Modifier) {
    val viewModel: MeetingReportViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        viewModel.getMeetingReport()
    }
    viewModel.meetingReports?.let { MeetingItem(it) }
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
            .padding(vertical = 16.dp, horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
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
        Text(
            text = stringResource(R.string.meeting_per_day),
            Modifier
                .padding(top = 16.dp),
            style = TextStyle(
                fontSize = 16.sp, color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        )
        MyBarChart()
        PieChart()
    }

}

@Composable
fun MyBarChart() {
    // Sample data
    val barEntries = listOf(
        BarEntry(1f, 4f),
        BarEntry(2f, 6f),
        BarEntry(3f, 8f),
        BarEntry(4f, 2f),
        BarEntry(5f, 4f),
        BarEntry(6f, 1f)
    )
    val labels = arrayOf("Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "lable6")

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
                xAxis.setCenterAxisLabels(true)
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
                datad.barWidth = 0.7f
                datad.isHighlightEnabled = false
                data = datad
                this.invalidate() // Refresh chart
            }
        }
    )
}

@Composable
fun PieChart() {
    // on below line we are creating a column
    // and specifying a modifier as max size.
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
                text = stringResource(R.string.projects),

                // on below line we are specifying style for our text
                style = TextStyle.Default,

                // on below line we are specifying font family.
                fontFamily = FontFamily.Default,

                // on below line we are specifying font style
                fontStyle = FontStyle.Normal,

                // on below line we are specifying font size.
                fontSize = 20.sp
            )

            // on below line we are creating a column and
            // specifying the horizontal and vertical arrangement
            // and specifying padding from all sides.
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .size(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // on below line we are creating a cross fade and
                // specifying target state as pie chart data the
                // method we have created in Pie chart data class.
                Crossfade(targetState = getPieChartData, label = "") { pieChartData ->
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
                            this.legend.textSize = 14F

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
                            .padding(5.dp), update = {
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

// on below line we are creating a method
// in which we are passing all the data.
val getPieChartData = listOf(
    PieChartData("Chrome", 34.68F),
    PieChartData("Firefox", 16.60F),
    PieChartData("Safari", 16.15F),
    PieChartData("Internet Explorer", 15.62F),
)

val Purple200 = Color(0xFF0F9D58)
val Purple500 = Color(0xFF0F9D58)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

// on below line we are adding different colors.
val greenColor = Color(0xFF0F9D58)
val blueColor = Color(0xFF2196F3)
val yellowColor = Color(0xFFFFC107)
val redColor = Color(0xFFF44336)

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
    ds.valueTextSize = 18f

    // on below line we are specifying type face as bold.
    ds.valueTypeface = Typeface.DEFAULT_BOLD

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

class CustomValueFormatter(private val labels: Array<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return labels.getOrNull(value.toInt()) ?: value.toString()
    }
}