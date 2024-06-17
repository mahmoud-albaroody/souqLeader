package com.alef.souqleader.ui.presentation.timeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.ui.constants.Constants
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.theme.Blue


@Composable
fun TimelineScreen(navController: NavController, modifier: Modifier) {
    val viewModel: TimeLineViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.getPosts()
    }


    LazyColumn(Modifier.padding(horizontal = 24.dp)) {
        items(viewModel.statePosts) {
            TimelineItem(it) {
                navController.navigate(Screen.CRMScreen.route)
            }
        }
    }
}


@Composable
fun TimelineItem(post: Post, onTimelineCLick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight * 0.38f)
            .padding(vertical = 6.dp)
            .clickable { onTimelineCLick.invoke() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .weight(6f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = post.post,
                style = TextStyle(
                    color = Blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Image(
                painter = rememberAsyncImagePainter( if(post.images?.isNotEmpty() == true){Constants.BASE_URL + post.images[0].image
                } else {
                    ""
                }
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(screenHeight * 0.22f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(percent = 10))
                    .padding(vertical = 4.dp),
            )

            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.like),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = post.likes_count.toString() + " " + stringResource(R.string.like),
                        style = TextStyle(
                            fontSize = 13.sp,
                        )
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.message_text),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .padding(end = 8.dp),
                        text = post.commentCount()
                            .toString() + " " + stringResource(R.string.comment), style = TextStyle(
                            fontSize = 13.sp,
                        )
                    )
                }
            }
        }
    }
}