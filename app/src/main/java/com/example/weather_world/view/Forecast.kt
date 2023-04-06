package com.example.weather_world.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_world.model.remote.data.LocalTemUnit
import com.example.weather_world.model.repositories.weather.Constants.IMG_URL
import com.example.weather_world.model.repositories.weather.Constants.TEMP_CONVERT2
import com.example.weather_world.model.repositories.weather.Constants.TEMP_CONVERTER1
import com.example.weather_world.viewModel.WeatherViewModel
import com.example.weatherappall.model.remote.data.forecast.WeatherDetail
import com.example.weatherappall.model.remote.data.weather.WeatherResponse
import com.skydoves.landscapist.glide.GlideImage
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun Forecast(viewModel: WeatherViewModel = hiltViewModel()) {
    val response = viewModel.forecastResponse.observeAsState()
    viewModel.getForeCastInfo("Houston")
    response.value?.let {
        SetForeCastUI(weatherDetails = it.list)
    }
}
@Composable
fun SetForeCastUI(weatherDetails: List<WeatherDetail>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(weatherDetails) { weatherDetail ->
            ForecastCard(weatherDetail)
        }
    }
}

@Composable
fun ForecastCard(weatherDetail: WeatherDetail) {
    fun tempConverter(temp: Double): Int{
        return(temp * TEMP_CONVERTER1 + TEMP_CONVERT2).roundToInt()
    }
    val mainTemp = tempConverter(weatherDetail.main.temp)
    val minTemp = tempConverter(weatherDetail.main.temp_min)
    val maxTemp = tempConverter(weatherDetail.main.temp_max)

    val date = Date(weatherDetail.dt * 1000L)
    val timeFormat = SimpleDateFormat("h a")
    val time = timeFormat.format(date)

    val dayFormat = SimpleDateFormat("E")
    val day = dayFormat.format(date)


    Card(
        modifier = Modifier
            .fillMaxHeight()
            .width(100.dp),
        border = null,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                day,
                fontSize = 15.sp,
            )
            Text(
                time,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            GlideImage(
                imageModel = "${IMG_URL}/${weatherDetail.weather[0].icon}.png",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp))
            Text(
                text = "$mainTemp °F",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$minTemp/$maxTemp °F",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Render hourly temperature with curve-line chart
 * show with animation
 */
/**
 * Render hourly temperature with curve-line chart
 * show with animation
 */
@Composable
fun LineChart(
    modifier: Modifier,
    dailyWeather: WeatherResponse
) {

    val (cur, setCur) = remember { mutableStateOf<WeatherResponse?>(null) }

    var trigger by remember { mutableStateOf(0f) }

    DisposableEffect(dailyWeather) {
        trigger = 1f
        onDispose { }
    }

    val animateFloat by animateFloatAsState(
        targetValue = trigger,
        animationSpec = tween(1500)
    ) {
        setCur(dailyWeather)
        trigger = 0f
    }

    val tempUnit = LocalTemUnit.current
    Canvas(modifier) {

        val increment = size.width / dailyWeather.weather.size
        val max = dailyWeather.main.temp_max
        val min = dailyWeather.main.temp_min
        val dy = (max - min).toFloat()

        drawIntoCanvas { canvas ->

            if (cur != dailyWeather) {
                // change visible range according to animation
                canvas.clipRect(Rect(0f, 0f, size.width * animateFloat, size.height))
            }

            val path = Path()

            val points = dailyWeather.weather.mapIndexed { index, hourlyWeather ->
                Offset(
                    increment * index + increment / 2,
                    (1 - (hourlyWeather.main.toFloatOrNull()
                        ?.minus(min.toFloat()))!! / dy) * (size.height * 0.3f) +
                            size.height * 0.2f // reserve space for drawText
                )
            }

            path.moveTo(0f, points.first().y)
            path.lineTo(points.first().x, points.first().y)

            (0..points.size - 2).forEach { index ->

                val startP = points[index]
                val endP = points[index + 1]

                val p2: Offset
                val p3: Offset
                val cx = (startP.x + endP.x) / 2
                val dy = abs((endP.y - startP.y) / 4)
                if (endP.y < startP.y) {
                    p2 = Offset(cx, startP.y - dy)
                    p3 = Offset(cx, endP.y + dy)
                } else {
                    p2 = Offset(cx, startP.y + dy)
                    p3 = Offset(cx, endP.y - dy)
                }
                path.cubicTo(p2.x, p2.y, p3.x, p3.y, endP.x, endP.y)
            }

            path.lineTo(points.last().x + increment / 2, points.last().y)
            path.lineTo(points.last().x + increment / 2, size.height)
            path.lineTo(0f, size.height)
            path.lineTo(0f, points.first().y)

            // draw path
            /*canvas.nativeCanvas.drawPath(
                path,
                Paint().apply {
                    val linearGradient = LinearGradient(
                        0f, 0f,
                        0f, 200f,
                        Color.Black.copy(alpha = 0.1f).toArgb(),
                        Color.Transparent.toArgb(),
                        Shader.TileMode.CLAMP
                    )

                    shader = linearGradient
                    isAntiAlias = true
                }
            )
*/
            // draw points
            canvas.drawPoints(
                PointMode.Points, points,
                Paint().apply {
                    strokeWidth = 8f
                    strokeCap = StrokeCap.Round
                    color = Color.Black.copy(0.6f)
                }
            )

            // draw text
            val size = 10.sp.toPx()
            val textPaint = android.graphics.Paint()
            dailyWeather.weather.asSequence().zip(points.asSequence())
                .forEachIndexed { index, pair ->
                    val (weather, points) = pair
                    canvas.nativeCanvas.drawText(
                        weather.main,
                        points.x - size / 2,
                        points.y - size / 1.5f,
                        textPaint
                    )
                }
        }
    }
}
