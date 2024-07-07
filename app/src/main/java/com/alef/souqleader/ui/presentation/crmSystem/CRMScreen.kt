package com.alef.souqleader.ui.presentation.crmSystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Comment
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.constants.Constants
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.White

@Composable
fun CRMScreen(navController: NavController, modifier: Modifier, post: Post) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    CRMScreenItem(post)
}


@Composable
fun CRMScreenItem(post: Post) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
//            .verticalScroll(scrollState)
            .padding(vertical = 16.dp, horizontal = 24.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = post.post,
                    style = TextStyle(
                        fontSize = 18.sp, color = Blue, fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = post.post,
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(
                        fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        if (!post.images.isNullOrEmpty()) {
                            if (post.images[0].image?.isNotEmpty() == true) {
                                AccountData.BASE_URL+ post.images[0].image
                            } else {
                                //  R.drawable.user_profile_placehoder
                            }
                        } else {

                        }
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight / 4)
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(16.dp))

                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(R.drawable.like),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(RoundedCornerShape(16.dp))

                        )
                        Text(
                            text = post.likes_count + "Like",
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(
                                fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painterResource(R.drawable.message_text),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Text(
                            text = post.comment.size.toString() + " " + stringResource(R.string.comment),
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .padding(end = 16.dp),
                            style = TextStyle(
                                fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.comments),
                    modifier = Modifier.padding(top = 16.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }


            LazyColumn(content = {
                items(post.comment) {
                    CommentItem(it)
                }
            })

        }

        Column(
            Modifier.align(Alignment.BottomCenter),

            ) {
            ReminderItem(text = stringResource(R.string.add_comment), text1 = "qwqw")
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderItem(text: String, text1: String) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),

        value = "",
        placeholder = {
            Text(text = stringResource(R.string.notes))
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            disabledLabelColor = Blue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = {

        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
    )
}

@Composable
fun CommentItem(comment: Comment) {
    Row(Modifier.padding(top = 14.dp)) {
        Image(
            painter = rememberAsyncImagePainter(
                if (comment.user_Image?.isNotEmpty() == true) {
                    AccountData.BASE_URL + comment.user_Image
                } else {
                    R.drawable.user_profile_placehoder
                }
            ),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            comment.username?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(horizontal = 14.dp),
                    style = TextStyle(
                        fontSize = 12.sp, color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Text(
                text = comment.comment,
                modifier = Modifier.padding(horizontal = 11.dp),
                        style = TextStyle(
                        fontSize = 12.sp, color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            )
        }
    }
}
