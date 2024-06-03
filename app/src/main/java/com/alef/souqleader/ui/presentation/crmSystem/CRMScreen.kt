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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alef.souqleader.R
import com.alef.souqleader.ui.theme.Blue
import com.alef.souqleader.ui.theme.White

@Composable
fun CRMScreen(modifier: Modifier) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    CRMScreenItem()
}

@Preview
@Composable
fun CRMScreenItem() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
//            .verticalScroll(scrollState)
            .padding(vertical = 16.dp, horizontal = 16.dp),
    ) {

        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "CRM system and how to management clients and leads",
                style = TextStyle(
                    fontSize = 18.sp, color = Blue, fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                text = "Lorem ipsum dolor sit amet," + " consectetur adipisici elit, sed do eiusmod tempor incididunt ut" + " labore et dolore magna aliqua. Ut enim ad minim. Empor incididunt ut" + " labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud" + " exercitation ullamco laboris nisi ut aliquip ex.",
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
                painterResource(R.drawable.test_icon),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth().height(screenHeight/4)
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
                        text = "3 Like",
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
                        text = "8 Comment",
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
                text = "Comments",
                modifier = Modifier.padding(top = 16.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Box(
            Modifier
                .fillMaxSize(),
        ) {
            LazyColumn(content = {
                items(16) {
                    CommentItem()
                }
            })
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            ) {
                ReminderItem("Add comment", "qwqw")
            }
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
            Text(text = "Notes")
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
fun CommentItem() {
    Row(Modifier.padding(top = 14.dp)) {
        Image(
            painterResource(R.drawable.user_profile_placehoder),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Column() {
            Text(
                text = "Ayman Mahmoud",
                modifier = Modifier.padding(horizontal = 14.dp),
                style = TextStyle(
                    fontSize = 14.sp, color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipisici elit, sed do eiusmod tempor",
                modifier = Modifier.padding(horizontal = 11.dp)
            )
        }
    }
}
