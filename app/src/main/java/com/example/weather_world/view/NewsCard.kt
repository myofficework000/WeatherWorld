package com.example.weather_world.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_world.viewModel.NewsViewModel

@Composable
fun NewsCard(viewModel: NewsViewModel = hiltViewModel()) {
    val response = viewModel.responseFromApi.observeAsState()

    viewModel.getNewsAccordingToRegion("India")

    Column(Modifier.fillMaxWidth()) {
        response.value?.let {
            //your UI

        }
    }
}