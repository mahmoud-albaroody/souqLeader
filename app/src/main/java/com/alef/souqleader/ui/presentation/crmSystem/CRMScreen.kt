package com.alef.souqleader.ui.presentation.crmSystem

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Comment
import com.alef.souqleader.data.remote.dto.Image
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.data.remote.dto.User
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.navigation.currentRoute
import com.alef.souqleader.ui.openPdfFile
import com.alef.souqleader.ui.presentation.timeline.DeletePostDialog
import com.alef.souqleader.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CRMScreen(navController: NavController, modifier: Modifier, postId: String) {
    val commentList = remember { mutableStateListOf<Comment>() }
    var comment by remember { mutableStateOf("") }
    var post by remember { mutableStateOf<Post?>(null) }
    var commentObject by remember { mutableStateOf<Comment?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val viewModel: CRMViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        if (Screen.CRMScreen.title == "Timeline") {
            viewModel.timelinePost(postId)
        } else {
            viewModel.companyPost(postId)
        }
        viewModel.viewModelScope.launch {
            viewModel.post.collect {
                post = it.data
            }
        }
        commentList.clear()
        post?.comment?.let { commentList.addAll(it) }
        viewModel.viewModelScope.launch {
            viewModel.stateAddComment.collect {
                // postList.clear()
                commentList.add(
                    Comment(
                        comment = comment,
                        user = User(
                            photo = AccountData.photo,
                            name = AccountData.name,
                            id = AccountData.userId
                        )
                    )
                )
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.stateDeleteComment.collect {
                if (Screen.CRMScreen.title == "Timeline") {
                    viewModel.getComments(post?.id.toString())
                } else {
                    viewModel.getCompanyComment(post?.id.toString())
                }

            }
        }
        viewModel.viewModelScope.launch {
            viewModel.stateComments.collect {
                commentList.clear()
                commentList.addAll(it.data)
            }
        }
    }
    LaunchedEffect(key1 = true) {
        if (Screen.CRMScreen.title == "Timeline") {
            viewModel.getComments(postId)
        } else {
            viewModel.getCompanyComment(postId)
        }
    }

    post?.let { it ->
        CRMScreenItem(it, postList = commentList, onRemoveComment = {
            showDialog = true
            commentObject = it


        }, onSendTextClick = { commit ->
            comment = commit
            if (Screen.CRMScreen.title == "Timeline") {
                viewModel.addComment(commit, it.id.toString())
            } else {
                viewModel.addCompanyComment(commit, it.id.toString())
            }
        })
    }
    DeletePostDialog(showDialog, onDismiss = {
        showDialog = false
    }, onConfirm = {

        if (Screen.CRMScreen.title == "Timeline") {
            viewModel.deleteComment(commentObject?.id.toString())
        } else {
            viewModel.deleteCompanyComment(commentObject?.id.toString())
        }

        showDialog = false
    })
}


@Composable
fun CRMScreenItem(
    post: Post,
    postList: SnapshotStateList<Comment>,
    onSendTextClick: (String) -> Unit,
    onRemoveComment: (Comment) -> Unit
) {
    val lazyColumnListState = rememberLazyListState()

    val corroutineScope = rememberCoroutineScope()
    val visibleSlider by remember { mutableStateOf(false) }
    val context = LocalContext.current


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
                if (!post.images.isNullOrEmpty())
                    ImageSlider(post.images,onPDFClick = {
                        openPdfFile(context, AccountData.BASE_URL + it.image)
                    })
//                Image(
//                    painter = rememberAsyncImagePainter(
//                        if (post.images.isNotEmpty()) {
//                            if (post.images[0].image?.isNotEmpty() == true) {
//                                AccountData.BASE_URL + post.images[0].image
//                            } else {
//                                //  R.drawable.user_profile_placehoder
//                            }
//                        } else {
//
//                        }
//                    ),
//                    contentDescription = "",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.clickable {
//                        visibleSlider = true
//                    }
//                        .fillMaxWidth()
//                        .height(screenHeight / 4)
//                        .padding(bottom = 8.dp)
//                        .clip(RoundedCornerShape(16.dp))
//
//                )
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
                    CommentItem(it, onRemoveComment = {
                        onRemoveComment(it)
                    })
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
        if (visibleSlider) {
            post.images?.let { ImageSlider(it,onPDFClick = {
                openPdfFile(context, AccountData.BASE_URL + it.image)
            }) }
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
        colors = TextFieldDefaults.colors(
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
fun CommentItem(comment: Comment, onRemoveComment: (Comment) -> Unit) {
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
            Modifier
                .fillMaxSize()
                .weight(9f),
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
        if (comment.user?.id == AccountData.userId) {
            Image(
                painter = painterResource(R.drawable.icons8_delete),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .size(2.dp, 20.dp)
                    .clip(RoundedCornerShape(percent = 10))
                    .clickable {
                        onRemoveComment(comment)
                    }
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(imageUrls: List<Image>,onPDFClick: ((Image) -> Unit)? = null) {
    val pagerState = rememberPagerState()
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    Column {
        HorizontalPager(
            count = imageUrls.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            if ((imageUrls[page].image?.contains("pdf") == true)) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(R.drawable.pdf_document_svgrepo_com),
                        contentDescription = "",
                        Modifier
                            .weight(0.5f)
                    )
                    Text(
                        text = stringResource(R.string.pdf_document),
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        ),
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.gray)
                        )
                    )
                    Button(modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue2)),
                        onClick = {
                            onPDFClick?.invoke(imageUrls[page])
                        }) {
                        Text(
                            text = stringResource(R.string.view_pdf), Modifier.padding(vertical = 4.dp),
                            style = TextStyle(textAlign = TextAlign.Center, fontSize = 13.sp)
                        )
                    }

                }

            }
            else {
                AsyncImage(
                    model = AccountData.BASE_URL + imageUrls[page].image,
                    contentDescription = "Slider Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            selectedImageUrl = AccountData.BASE_URL + imageUrls[page].image
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )
    }

    selectedImageUrl?.let { url ->
        Dialog(onDismissRequest = { selectedImageUrl = null }) {
            var scale by remember { mutableStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }
            val state = rememberTransformableState { zoomChange, offsetChange, _ ->
                scale *= zoomChange
                offset += offsetChange
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale = (scale * zoom).coerceIn(1f, 5f)
                                offset += pan
                            }
                        }
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .transformable(state = state)
                        .align(Alignment.Center)
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = "Full Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
                // Close button (top-right)
                IconButton(
                    onClick = { selectedImageUrl = null },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .background(Color.Red.copy(alpha = 0.3f), shape = CircleShape)

                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }

            }
        }
    }

}