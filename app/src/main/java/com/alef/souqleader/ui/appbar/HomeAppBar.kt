package com.alef.souqleader.ui.appbar


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.alef.souqleader.R
import com.alef.souqleader.ui.MainViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeAppBar(
    title: String, openDrawer: () -> Unit, mainViewModel: MainViewModel,
    pressOnBack: () -> Unit
) {
    var selected by remember { mutableStateOf(false) }
    TopAppBar(
        backgroundColor = colorResource(id = R.color.blue2),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6, color = colorResource(id = R.color.white)
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                openDrawer()
            }) {
                Icon(
                    Icons.Default.Menu, "Menu",
                    tint = colorResource(id = R.color.white)
                )
            }
        },
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6, color = colorResource(id = R.color.white)
                )
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

                if (mainViewModel.showMenuContact)
                    Image(
                        painter = painterResource(R.drawable.icons8_menu),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp)
                            .clickable {
                                mainViewModel.showSendContact = !mainViewModel.showSendContact
                            }
                    )
                if (mainViewModel.showSendContact) {

                    Image(
                        painter = painterResource(R.drawable.icons8_mail),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                mainViewModel.viewModelScope.launch {
                                    mainViewModel.onSendMailClick.emit(true)
                                }
                            }
                    )
                    Image(
                        painter = painterResource(R.drawable.message_text1),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                mainViewModel.viewModelScope.launch {
                                    mainViewModel.onSmsMailClick.emit(true)
                                }
                            }
                    )
                    Image(
                        painter = painterResource(R.drawable.icons8_whatsapp),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.white)),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                mainViewModel.viewModelScope.launch {
                                    mainViewModel.onWhatsClick.emit(true)
                                }
                            }
                    )
                    Checkbox(
                        modifier = Modifier
                            .align(Alignment.CenterVertically).padding(end = 16.dp),
                        checked = selected,
                        onCheckedChange = {
                            selected = !selected
                            mainViewModel.viewModelScope.launch {
                                mainViewModel.onSelectAllClick.emit(selected)
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.White,
                            uncheckedColor = Color.White,
                            checkmarkColor = colorResource(id = R.color.blue)
                        )
                    )
                }


            }
        }
    )

}
