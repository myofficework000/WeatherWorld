package com.example.weather_world.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_world.model.remote.data.LocalTemUnit
import com.example.weather_world.model.remote.data.weather.Weather
import com.example.weather_world.viewModel.WeatherViewModel
import com.example.weatherappall.model.remote.data.weather.WeatherResponse

@Composable
fun Weather(viewModel: WeatherViewModel = hiltViewModel()) {

    val weatherInfo by viewModel.weatherInfo.observeAsState()

    Card(
        modifier = Modifier
            .background(color = Color.Cyan)
            .fillMaxWidth()
            .padding(15.dp)
            .clickable{ },
    ){
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            weatherInfo?.let {
                Text(
                    text = it.name,
                    color = Color.White,
                    fontSize = 60.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center

                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            weatherInfo?.let {
                Text(
                    text = it.main.temp.toInt().toString() + "°C",
                    fontSize = 70.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            weatherInfo?.let {
                Text(
                    text = it.clouds.toString().trim(),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            weatherInfo?.let {
                Text(
                    text = "H:${it.main.temp_max.toInt()}° L:${it.main.temp_min.toInt()}°",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

/**
 * Pager showing weather info
 */
@Composable
fun WeatherInfoPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    weatherResponse: WeatherResponse?,
    dailyWeathers: List<Weather>
) {
    if (weatherResponse == null) {
        Text("Loading...")
        return
    }

    Pager(
        state = pagerState,
        modifier = modifier
    ) {

        val day = dailyWeathers[page]

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                weatherResponse.name,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                day.main,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                day.description,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(100.dp)
            ) {

                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    /* Text(
                         "${day.temperatureRange.first.displayName(LocalTemUnit.current)}↑",
                         style = TextStyle(
                             fontSize = 19.sp,
                             fontFamily = FontFamily.Monospace,
                             fontWeight = FontWeight.Normal,
                         ),
                         modifier = Modifier.align(Alignment.End)
                     )*/
                    Spacer(modifier = Modifier.height(3.dp))
                    /*Text(
                        "${day.temperatureRange.second.displayName(LocalTemUnit.current)}↓",
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Normal,
                        ),
                        modifier = Modifier.align(Alignment.End)
                    )*/
                }

                val animateTween by rememberInfiniteTransition().animateFloat(
                    initialValue = -1f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        tween(2000, easing = LinearEasing),
                        RepeatMode.Reverse
                    )
                )

                Text(
                    "${weatherResponse.main.temp} ${LocalTemUnit.current.text}",
                    style = TextStyle(
                        fontSize = 70.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    ),
                    letterSpacing = 0.sp,
                    modifier = Modifier.offset(0.dp,(-5).dp * animateTween)
                )
                /*

                Text(
                    day.temperature().displayName(LocalTemUnit.current),
                    style = TextStyle(
                        fontSize = 70.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    ),
                    letterSpacing = 0.sp,
                    modifier = Modifier.offset(0.dp, (-5).dp * animateTween)
                )*/
                /*Text(
                    LocalTemUnit.current.text,
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )*/
            }
        }
    }
}