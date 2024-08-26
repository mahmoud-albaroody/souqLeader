package com.alef.souqleader.ui.presentation.timeline

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.SoundEffectConstants
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.theme.*
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


@Composable
fun TimelineScreen(navController: NavController, modifier: Modifier) {
    val viewModel: TimeLineViewModel = hiltViewModel()
    val posts = remember { mutableStateListOf<Post>() }
    var visibleMeda by remember { mutableStateOf(false) }
    //  val posts = mutableStateListOf<Post>()
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        visibleMeda = true
    }
    var likedPost: Post? = null
    LaunchedEffect(key1 = true) {
        viewModel.getPosts()
    }
    viewModel.addLike.observe(LocalLifecycleOwner.current) {
        if (it.status) {
            if (likedPost != null) {
                val index = posts.indexOf(likedPost)
                if (likedPost?.isLiked == 1) {
                    posts[index] = posts[index].copy(isLiked = 0)
                } else {
                    posts[index] = posts[index].copy(isLiked = 1)
                }
            }
        }
    }
    viewModel.statePosts.observe(LocalLifecycleOwner.current) {
        posts.clear()
        it?.let { it1 -> posts.addAll(it1) }
    }


    Column {
        WriteTextPost(onSelectImage = {
            launcher.launch("image/*")

        },
            onPostClick = {
                val images: ArrayList<MultipartBody.Part> = arrayListOf()
                val requestFile: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), it)
//            val filePath = getPathFromUri(this, fileUri)
//
//            if (filePath != null) {
//                // Create File instance from file path
//                val file = File(filePath)
//            }
                val body: MultipartBody.Part =
                    MultipartBody.Part.createFormData("file", "", requestFile)

                images.add(body)

                viewModel.addPost(requestFile, images)
                visibleMeda = false
            }, onOpenCamera = {
            }
        )
        if (visibleMeda)
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 36.dp)
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .width(100.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

        LazyColumn(Modifier.padding(horizontal = 24.dp)) {
            items(posts) { post ->
                TimelineItem(
                    post, onTimelineCLick = {
                        val postJson = post.toJson()
                        navController.navigate(
                            Screen.CRMScreen.route
                                .plus("?" + Screen.CRMScreen.objectName + "=${postJson}")
                        )
                    },
                    onLikeClick = {
                        likedPost = post

                        if (post.isLiked == 1) {
                            viewModel.addLike("0", post.id.toString())
                        } else {
                            viewModel.addLike("1", post.id.toString())
                        }
                    })
            }
        }
    }
   // Camera()

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteTextPost(
    onPostClick: (String) -> Unit,
    onSelectImage: () -> Unit,
    onOpenCamera: () -> Unit
) {
    var textState by remember { mutableStateOf("") }
    Column {
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
                    value = textState,
                    placeholder = {
                        Text(text = stringResource(R.string.write_your_post))
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = colorResource(id = R.color.black),
                        disabledLabelColor = colorResource(id = R.color.blue),
                        focusedIndicatorColor = colorResource(id = R.color.transparent),
                        unfocusedIndicatorColor = colorResource(id = R.color.transparent)
                    ),
                    onValueChange = {
                        textState = it
                    },
//                shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                )

            }
        }
        MediaPost(onPostClick = {
            onPostClick(textState)
        },
            onSelectImage = {
                onSelectImage()
            },
            onOpenCamera = {

            })
    }
}


@Composable
fun MediaPost(
    onPostClick: () -> Unit, onSelectImage: () -> Unit,
    onOpenCamera: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            Modifier
                .weight(2f)
                .padding(horizontal = 8.dp)
        ) {
            Image(
                painterResource(R.drawable.gallery),
                modifier = Modifier
                    .clickable {
                        onSelectImage()
                        //   onFilterClick.invoke()
                    }
                    .weight(0.5f),
                contentDescription = "",
            )

            Image(
                painterResource(R.drawable.camera_upload),
                contentDescription = "",
                Modifier
                    .weight(0.5f)
                    .clickable {
                        onOpenCamera()
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
            .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue2)),
            onClick = {
                onPostClick()
            }) {
            Text(
                text = stringResource(R.string.post), Modifier.padding(vertical = 6.dp),
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)
            )
        }
    }


}

@Composable
fun TimelineItem(post: Post, onTimelineCLick: () -> Unit, onLikeClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var likedIcon: Int = R.drawable.like
    if (post.isLiked == 1) {
        likedIcon = R.drawable.ic_like_blue
    }
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
                    color = colorResource(id = R.color.blue),
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
                Row(modifier = Modifier.clickable {
                    onLikeClick.invoke()
                }, verticalAlignment = Alignment.CenterVertically) {

                    Image(
                        painterResource(likedIcon),
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


@Preview
@Composable
fun Camera() {
    val context = LocalContext.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }
    val previewView: PreviewView = remember { PreviewView(context) }
    val lifecycleOwner = LocalLifecycleOwner.current

    Column {
        val localView = LocalView.current
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {
                    localView.playSoundEffect(SoundEffectConstants.CLICK)
                    startCamera(context, lifecycleOwner, previewView) { value ->

                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Camera,
                    contentDescription = "Camera",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(54.dp)
                )
            }
        }
    }
}

private fun startCamera(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    onScan: (String) -> Unit
) {
    val cameraController = LifecycleCameraController(context)
    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    val barcodeScanner = BarcodeScanning.getClient(options)
    val mlKitAnalyzer = MlKitAnalyzer(
        listOf(barcodeScanner),
        COORDINATE_SYSTEM_VIEW_REFERENCED,
        ContextCompat.getMainExecutor(context)
    ) { result ->
        val barcodeResult = result?.getValue(barcodeScanner)
        val scannedBarcodeValue = barcodeResult?.firstOrNull()?.rawValue ?: ""
        onScan(scannedBarcodeValue)
    }
    cameraController.setImageAnalysisAnalyzer(
        ContextCompat.getMainExecutor(context),
        mlKitAnalyzer
    )
    cameraController.bindToLifecycle(lifecycleOwner)
    previewView.controller = cameraController
}