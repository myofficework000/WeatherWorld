package com.example.weather_world.view

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun AirPollution(location: Location) {
    Column{
        Box(Modifier.fillMaxWidth()) {
            Text("Air Pollution", Modifier.align(Alignment.CenterStart))
            Text("DAQI(GB)", Modifier.align(Alignment.CenterEnd))
        }
        Text("1 - Low")
        LinearProgressIndicator(
            0f,
            Modifier.fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Text("Air quality for xxx")
    }
}

@Preview(showBackground = true)
@Composable
private fun AirPollutionPreview() {
    AirPollution(location = Location(""))
}