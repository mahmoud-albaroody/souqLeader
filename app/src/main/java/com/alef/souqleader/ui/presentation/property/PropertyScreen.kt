package com.alef.souqleader.ui.presentation.property

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.data.remote.dto.Property
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.constants.Constants
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.projects.Filter


@Composable
fun PropertyScreen(navController: NavController, modifier: Modifier) {
    val viewModel: PropertyScreenViewModel = hiltViewModel()


    LaunchedEffect(key1 = true) {
        viewModel.getProperty()
    }
    Column {
        Filter()
        LazyColumn {
            items(viewModel.stateListOfProperty) {
                PropertyItem(it) { property ->
                    val propertyJson = property.toJson()
                    navController.navigate(
                        Screen.PropertyDetailsScreen.route
                            .plus("?" + Screen.PropertyDetailsScreen.objectName + "=${propertyJson}")
                    )
                }
            }
        }
    }

}


@Composable
fun PropertyItem(property: Property, onProjectClick: (Property) -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3.2f)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onProjectClick.invoke(property)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
                .weight(4f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(3.4f)
            ) {
                if (!property.gallery.isNullOrEmpty())
                    Image(
                        painter = rememberAsyncImagePainter(
                            AccountData.BASE_URL + property.gallery[0].image
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(percent = 10))
                    )
            }

            Row(
                Modifier
                    .fillMaxSize()
                    .weight(0.9f),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    property.getTitle().let {
                        it?.let { it1 ->
                            Text(
                                text = it1,
                                style = TextStyle(
                                    color = colorResource(id = R.color.blue),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                    property.region_name.let {
                        it?.let { it1 ->
                            Text(
                                text = it1, style = TextStyle(
                                    fontSize = 13.sp,
                                )
                            )
                        }
                    }
                }
                property.price?.let {
                    Text(
                        text = it, style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}