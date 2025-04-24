package com.alef.souqleader.ui.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.R
import com.alef.souqleader.ui.MainViewModel
import com.alef.souqleader.ui.presentation.jobApplication.JobApplicationViewModel
import kotlinx.coroutines.launch


@Composable
fun AppBarWithArrow(
    title: String?, mainViewModel: MainViewModel,
    pressOnBack: () -> Unit
) {

    TopAppBar(
        elevation = 6.dp,
        backgroundColor = colorResource(id = R.color.blue2),
        modifier = Modifier.height(58.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(R.drawable.arrow_left),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            pressOnBack()
                        }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    text = title ?: "",
                    style = MaterialTheme.typography.h6,
                    color = colorResource(id = R.color.white)
                )
            }
            if (mainViewModel.showFilterIcon)
                Image(
                    painter = painterResource(R.drawable.filter_icon),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable {
                            mainViewModel.showFilter = !mainViewModel.showFilter
                        }
                )

            if (mainViewModel.showShareIcon)
                Image(
                    painter = painterResource(R.drawable.baseline_ios_share_24),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 16.dp)
                        .clickable {
                            mainViewModel.viewModelScope.launch {
                                mainViewModel.onShareClick.emit(true)
                            }
                        }
                )
        }
    }
}