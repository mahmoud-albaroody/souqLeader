package com.alef.souqleader.ui.presentation.companyTimeline

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.SoundEffectConstants
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.Info
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.presentation.timeline.TimeLineViewModel
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CompanyTimelineScreen(
    navController: NavController,
    modifier: Modifier,
    mainViewModel: MainViewModel
) {
    val viewModel: CompanyTimeLineViewModel = hiltViewModel()
    val posts = remember { mutableStateListOf<Post>() }
    var visibleMeda by remember { mutableStateOf(false) }
    var page = 1
    var info by remember { mutableStateOf(Info()) }
    var likedPost by remember { mutableStateOf<Post?>(null) }
    val context = LocalContext.current
    val listState = rememberLazyListState()
    //  val posts = mutableStateListOf<Post>()
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var vedioPath by remember {
        mutableStateOf<Uri?>(null)
    }
    var videoUrl: Uri? = null

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            vedioPath = null
            visibleMeda = true
        } else {
            visibleMeda = false
        }
    }

    val pickVedioLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            vedioPath = it
            imageUri = null
            visibleMeda = false
        }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            // Save the Bitmap to a file and get the Uri
            val uri = saveBitmapToFile(context, bitmap)
            imageUri = uri
            visibleMeda = true
            vedioPath = null
        }
    }
    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) {
        vedioPath = videoUrl
        visibleMeda = false
        imageUri = null
    }



    LaunchedEffect(key1 = true) {
        viewModel.getCompanyPost(page)
        viewModel.viewModelScope.launch {
            viewModel.statePosts.collect {

                if (it is Resource.Success) {


                    it.data?.let {
                        if (it.info != null)
                            info = it.info
                        it.data.let { it1 -> posts.addAll(it1) }
                    }
                    mainViewModel.showLoader = false
                } else if (it is Resource.Loading) {
                    if (page == 1)
                        mainViewModel.showLoader = true
                } else if (it is Resource.DataError) {
                    mainViewModel.showLoader = false
                }
            }
        }
    }
    viewModel.addLike.observe(LocalLifecycleOwner.current) {
        if (it.status) {
            if (likedPost != null) {
                val index = posts.indexOf(posts.find { it.id == likedPost!!.id })
                if (likedPost?.isLiked == 1) {
                    posts[index] = posts[index].copy(isLiked = 0)
                    posts[index] = posts[index].copy(likes_count = posts[index].likes_count!! - 1)

                } else {
                    posts[index] = posts[index].copy(isLiked = 1)
                    posts[index] = posts[index].copy(likes_count = posts[index].likes_count!! + 1)
                }
            }
        }
    }




    Column {
        WriteTextPost(onSelectImage = {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        },
            onPostClick = {
                val images: ArrayList<MultipartBody.Part> = arrayListOf()

                imageUri?.let { uri ->
                    val parcelFileDescriptor =
                        context.contentResolver.openFileDescriptor(uri, "r", null)
                    parcelFileDescriptor?.let { pfd ->
                        val inputStream = FileInputStream(pfd.fileDescriptor)
                        val file = File(context.cacheDir, "temp_image_file")
                        val outputStream = FileOutputStream(file)
                        inputStream.copyTo(outputStream)

                        // Close streams
                        inputStream.close()
                        outputStream.close()
                        pfd.close()
                        val requestFile: RequestBody =
                            RequestBody.create("image/*".toMediaType(), file)
                        val imagePart =
                            MultipartBody.Part.createFormData("images[]", file.name, requestFile)

                        images.add(imagePart)
                        val name: RequestBody = RequestBody.create(
                            "text/plain".toMediaType(),
                            "post2"
                        )
                        // Call the ViewModel's addPost function with the necessary parameters
                        viewModel.addPost(name, images)
                    }
                }
//                imageUri?.let { uri ->
//                    // Create a File object from the URI
//                    val file = File(uri.path ?: "")
//
//                    // Convert the file to RequestBody
//                    val requestFile: RequestBody = RequestBody.create(
//                        "image/*".toMediaType(),
//                        file
//                    )
//                    val name: RequestBody = RequestBody.create(
//                      "text/plain".toMediaType(),
//                   "post"
//                    )
//                    // Create MultipartBody.Part using the file's name and the request body
//                    val body: MultipartBody.Part =
//                        MultipartBody.Part.createFormData("file", file.name, requestFile)
//
//                    // Add the body to the images list
//                    images.add(body)
//
//                    // Call the ViewModel's addPost function with the necessary parameters
//                    viewModel.addPost(name, images)
//                }
                visibleMeda = false
            }, onOpenCamera = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        2
                    )
                }
                cameraLauncher.launch(null)
            }, onVedio = {
                // Create file and URI using FileProvider
                val videoFile = File(context.getExternalFilesDir(null), "video.mp4")
                val videoUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    videoFile
                )
                videoUrl = videoUri

                // Launch the camera to record video
                videoLauncher.launch(videoUri)
            },
            onPickVideo = {
                pickVedioLauncher.launch(
                    PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly)
                )
            }
        )
        if (visibleMeda && imageUri != null)
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

        if (vedioPath != null)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 36.dp),
                text = vedioPath?.path.toString()
            )

        LazyColumn(state = listState, modifier = Modifier.padding(horizontal = 24.dp)) {
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
            if (info.pages != null)
                if (info.pages!! > info.count!!) {
                    item {
                        if (posts.isNotEmpty()) {
                            viewModel.getCompanyPost(++page)
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(16.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                    }
                }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteTextPost(
    onPostClick: (String) -> Unit,
    onSelectImage: () -> Unit,
    onOpenCamera: () -> Unit,
    onVedio: () -> Unit,
    onPickVideo: () -> Unit
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
                onOpenCamera()
            },
            onVideo = {
                onVedio()
            }, onPickVideo = {
                onPickVideo()
            })
    }
}


@Composable
fun MediaPost(
    onPostClick: () -> Unit, onSelectImage: () -> Unit,
    onOpenCamera: () -> Unit, onVideo: () -> Unit, onPickVideo: () -> Unit
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
                        onVideo()
                    }
            )
            Image(
                painterResource(R.drawable.record_upload),
                contentDescription = "",
                Modifier
                    .weight(0.5f)
                    .clickable {
                        onPickVideo()

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
            .padding(vertical = 6.dp)
            .clickable { onTimelineCLick.invoke() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize(),

            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    if (post.images?.isNotEmpty() == true) {
                        AccountData.BASE_URL + post.images[0].image
                    } else {
                        ""
                    }
                ),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .height(screenHeight * 0.22f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(percent = 10))
            )
            post.post?.let {
                Text(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .padding(horizontal = 16.dp),
                    text = it,
                    style = TextStyle(
                        color = colorResource(id = R.color.blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }


            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
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
        CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED,
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
}// Function to save the Bitmap to a file and return the Uri

fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
    val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
    return try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}