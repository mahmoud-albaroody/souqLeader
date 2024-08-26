package com.alef.souqleader.ui.presentation.projects

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.constants.Constants
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.salesProfileReport.VerticalDivider
import com.alef.souqleader.ui.theme.*
import com.google.gson.Gson

@Composable
fun ProjectsScreen(navController: NavController, modifier: Modifier) {
    val viewModel: ProjectsScreenViewModel = hiltViewModel()


    LaunchedEffect(key1 = true) {
        viewModel.getProject()
    }



    Column {
        Filter()
        LazyColumn(Modifier.padding(top = 8.dp)) {
            items(viewModel.stateListOfProjects) { item ->
                ProjectsItem(item) { project ->
                    val projectJson = project.toJson()
                    navController.navigate(
                        Screen.ProjectsDetailsScreen
                            .route.plus("?" + Screen.ProjectsDetailsScreen.objectName + "=${projectJson}")
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun Filter() {
    Card(
        Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {

            Row(
                Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.filter_icon),
                    contentDescription = "",
                    Modifier

                        .clickable {
                            //   onFilterClick.invoke()
                        }
                )
                Text(
                    text = stringResource(id = R.string.filter),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.blue2)
                    )
                )
            }

            VerticalDivider()
            Row(
                Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.sort_icon),
                    contentDescription = "",
                    Modifier

                        .clickable {
                            //   onFilterClick.invoke()
                        }
                )
                Text(
                    text = stringResource(R.string.sort_by),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.blue2)
                    )
                )
            }
            VerticalDivider()
            Row(
                Modifier
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.map_icon),
                    contentDescription = "",
                    Modifier
                        .clickable {
                            //   onFilterClick.invoke()
                        }
                )
                Text(
                    text = stringResource(R.string.map),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.blue2)
                    )
                )
            }
        }
    }

}

@Composable
fun ProjectsItem(project: Project, onProjectClick: (Project) -> Unit) {
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
                onProjectClick.invoke(project)
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
                if (project.images?.isNotEmpty() == true)
                    Image(
                        painter = rememberAsyncImagePainter(
                            AccountData.BASE_URL + project.images[0].file
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
                    project.title?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                color = colorResource(id = R.color.blue),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    project.region_name?.let {
                        Text(
                            text = it, style = TextStyle(
                                fontSize = 13.sp,
                            )
                        )
                    }
                }
                project.start_price?.let {
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