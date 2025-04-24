package com.alef.souqleader.ui.presentation.proprtiesDetails


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.fonts.FontStyle
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.util.Log
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.alef.souqleader.ui.MainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Composable
fun PropertyDetailsScreen(
    navController: NavController,
    modifier: Modifier,
    mainViewModel: MainViewModel,
    property: PropertyObject?
) {
    //val viewModel: DetailsGymScreenViewModel = viewModel()
    mainViewModel.showShareIcon = true
    val imageUrls = arrayListOf<String>()
    Item(property)
    val context = LocalContext.current
    property?.gallery?.forEach {
        it.image?.let { it1 -> imageUrls.add(AccountData.BASE_URL + it1) }
    }


    LaunchedEffect(key1 = Unit) {
        mainViewModel.onShareClick.collect {
            if (it)
                generatePdfFromImageArray(context, imageUrls, property)
        }
    }




    DisposableEffect(Unit) {
        onDispose {
            mainViewModel.showShareIcon = false
        }
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
fun generatePDF(context: Context, images: List<Bitmap>, property: PropertyObject?): File? {
    val pageHeight = 1120
    val pageWidth = 792
    val pdfDocument = PdfDocument()
    val paint = Paint()
    val title = Paint()

    val drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground)
    val bmp = drawable?.toBitmap()
    val scaledBitmap = bmp?.let { Bitmap.createScaledBitmap(it, 140, 140, false) }

    val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val myPage = pdfDocument.startPage(myPageInfo)
    val canvas: Canvas = myPage.canvas

    if (scaledBitmap != null) {
        canvas.drawBitmap(scaledBitmap, 20F, 10F, paint)
    }

    title.apply {
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        textSize = 16F
        color = Color.Black.toArgb()
        textAlign = Paint.Align.CENTER
    }

    canvas.drawText(context.getString(R.string.app_name), 160F, 75F, title)

    val cellPadding = 10
    val tableStartX = 50f
    var tableStartY = 250f
    val columnWidth = pageWidth / 2f - 60
    val rowHeight = 50f
    val titlePaint = Paint().apply {
        typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD)
        textSize = 18f
        color = context.getColor(R.color.black)
    }
    // Draw header
    property?.getTitle()?.let { canvas.drawText(it, tableStartX, 130f, titlePaint) }
    property?.description()?.let { canvas.drawText(it, tableStartX, 160f, titlePaint) }
    canvas.drawText(
        context.getString(R.string.location) + property?.regions?.getTitle(),
        tableStartX,
        190f,
        titlePaint
    )
    canvas.drawText(context.getString(R.string.property_details), tableStartX, 220f, titlePaint)

    val tableData = listOf(
        context.getString(R.string.unit_no) to property?.unit_no.toString(),
        context.getString(R.string.building_no) to property?.bulding_no.toString(),
        context.getString(R.string.land_space) to property?.land_space.toString(),
        context.getString(R.string.bua) to property?.bua.toString(),
        context.getString(R.string.price) to property?.price.toString(),
        context.getString(R.string.meter_price) to property?.meter_price.toString(),
        context.getString(R.string.owner_name) to property?.owner.toString(),
        context.getString(R.string.owner_mobile) to property?.owner_mobile.toString(),
        context.getString(R.string.bedrooms) to property?.bedrooms.toString(),
        context.getString(R.string.bathrooms) to property?.bathrooms.toString(),
        context.getString(R.string.floor) to property?.floor.toString(),
        context.getString(R.string.unit_code) to property?.unit_code.toString(),
        context.getString(R.string.category) to property?.property_category?.getTitle().toString(),
        context.getString(R.string.department) to property?.property_department?.getTitle().toString(),
        context.getString(R.string.unit_type) to property?.unit_type?.toString()
    )

    for ((label, value) in tableData) {
        // Left column - label
        canvas.drawRect(
            tableStartX,
            tableStartY,
            tableStartX + columnWidth,
            tableStartY + rowHeight,
            paint.apply { style = Paint.Style.STROKE }
        )
        canvas.drawText(
            label,
            tableStartX + cellPadding,
            tableStartY + rowHeight / 2 + 5,
            paint.apply { style = Paint.Style.FILL }
        )

        // Right column - value
        canvas.drawRect(
            tableStartX + columnWidth,
            tableStartY,
            tableStartX + 2 * columnWidth,
            tableStartY + rowHeight,
            paint.apply { style = Paint.Style.STROKE }
        )
        canvas.drawText(
            value.toString(),
            tableStartX + columnWidth + cellPadding,
            tableStartY + rowHeight / 2 + 5,
            paint
        )

        tableStartY += rowHeight
    }

    pdfDocument.finishPage(myPage)

// ---- Page 2: Villa Image with Title and Watermark ----

    for (bitmap in images) {

        val pageInfo2 = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 2).create()
        val page2 = pdfDocument.startPage(pageInfo2)
        val canvas2 = page2.canvas



        if (scaledBitmap != null) {
            canvas2.drawBitmap(scaledBitmap, 20F, 10F, paint)
        }

        title.apply {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            textSize = 16F
            color = Color.Black.toArgb()
            textAlign = Paint.Align.CENTER
        }

        canvas2.drawText(context.getString(R.string.app_name), 160F, 75F, title)



        val scaledBitmap1 = bitmap.let {
            Bitmap.createScaledBitmap(it, 600, 400, false)
        }

        scaledBitmap1.let {
            canvas2.drawBitmap(it, 96f, 140f, paint)
        }

        // Draw watermark
        val watermarkPaint = Paint().apply {
            color = context.getColor(R.color.black)
            alpha = 90
            textSize = 36f
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.CENTER
        }

        canvas2.drawText(context.getString(R.string.app_name), pageWidth / 2f, 400f, watermarkPaint)

        pdfDocument.finishPage(page2)

    }


    val file = File(context.getExternalFilesDir(null), "property_details.pdf")
    return try {
        pdfDocument.writeTo(FileOutputStream(file))
       // Toast.makeText(context, "PDF saved to ${file.absolutePath}", Toast.LENGTH_SHORT).show()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        pdfDocument.close()
        Toast.makeText(context, "Failed to generate PDF", Toast.LENGTH_SHORT).show()
        null
    }

}



fun generatePdfFromImageArray(
    context: Context,
    imageUrls: List<String>,
    propertyObject: PropertyObject?
) {
    CoroutineScope(Dispatchers.IO).launch {
        val bitmaps = imageUrls.mapNotNull { url ->
            try {
                withContext(Dispatchers.IO) {
                    Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .submit()
                        .get()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        withContext(Dispatchers.Main) {
            if (bitmaps.isNotEmpty()) {

                generatePDF(context, bitmaps, property = propertyObject)?.let {
                    sharePdfFile(context, it)
                }
            } else {
                Toast.makeText(context, "Failed to load images", Toast.LENGTH_SHORT).show()
            }
        }
    }
}