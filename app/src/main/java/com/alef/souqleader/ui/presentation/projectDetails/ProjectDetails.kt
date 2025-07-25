package com.alef.souqleader.ui.presentation.projectDetails


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.presentation.proprtiesDetails.generatePdfFromImageArray
import com.google.accompanist.pager.*

@Composable
fun ProjectDetailsScreen(navController: NavController, modifier: Modifier,
                         mainViewModel: MainViewModel, project: Project?) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    val imageUrls = arrayListOf<String>()
    val context = LocalContext.current
    project?.images?.forEach {
        it.file?.let { it1 -> imageUrls.add(AccountData.BASE_URL + it1) }
    }
    mainViewModel.showShareIcon = true
    LaunchedEffect(key1 = Unit) {

        mainViewModel.onShareClick.collect {
            if (it)
                generatePdfFromImageArray(context, imageUrls,null, project)
        }
    }
    Item(project)
}

@Composable
fun Item(project: Project?) {
    Column(
        Modifier
            .background(colorResource(id = R.color.white))
            .fillMaxSize()
    ) {

        project?.let {
            ImageSliderExample(it)
            DetailsItem(it)
        }

    }
}

@Composable
fun DetailsItem(project: Project) {
    Column(
        Modifier
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .padding(bottom = 16.dp, top = 0.dp)
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Column {
                    project.title?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                color = colorResource(id = R.color.blue),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                    project.region_name?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                color = colorResource(id = R.color.lightGray),
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }
            Column {
                project.start_price?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = colorResource(id = R.color.black),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }

        // DetailsProduct()
        project.description?.let {
            MyScrollableTextView(
                it
            )
        }
    }
}

@Composable
fun MyScrollableTextView(text: String) {
    // val text = "This is a long text. ".repeat(20)

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(text = text)
    }
}


@Composable
fun DetailsProduct() {
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .verticalScroll(scrollState)
    ) {
        ReminderItem(stringResource(R.string.unit_no), "A18")
        ReminderItem(stringResource(R.string.building_no), "2761")
        ReminderItem(stringResource(R.string.land_space), "120 m2")
        ReminderItem(stringResource(R.string.price), "250.000 EGP")
        ReminderItem(stringResource(R.string.owner_name), "Ahmed Ali")
        ReminderItem(stringResource(R.string.owner_mobile), "012736355546")
        ReminderItem(stringResource(R.string.property_type), "Private property")
    }
}

@Composable
fun ReminderItem(text: String, text1: String) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp,
                    horizontal = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 12.sp, color = colorResource(id = R.color.gray)
                ),
            )
            Text(
                text = text1,
                style = TextStyle(
                    fontSize = 14.sp, color = colorResource(id = R.color.black)
                ),
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSliderExample(project: Project) {


    val pagerState = rememberPagerState()

    Column {
        project.images?.size?.let {
            HorizontalPager(
                count = it,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) { page ->
                Image(
                    painter = rememberImagePainter(data = AccountData.BASE_URL + project.images[page].file),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }
}

