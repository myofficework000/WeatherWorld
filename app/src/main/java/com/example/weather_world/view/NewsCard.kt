package com.example.weather_world.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_world.viewModel.NewsViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun NewsCard(viewModel: NewsViewModel = hiltViewModel()) {
    val response = viewModel.responseFromApi.observeAsState()

    viewModel.getNewsAccordingToRegion("India")

    Column(
       modifier = Modifier.fillMaxWidth()
           .height(290.dp)
           .background(color = Color.Cyan)
    ) {
        response.value?.let {
            LazyRow {
                items(it.news) {newsItem ->
                    Card(
                        modifier = Modifier
                            .width(300.dp)
                            .fillMaxHeight()
                            .wrapContentHeight()
                            .padding(10.dp)

                    ) {
                        GlideImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            imageModel = newsItem.image
                        )
                        Text(
                            text = newsItem.title,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }
    }
}