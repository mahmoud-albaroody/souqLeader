package com.alef.souqleader.ui.presentation.proprtiesDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.alef.souqleader.data.remote.dto.Property
import com.alef.souqleader.domain.model.AccountData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun PropertyDetailsScreen(navController: NavController, modifier: Modifier, property: Property?) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    Item(property)
}

@Composable
fun Item(property: Property?) {
    Column(
        Modifier
            .background(colorResource(id = R.color.white))
            .fillMaxSize()
    ) {

        property?.let {
            ImageSliderExample(it)
            DetailsItem(it)
        }

    }
}

@Composable
fun DetailsItem(property: Property) {
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
                    property.getTitle()?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                color = colorResource(id = R.color.blue),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                    property.region_name?.let {
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
                property.price?.let {
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

        DetailsProduct(property)

    }
}


@Composable
fun DetailsProduct(property: Property) {
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .verticalScroll(scrollState)
    ) {
        property.unit_no?.let { ReminderItem(stringResource(R.string.unit_no), it) }
        property.bulding_no?.let { ReminderItem(stringResource(R.string.building_no), it) }
        property.land_space?.let { ReminderItem(stringResource(R.string.land_space), it) }
        property.price?.let { ReminderItem(stringResource(R.string.price), it) }
        property.owner?.let { ReminderItem(stringResource(R.string.owner_name), it) }
        property.owner_mobile?.let { ReminderItem(stringResource(R.string.owner_mobile), it) }
        property.property_unit_type?.title()?.let {
            ReminderItem(
                stringResource(R.string.property_type),
                it
            )
        }
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
fun ImageSliderExample(property: Property) {
    val images = listOf(
        "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
    )

    val pagerState = rememberPagerState()

    Column {
        property.gallery?.size?.let {
            HorizontalPager(
                count = it,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) { page ->
                Image(
                    painter = rememberImagePainter(data = AccountData.BASE_URL + property.gallery[page].image),
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