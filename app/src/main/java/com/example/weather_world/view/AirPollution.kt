package com.example.weather_world.view

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather_world.model.repositories.weather.Constants
import com.example.weather_world.view.ui.theme.cardDayColor
import com.example.weatherappall.model.remote.data.pollutionforecast.Components
import com.example.weatherappall.model.remote.data.pollutionforecast.Main
import com.example.weatherappall.model.remote.data.pollutionforecast.Pollution

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirPollution(
    data: Pollution?,
    cityName: String
) {
    var aqiFraction by remember{ mutableStateOf(5f) }
    val aqiAnimated by animateFloatAsState(
        aqiFraction,
        tween(1000, easing = FastOutSlowInEasing))

    LaunchedEffect(data) {
        aqiFraction = aqiToFraction(data?.main?.aqi ?: 1)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()

    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = cardDayColor)
        ){
            Box(Modifier.fillMaxWidth().padding(10.dp)) {
                Text("Air Pollution", Modifier.align(Alignment.CenterStart),fontWeight = FontWeight.Bold,
                    color = Color.White)
                Text("DAQI(GB)", Modifier.align(Alignment.CenterEnd),fontWeight = FontWeight.Bold,
                    color = Color.White)
            }
            Text(aqiToText(data?.main?.aqi ?: 0),fontWeight = FontWeight.Bold,
                color = Color.White)
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        Constants.airQualityBarGradient,
                        RoundedCornerShape(20.dp)
                    )
            ) {
                Slider(
                    value = aqiAnimated,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .padding(10.dp),
                    enabled = false,
                    track = {
                        SliderDefaults.Track(
                            it,
                            colors = SliderDefaults.colors(
                                activeTrackColor = Color.Transparent,
                                inactiveTrackColor = Color.Transparent
                            )
                        )
                    }
                )
            }
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Air quality for $cityName",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }

}

private fun aqiToText(aqi: Int) = "$aqi - ${
    when(aqi) {
        1 -> "Good"
        2 -> "Fair"
        3 -> "Moderate"
        4 -> "Poor"
        5 -> "Very Poor"
        else -> "N/A"
    }
}"

private fun aqiToFraction (aqi: Int) = (aqi - 1) / 4.0f

@Preview(showBackground = true)
@Composable
private fun AirPollutionPreview() {
    AirPollution(
        Pollution(
            Components(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0),
            0,
            Main(3)
        ),
        "London"
    )
}