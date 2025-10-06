package com.alef.souqleader.ui.presentation.timeline

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.SoundEffectConstants
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.alef.souqleader.data.remote.dto.Image
import com.alef.souqleader.data.remote.dto.Post
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.MainActivity
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.extention.toJson
import com.alef.souqleader.ui.navigation.Screen
import com.alef.souqleader.ui.openPdfFile
import com.alef.souqleader.ui.presentation.crmSystem.ImageSlider
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets



@Composable
fun TimelineScreen(navController: NavController, modifier: Modifier, mainViewModel: MainViewModel) {
    val viewModel: TimeLineViewModel = hiltViewModel()
    val posts = viewModel.posts
    var visibleMeda by remember { mutableStateOf(false) }
    var loadMore by remember { mutableStateOf(true) }
    var info by remember { mutableStateOf(Info()) }
    var likedPost by remember { mutableStateOf<Post?>(null) }
    var deletedPost by remember { mutableStateOf<Post?>(null) }
    val images = remember { mutableStateListOf<Uri>() }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val listState = rememberLazyListState()
    var isDataLoaded by rememberSaveable { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var isUploading by remember { mutableStateOf(false) }
    var uploadResult by remember { mutableStateOf<String?>(null) }

    //  val posts = mutableStateListOf<Post>()
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var pdfUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var vedioPath by remember {
        mutableStateOf<Uri?>(null)
    }
    var videoUrl: Uri? = null

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.PickMultipleVisualMedia()
    ) { uri: List<Uri> ->
        if (uri.isNotEmpty()) {
            imageUri = uri[0]
            images.addAll(uri)
            // imageUri?.let { it1 -> images.add(it1) }
            vedioPath = null
            visibleMeda = true
        } else {
            visibleMeda = false
        }
    }

    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {

                imageUri = uri
                imageUri?.let { it1 -> images.add(it1) }
                visibleMeda = true
                vedioPath = null
            }
        }
    )


    val pickVedioLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            vedioPath = it
            imageUri = null
            images.clear()
            visibleMeda = false
        }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            // Save the Bitmap to a file and get the Uri
            val uri = saveBitmapToFile(context, bitmap)
            pdfUri = uri
            pdfUri?.let { it1 -> images.add(it1) }
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
        images.clear()
    }



    LaunchedEffect(Unit) {
        if (!isDataLoaded) {
            isDataLoaded = true
            viewModel.getPosts(viewModel.page)
            viewModel.viewModelScope.launch {
                viewModel.statePosts.collect {
                    imageUri = null
                    images.clear()
                    if (it is Resource.Success) {
                        it.data?.let {
                            if (it.info != null)
                                info = it.info

                            it.data.let { it1 ->
                                if (viewModel.page == 1) {
                                    posts.clear()
                                }
                                posts.addAll(it1)
                            }
                        }
                        loadMore = true
                        mainViewModel.showLoader = false
                    } else if (it is Resource.Loading) {
                        if (viewModel.page == 1)
                            mainViewModel.showLoader = true
                    } else if (it is Resource.DataError) {
                        mainViewModel.showLoader = false
                    }

                }
            }
        }

        viewModel.addPosts.observe(context as MainActivity) {
            isUploading = false
            uploadResult = "تم رفع الملف بنجاح ✅"
            if (it.status) {
                viewModel.page = 1
                viewModel.getPosts(viewModel.page)
            } else {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
            }
        }
        viewModel.deletePost.observe(context) {
            if (it.status) {
            } else {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }
    viewModel.addLike.observe(context as MainActivity) {
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

                if (it.isEmpty()) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.please_add_a_caption_to_your_post),
                        Toast.LENGTH_LONG
                    ).show()
                }
                else {
                    isUploading = true
                    uploadResult = null
                    val imagesMultipart: ArrayList<MultipartBody.Part> = arrayListOf()
                    // if(imageUri==null)
                    if (images.isEmpty()) {
                        val name: RequestBody = RequestBody.create(
                            "text/plain".toMediaType(),
                            it
                        )
                        viewModel.addPost(name, null)
                    } else {
                        images.forEach {
                            val parcelFileDescriptor =
                                context.contentResolver.openFileDescriptor(it, "r", null)
                            parcelFileDescriptor?.let { pfd ->
                                val inputStream = FileInputStream(pfd.fileDescriptor)
                                val file = File(
                                    context.cacheDir,
                                    "temp_image_file_${System.currentTimeMillis()}"
                                )
                                val outputStream = FileOutputStream(file)
                                inputStream.copyTo(outputStream)

                                // Close streams
                                inputStream.close()
                                outputStream.close()
                                pfd.close()

                                val fileExtension: String =
                                    if (context.contentResolver.getType(it) == "application/pdf") {
                                        ".pdf"

                                    } else {
                                        ".jpg"
                                    }
                                val type: String =
                                    if (context.contentResolver.getType(it) == "application/pdf") {
                                        "application/pdf"

                                    } else {
                                        "image/jpeg"
                                    }


                                val requestFile: RequestBody =
                                    RequestBody.create(type.toMediaType(), file)
                                val imagePart =
                                    MultipartBody.Part.createFormData(
                                        "images[]",
                                        file.name + fileExtension,
                                        requestFile
                                    )

                                imagesMultipart.add(imagePart)
                            }
                        }


                        val name: RequestBody = RequestBody.create(
                            "text/plain".toMediaType(),
                            it
                        )
                        mainViewModel.showLoader = true
                        viewModel.addPost(name, imagesMultipart)
                    }

                    visibleMeda = false
                }


            },
            onOpenCamera = {
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
                } else {
                    cameraLauncher.launch(null)
                }
            },
            onVedio = {
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
            },
            onPDFClick = {
                pdfLauncher.launch(arrayOf("application/pdf")) // Allow PDF only
            }
        )
        if (visibleMeda && images.isNotEmpty())
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 24.dp)
            ) {
                items(images) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxHeight()
                                .width(100.dp)
                        ) {
                            if (context.contentResolver.getType(it) == "application/pdf") {
                                Image(
                                    painter = painterResource(id = R.drawable.upload_pdf),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(12.dp))
                                )
                            } else {
                                AsyncImage(
                                    model = it,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(12.dp))
                                )
                            }
                            // Delete icon at top-right corner
                            IconButton(
                                onClick = {
                                    images.remove(it)
                                },
                                modifier = Modifier
                                    .size(25.dp)
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                                    .background(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        shape = CircleShape
                                    )
                            ) {
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    imageVector = Icons.Default.Delete, // or use a custom icon if preferred
                                    contentDescription = "Delete Image",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }

        if (vedioPath != null)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 36.dp),
                text = vedioPath?.path.toString()
            )
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = {
                refreshing = true
                coroutineScope.launch {
                    posts.clear()
                    viewModel.page = 1
                    viewModel.getPosts(viewModel.page)
                    refreshing = false
                }
            }
        ) {
            LazyColumn(state = listState, modifier = Modifier.padding(horizontal = 24.dp)) {
                items(posts) { post ->
                    TimelineItem(
                        post, onTimelineCLick = {
                            Screen.CRMScreen.title = "Timeline"
//                        post.postType = "timelineCompany"
//                        val postJson =
//                            URLEncoder.encode(post.toJson(), StandardCharsets.UTF_8.toString())
                            navController.navigate(
                                Screen.CRMScreen.route
                                    .plus("?" + Screen.CRMScreen.objectName + "=${post.id}")
                            )
                        },
                        onLikeClick = {
                            likedPost = post
                            if (post.isLiked == 1) {
                                viewModel.addLike("0", post.id.toString())
                            } else {
                                viewModel.addLike("1", post.id.toString())
                            }
                        },
                        onDeletePostClick = {
                            deletedPost = post
                            showDialog = true
                        }, onPDFClick = {
                            openPdfFile(context, AccountData.BASE_URL + it.image)
                        })
                }
                if (info.pages != null && loadMore)
                    if (info.pages!! > viewModel.page) {
                        item {
                            if (posts.isNotEmpty()) {
                                viewModel.viewModelScope.launch {
                                    delay(1000)
                                    loadMore = false
                                    viewModel.getPosts(++viewModel.page)
                                }
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
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
    if (isUploading) {
        // Dialog Loader
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {},
//            title = { Text("جاري رفع الملف...") },
            text = {
                Column (Modifier.fillMaxWidth()){
                    Row(Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text(stringResource(R.string.uploading))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        CircularProgressIndicator()
//                        Spacer(modifier = Modifier.width(16.dp))
//                        Text(stringResource(R.string.waiting))
                    }
                }

            }
        )
    }
    DeletePostDialog(showDialog, onDismiss = {
        showDialog = false
    }, onConfirm = {
        viewModel.deletePost(deletedPost?.id.toString())
        showDialog = false
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteTextPost(
    onPostClick: (String) -> Unit,
    onSelectImage: () -> Unit,
    onOpenCamera: () -> Unit,
    onVedio: () -> Unit,
    onPickVideo: () -> Unit,
    onPDFClick: () -> Unit
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
                    colors = TextFieldDefaults.colors(
                        cursorColor = colorResource(id = R.color.black),
                        disabledLabelColor = colorResource(id = R.color.blue),
                        focusedIndicatorColor = colorResource(id = R.color.transparent),
                        unfocusedIndicatorColor = colorResource(id = R.color.transparent)
                    ),
                    onValueChange = {
                        textState = it
                    },
                    singleLine = true,
                )

            }
        }
        MediaPost(onPostClick = {
            onPostClick(textState)
            textState = ""
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
            }, onPDFClick = {
                onPDFClick()
            })
    }
}


@Composable
fun MediaPost(
    onPostClick: () -> Unit, onSelectImage: () -> Unit,
    onOpenCamera: () -> Unit,
    onVideo: () -> Unit, onPickVideo: () -> Unit,
    onPDFClick: () -> Unit
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
                .weight(1f)
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
//            Image(
//                painterResource(R.drawable.video_play),
//                contentDescription = "",
//                Modifier
//                    .weight(0.5f)
//                    .clickable {
//                        onVideo()
//                    }
//            )
//            Image(
//                painterResource(R.drawable.record_upload),
//                contentDescription = "",
//                Modifier
//                    .weight(0.5f)
//                    .clickable {
//                        onPickVideo()
//
//                    }
//            )
            Image(
                painterResource(R.drawable.pdf_svgrepo_com),
                contentDescription = "",
                Modifier
                    .weight(0.5f)
                    .clickable {
                        onPDFClick()

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
fun TimelineItem(
    post: Post, onTimelineCLick: () -> Unit,
    onLikeClick: () -> Unit,
    onDeletePostClick: (Post) -> Unit,
    onPDFClick:(Image)->Unit
) {
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(9.2f)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        Image(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                            painter = rememberAsyncImagePainter(
                                if (post.user?.photo?.isNotEmpty() == true) {
                                    AccountData.BASE_URL + post.user.photo
                                } else {
                                    R.drawable.user_profile_placehoder
                                }
                            ),
                            contentDescription = ""
                        )
                        Image(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(10.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                            painter = rememberAsyncImagePainter(
                                if (post.user?.is_online == 1) {
                                    R.drawable.ellipse_green
                                } else {
                                    R.drawable.ellipse_gray
                                }
                            ),
                            contentDescription = ""
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        post.user?.name?.let {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = it, fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        post.getDate()?.let {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = it,
                                fontSize = 11.sp,
                            )
                        }
                    }

                }
                if (post.tenant_id == AccountData.tenantId && AccountData.userId == post.user?.id)
                    Image(
                        painter = painterResource(R.drawable.icons8_delete),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(percent = 10))
                            .clickable {

                                onDeletePostClick(post)
                            }
                    )
            }
            post.post?.let {
                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 16.dp),
                    text = it,
                    style = TextStyle(
                        color = colorResource(id = R.color.blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }


            if (!post.images.isNullOrEmpty())

                ImageSlider(post.images, onPDFClick = {
                    onPDFClick(it)
                })
//                Image(
//                    painter = rememberAsyncImagePainter(
//                        if (post.images.isNotEmpty()) {
//                            AccountData.BASE_URL + post.images[0].image
//                        } else {
//                            ""
//                        }
//                    ),
//                    contentDescription = "",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .height(screenHeight * 0.22f)
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(percent = 10))
//                )



            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 8.dp),
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

@Composable
fun DeletePostDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = stringResource(R.string.delete_post))
            },
            text = {
                Text(stringResource(R.string.are_you_sure_you_want_to_delete_this_post))
            },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(stringResource(R.string.delete), color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}