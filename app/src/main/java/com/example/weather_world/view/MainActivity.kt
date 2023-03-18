package com.example.weather_world.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_world.model.remote.data.TemperatureUnit
import com.example.weather_world.view.ui.theme.Pink80
import com.example.weather_world.view.ui.theme.WeatherWorldTheme
import com.example.weather_world.viewModel.WeatherViewModel
import com.example.weatherappall.model.remote.data.forecast.Weather
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity(), LocationListener {
    private lateinit var locationManager: LocationManager
    var lat: Double? = null
    var lon: Double? = null
    private var latestLocation by mutableStateOf(Location(""))

    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar(window)
        getCurrentLocation()
        setContent {
            val weatherViewModel = hiltViewModel<WeatherViewModel>()
            val currentCity = remember(weatherViewModel.currentCoordData) {
                weatherViewModel.currentCoordData?.firstOrNull()?.name ?: ""
            }
            val cityName = remember{ mutableStateOf("") }

            LaunchedEffect(latestLocation) {
                weatherViewModel.getAirPollutionInfo(latestLocation)
                if (weatherViewModel.currentCoordData == null) {
                    weatherViewModel.getWeatherInfo(currentCity)
                } else {
                    weatherViewModel.getWeatherInfo("London")
                }
            }

            LaunchedEffect(weatherViewModel.currentCoordData) {
                weatherViewModel.currentCoordData?.firstOrNull()?.apply {
                    weatherViewModel.getAirPollutionInfo(getLocation())
                    if (weatherViewModel.currentCoordData == null) {
                        weatherViewModel.getWeatherInfo(currentCity)
                    } else {
                        weatherViewModel.getWeatherInfo("London")
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Pink80)
                        .align(Alignment.TopCenter)
                ) {

                }
                Column {
                    SearchUI(city = cityName, weatherViewModel::getCoordsByCity)
                    Text(text = "Weather Info")
                    Weather()
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Pink80)
                    ) {
                        Forecast()
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Pink80)
                    ) {
                        AirPollution(
                            weatherViewModel.currentAirPollutionData,
                            currentCity
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    NewsCard()
                }
            }
        }
    }

    private fun getCurrentLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                500f,
                this
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        lon = location.longitude
        latestLocation = location
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherWorldTheme {
        // AirPollution()
    }
}

@Composable
fun WeatherIcon(modifier: Modifier, weatherIcon: Weather) {

    val (cur, setCur) = remember { mutableStateOf(weatherIcon) }
    var trigger by remember { mutableStateOf(0f) }

    DisposableEffect(weatherIcon.icon) {
        trigger = 1f
        onDispose { }
    }

    val animateFloat by animateFloatAsState(
        targetValue = trigger,
        animationSpec = tween(1000)
    ) {
        setCur(weatherIcon)
        trigger = 0f
    }

    /* val composeInfo = remember(animateFloat) {
        cur.composedIcon + (weatherIcon.composedIcon - cur.composedIcon) * animateFloat
         cur.icon animateFloat

     }

     ComposedIcon(
         modifier,
         composeInfo
     )*/
}

@Composable
private fun backgroundColorState(target: Color) =
    animateColorAsState(
        targetValue = target,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

/**
 * transparent ActionBar with more-opt menu
 */
@Composable
fun ActionBar(selected: TemperatureUnit, onSelect: (TemperatureUnit) -> Unit) {
    Box(
        Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(Color.Black.copy(alpha = 0.4f), Color.Transparent)
                )
            )
            .clearAndSetSemantics { }
    ) {
        var showDialogState by remember { mutableStateOf(false) }

        if (showDialogState) {
            ShowDialog(selected) {
                onSelect(it)
                showDialogState = false
            }
        }

        Image(
            Icons.Default.MoreVert,
            "",
            Modifier
                .size(50.dp)
                .offset((-2).dp, 30.dp)
                .clickable { showDialogState = true }
                .padding(10.dp)
                .align(Alignment.TopEnd),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

private fun transparentStatusBar(window: Window) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    val vis = window.decorView.systemUiVisibility
    window.decorView.systemUiVisibility = option or vis
    window.statusBarColor = android.graphics.Color.TRANSPARENT
}