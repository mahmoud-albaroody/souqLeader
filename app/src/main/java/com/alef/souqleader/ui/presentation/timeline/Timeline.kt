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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.constants.Constants
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.Blue2


@Composable
fun TimelineScreen(navController: NavController, modifier: Modifier) {
    val viewModel: TimeLineViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.getPosts()
    }


    Column {
        WritePost()
        LazyColumn(Modifier.padding(horizontal = 24.dp)) {
            items(viewModel.statePosts) { post ->
                TimelineItem(post) {
                    val postJson = post.toJson()
                    navController.navigate(
                        Screen.CRMScreen.route
                            .plus("?" + Screen.CRMScreen.objectName + "=${postJson}")
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WritePost() {
    Column {
        WriteTextPost()
        MediaPost()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteTextPost() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp, horizontal = 24.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextField(
                modifier = Modifier.weight(3f),
                value = "",
                placeholder = {
                    Text(text = "text")
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    disabledLabelColor = Blue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {

                },
//                shape = RoundedCornerShape(8.dp),
                singleLine = true,
            )

        }
    }

}


@Composable
fun MediaPost() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp).padding(bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            Modifier
                .weight(2f).padding( horizontal = 8.dp)
        ) {
            Image(
                painterResource(R.drawable.gallery),
                modifier = Modifier
                    .weight(0.5f),
                contentDescription = "",
            )

            Image(
                painterResource(R.drawable.camera_upload),
                contentDescription = "",
                Modifier
                    .weight(0.5f)
                    .clickable {
                        //   onFilterClick.invoke()
                    }
            )
            Image(
                painterResource(R.drawable.video_play),
                contentDescription = "",
                Modifier
                    .weight(0.5f)
                    .clickable {
                        //   onFilterClick.invoke()
                    }
            )
            Image(
                painterResource(R.drawable.record_upload),
                contentDescription = "",
                Modifier
                    .weight(0.5f)
                    .clickable {
                        //   onFilterClick.invoke()
                    }
            )
        }
        Button(modifier = Modifier
            .weight(1.2f)
            .padding( horizontal = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(Blue2),
            onClick = {

            }) {
            Text(
                text = stringResource(R.string.post), Modifier.padding(vertical = 6.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
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
                painter = rememberAsyncImagePainter(
                    if (post.images?.isNotEmpty() == true) {
                        AccountData.BASE_URL + post.images[0].image
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