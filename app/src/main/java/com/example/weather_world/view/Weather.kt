package com.example.weather_world.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_world.model.remote.data.LocalTemUnit
import com.example.weather_world.model.remote.data.weather.Weather

@Composable
fun Weather(){

}

/**
 * Pager showing weather info
 */
@Composable
fun WeatherInfoPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    dailyWeathers: List<Weather>
) {
    Pager(
        state = pagerState,
        modifier = modifier
    ) {

        val day = dailyWeathers[page]

        Column(modifier = Modifier.fillMaxSize()) {

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
                )/*

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
                Text(
                    LocalTemUnit.current.text,
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}