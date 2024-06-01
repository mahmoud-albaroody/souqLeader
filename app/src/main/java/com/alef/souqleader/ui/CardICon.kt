package com.alef.souqleader.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alef.souqleader.domain.model.Gym

@Composable
fun CardICon(
    modifier: Modifier,
    gym: Gym,
    icon: Int? = null,
    onclick: () -> Unit = {}
) {
    gym.gymIcon?.let { painterResource(it) }?.let {
        Image(
            painter = it,
            contentDescription = gym.gymDescription,
            alignment = Alignment.Center,
            modifier = modifier
                .padding(4.dp)
                .clickable {
                    onclick()
                }
        )
    }
}
