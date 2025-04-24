package com.alef.souqleader.ui.presentation.proprtiesDetails


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.fonts.FontStyle
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.PropertyObject
import com.alef.souqleader.domain.model.AccountData
import com.bumptech.glide.Glide
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Composable
fun PropertyDetailsScreen(
    navController: NavController,
    modifier: Modifier,
    property: PropertyObject?
) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    Item(property)
    val context = LocalContext.current
    val file = generatePDF(context)
    file?.let {
        sharePdfFile(context, file)
    }
}

@Composable
fun Item(property: PropertyObject?) {
    Column(
        Modifier
            .background(colorResource(id = R.color.white))
            .fillMaxSize()
    ) {

        property?.let {
            ImageSliderExample(it)
            DetailsItem(it)
        }

    }
}

@Composable
fun DetailsItem(property: PropertyObject) {
    Column(
        Modifier
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .padding(bottom = 16.dp, top = 0.dp)
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Column {
                    property.getTitle()?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                color = colorResource(id = R.color.blue),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                    property.region_name?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                color = colorResource(id = R.color.lightGray),
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }
            Column {
                property.price?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = colorResource(id = R.color.black),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }

        DetailsProduct(property)

    }
}


@Composable
fun DetailsProduct(property: PropertyObject) {
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .verticalScroll(scrollState)
    ) {
        property.unit_no?.let { ReminderItem(stringResource(R.string.unit_no), it) }
        property.bulding_no?.let { ReminderItem(stringResource(R.string.building_no), it) }
        property.land_space?.let { ReminderItem(stringResource(R.string.land_space), it) }
        property.price?.let { ReminderItem(stringResource(R.string.price), it) }
        property.owner?.let { ReminderItem(stringResource(R.string.owner_name), it) }
        property.owner_mobile?.let { ReminderItem(stringResource(R.string.owner_mobile), it) }
        property.property_unit_type?.title()?.let {
            ReminderItem(
                stringResource(R.string.property_type),
                it
            )
        }
    }
}

@Composable
fun ReminderItem(text: String, text1: String) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp,
                    horizontal = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 12.sp, color = colorResource(id = R.color.gray)
                ),
            )
            Text(
                text = text1,
                style = TextStyle(
                    fontSize = 14.sp, color = colorResource(id = R.color.black)
                ),
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSliderExample(property: PropertyObject) {
    val images = listOf(
        "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
        "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
    )

    val pagerState = rememberPagerState()

    Column {
        property.gallery?.size?.let {
            HorizontalPager(
                count = it,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) { page ->
                Image(
                    painter = rememberImagePainter(data = AccountData.BASE_URL + property.gallery[page].image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }
}

fun sharePdfFile(context: Context, file: File) {
    val uri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider", // ← make sure this matches your Manifest
        file
    )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Share PDF"))
}

// Function to generate a simple PDF document
fun generatePDF(context: Context): File? {
    val pageHeight = 1120
    val pageWidth = 792
    val pdfDocument = PdfDocument()
    val paint = Paint()
    val title = Paint()
    val imageUrl = "https://img.freepik.com/free-vector/www-icon_23-2147934051.jpg?t=st=1745421910~exp=1745425510~hmac=182f00b3a52a1059cda82ad187b6ae7c96271499d79e75720ab2303f5637a09d&w=740"
    CoroutineScope(Dispatchers.Main).launch {
        val bitmap = loadBitmapFromUrl(context, imageUrl)

        val scaledBitmap = bitmap?.let { Bitmap.createScaledBitmap(it, 140, 140, false) }

        val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val myPage = pdfDocument.startPage(myPageInfo)
        val canvas: Canvas = myPage.canvas

        if (scaledBitmap != null) {
            canvas.drawBitmap(scaledBitmap, 56F, 40F, paint)
        }
    }


        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground)
        val bmp = drawable?.toBitmap()
        val scaledBitmap = bmp?.let { Bitmap.createScaledBitmap(it, 140, 140, false) }

        val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val myPage = pdfDocument.startPage(myPageInfo)
        val canvas: Canvas = myPage.canvas

        if (scaledBitmap != null) {
            canvas.drawBitmap(scaledBitmap, 56F, 40F, paint)
        }

        title.apply {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            textSize = 16F
            color = Color.Black.toArgb()
            textAlign = Paint.Align.CENTER
        }

        canvas.drawText("Geeks for Geeks", 209F, 100F, title)
        canvas.drawText("A portal for IT professionals.", 209F, 120F, title)
        canvas.drawText("This is a sample document which we have created.", 396F, 560F, title)

        pdfDocument.finishPage(myPage)

        val file = File(context.getExternalFilesDir(null), "hhhhh.pdf")

        return try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(context, "PDF saved at ${file.absolutePath}", Toast.LENGTH_SHORT).show()
            pdfDocument.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            pdfDocument.close()
            Toast.makeText(context, "Failed to generate PDF", Toast.LENGTH_SHORT).show()
            null
        }

}

suspend fun loadBitmapFromUrl(context: Context, imageUrl: String): Bitmap? =
    withContext(Dispatchers.IO) {
        try {
            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .submit()
                .get()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }