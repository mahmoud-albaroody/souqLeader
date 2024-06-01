package com.alef.souqleader.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alef.souqleader.domain.model.Gym

@Composable
fun CardDetails(modifier: Modifier, gym: Gym, verticalArrangement:Arrangement.Vertical) {
    Column(
        modifier,
        verticalArrangement=verticalArrangement
    ) {
        Text(
            text = gym.gymTitle, Modifier.padding(top = 6.dp, end = 4.dp),
            style = TextStyle(
                color = Color.Green,
                fontWeight = FontWeight.SemiBold
            )
        )

        Text(
            text = gym.gymDescription,
            Modifier
                .padding(bottom = 6.dp, top = 2.dp, end = 4.dp)

        )
    }
}
