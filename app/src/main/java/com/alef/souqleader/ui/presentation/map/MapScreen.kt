package com.alef.souqleader.ui.presentation.map


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.alef.souqleader.R
import com.alef.souqleader.data.remote.dto.Lead
import com.alef.souqleader.data.remote.dto.Project
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.ui.MainViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(
    navController: NavController, projects: ProjectResponse?, propertyResponse: PropertyResponse?,
    mainViewModel: MainViewModel
) {
    MapIcons(projects, propertyResponse)
}



@Composable
fun MapIcons(projects: ProjectResponse?, propertyResponse: PropertyResponse?) {


    if (projects != null) {
        val latLng = projects.data?.get(0)?.map?.substringBefore(",")?.toDouble()?.let {
            projects.data?.get(0)?.map?.substringAfter(",")?.toDouble()?.let { it1 ->
                LatLng(
                    it,
                    it1
                )
            }
        }
        val cameraPositionState = rememberCameraPositionState {
            if (latLng?.let { CameraPosition.fromLatLngZoom(it, 12f) } != null)
                position = latLng.let { CameraPosition.fromLatLngZoom(it, 12f) }!!
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            projects.data?.forEach { project ->
                if (!project.map.isNullOrEmpty()) {
                    project.map.substringAfter(",").toDouble().let { it1 ->
                        LatLng(project.map.substringBefore(",").toDouble(), it1)
                    }.let { it2 -> MarkerState(it2) }.let { it3 ->
                        MarkerInfoWindow(
                            state = it3,
                            icon = bitMapFromVector(LocalContext.current, R.drawable.pin)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .border(
                                        BorderStroke(1.dp, Color.Black),
                                        RoundedCornerShape(24)
                                    )
                                    .clip(RoundedCornerShape(24))
                                    .background(Color.LightGray)
                                    .padding(20.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Info,
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp)
                                )
                                project.title?.let { it1 ->
                                    Text(
                                        it1,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                project.region_name?.let { it1 ->
                                    Text(
                                        it1,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                project.start_price?.let { it1 ->
                                    Text(
                                        it1,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    } else {
        val latLng = propertyResponse?.data?.get(0)?.map?.substringBefore(",")?.toDouble()?.let {
            propertyResponse.data?.get(0)?.map?.substringAfter(",")?.toDouble()?.let { it1 ->
                LatLng(
                    it,
                    it1
                )
            }
        }
        if (latLng != null) {
            val cameraPositionState = rememberCameraPositionState {
                position = latLng.let { CameraPosition.fromLatLngZoom(it, 12f) }
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                propertyResponse.data?.forEach { property ->
                    if (!property.map.isNullOrEmpty()) {
                        property.map.substringAfter(",").toDouble().let { it1 ->
                            LatLng(property.map.substringBefore(",").toDouble(), it1)
                        }.let { it2 -> MarkerState(it2) }.let { it3 ->
                            MarkerInfoWindow(
                                state = it3,
                                icon = bitMapFromVector(LocalContext.current, R.drawable.pin)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .border(
                                            BorderStroke(1.dp, Color.Black),
                                            RoundedCornerShape(24)
                                        )
                                        .clip(RoundedCornerShape(24))
                                        .background(Color.LightGray)
                                        .padding(20.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.Info,
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    property.getTitle()
                                        ?.let { it1 -> Text(it1, fontWeight = FontWeight.Bold) }
                                    property.region_name?.let { it1 ->
                                        Text(
                                            it1,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    property.price?.let { it1 ->
                                        Text(
                                            it1,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } else {
            GoogleMap(
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}

private fun bitMapFromVector(context: Context, vectorResID: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResID)
    vectorDrawable?.setBounds(
        0,
        0,
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight
    )
    val bitmap = vectorDrawable?.intrinsicWidth?.let {
        vectorDrawable.intrinsicHeight.let { it1 ->
            Bitmap.createBitmap(
                it,
                it1,
                Bitmap.Config.ARGB_8888
            )
        }
    }
    val canvas = bitmap?.let { Canvas(it) }
    canvas?.let { vectorDrawable.draw(it) }
    return bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }
}