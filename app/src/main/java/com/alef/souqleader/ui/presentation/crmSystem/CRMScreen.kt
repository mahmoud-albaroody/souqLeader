package com.alef.souqleader.ui.presentation.crmSystem

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Comment
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.data.remote.dto.User
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CRMScreen(navController: NavController, modifier: Modifier, post: Post) {
    val postList = remember { mutableStateListOf<Comment>() }
    var comment by remember { mutableStateOf("") }

    postList.clear()
    post.comment?.let { postList.addAll(it) }
    val viewModel: CRMViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        viewModel.viewModelScope.launch {
            viewModel.stateAddComment.collect {
                // postList.clear()
                postList.add(
                    Comment(
                        comment = comment,
                        user = User(
                            photo = AccountData.photo,
                            name = AccountData.name
                        )
                    )
                )
            }
        }
    }
    CRMScreenItem(post, postList = postList) {
        comment = it
        if(post.postType=="companyType"){
            viewModel.addCompanyComment(it, post.id.toString())
        }else {
            viewModel.addComment(it, post.id.toString())
        }
    }
}


@Composable
fun CRMScreenItem(
    post: Post,
    postList: SnapshotStateList<Comment>, onSendTextClick: (String) -> Unit
) {
    val lazyColumnListState = rememberLazyListState()

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val scrollState = rememberScrollState()
    val corroutineScope = rememberCoroutineScope()



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
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
                Image(
                    painter = rememberAsyncImagePainter(
                        if (!post.images.isNullOrEmpty()) {
                            if (post.images[0].image?.isNotEmpty() == true) {
                                AccountData.BASE_URL + post.images[0].image
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
                        .padding(bottom = 8.dp)
                        .clip(RoundedCornerShape(16.dp))

                )
                Text(
                    text = post.post ?: "",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.blue),
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = post.post ?: "",
                    modifier = Modifier.padding(top = 8.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.gray),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Column(
                Modifier
                    .fillMaxWidth()
            ) {

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
                            text = post.likes_count.toString() +
                                    stringResource(id = R.string.like),
                            modifier = Modifier.padding(start = 8.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.gray),
                                fontWeight = FontWeight.Bold
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
                            text = postList.size.toString() + " " + stringResource(R.string.comment),
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .padding(end = 16.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(id = R.color.gray),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.comments),
                    modifier = Modifier.padding(top = 16.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                )
            }


            LazyColumn(state = lazyColumnListState, content = {
                items(postList) {
                    CommentItem(it)
                }
            })

        }
        Column(
            Modifier.align(Alignment.BottomCenter)
        ) {
            AddComment {
                onSendTextClick(it)

            }
        }

    }
    SideEffect {
        corroutineScope.launch {
            lazyColumnListState.scrollToItem(lazyColumnListState.layoutInfo.totalItemsCount)
        }
    }
}

@Composable
fun AddComment(onSendTextClick: (String) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.grey100))
    ) {
        ReminderItem(
            text = stringResource(R.string.add_comment),
            text1 = "qwqw"
        ) {

            onSendTextClick(it)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderItem(text: String, text1: String, onSendTextClick: (String) -> Unit) {
    var textState by remember { mutableStateOf("") }
    val maxLength = 110


    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = textState,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = colorResource(id = R.color.black),
            disabledLabelColor = colorResource(id = R.color.lightBlue),
            focusedIndicatorColor = colorResource(id = R.color.transparent),
            unfocusedIndicatorColor = colorResource(id = R.color.transparent)
        ),
        placeholder = {
            Text(text = stringResource(id = R.string.add_comment))
        },
        onValueChange = {
            if (it.length <= maxLength) textState = it
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = {
                onSendTextClick(textState)
                textState = ""
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_send_24),
                    contentDescription = null
                )
            }
        }
    )

}

@Composable
fun CommentItem(comment: Comment) {
    Row(Modifier.padding(top = 14.dp)) {
        Image(
            painter = rememberAsyncImagePainter(
                if (comment.user?.photo?.isNotEmpty() == true) {
                    AccountData.BASE_URL + comment.user.photo
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
            Text(
                text = comment.user?.name ?: "",
                modifier = Modifier.padding(horizontal = 14.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = comment.comment ?: "",
                modifier = Modifier.padding(horizontal = 14.dp),
                style = TextStyle(
                    fontSize = 12.sp, color = colorResource(id = R.color.gray)
                )
            )
        }
    }
}
