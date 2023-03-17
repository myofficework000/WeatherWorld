package com.example.weather_world.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_world.R
import com.example.weather_world.model.remote.data.news.News
import com.example.weather_world.viewModel.NewsViewModel
import com.skydoves.landscapist.glide.GlideImage
@ExperimentalMaterial3Api
@Composable
fun NewsCard(viewModel: NewsViewModel = hiltViewModel()) {
    val response = viewModel.responseFromApi.observeAsState()
    val regions = arrayOf("AS", "PSE", "CA", "CN", "FI")
    viewModel.getNewsAccordingToRegion("CA")

    Column(
       modifier = Modifier
           .fillMaxWidth()
           .background(color = Color.Cyan)
    ) {
        response.value?.let {
            LazyRow {
                items(it.news) {newsItem ->
                    DrawNewsItem(newsItem)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun DrawNewsItem(item: News) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
            .wrapContentHeight()
            .padding(10.dp)

    ) {
        GlideImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            imageModel = item.image
        )
        Text(
            text = item.title,
            fontSize = 18.sp,
            maxLines = 1
        )
        Row() {
            Text(
                text = item.published,
                fontSize = 12.sp,
                maxLines = 1
            )
            IconButton(
                onClick = { expanded = !expanded }
            ) {
                Icon(
                    imageVector =
                    if (expanded) ImageVector.vectorResource(id = R.drawable.baseline_expand_less_24)
                    else ImageVector.vectorResource(id = R.drawable.baseline_expand_more_24),
                    tint = Color.Magenta,
                    contentDescription = "Spring Effect"
                )
            }
        }

        if (expanded) {
            Text(
                text = item.description,
                fontSize = 14.sp,
            )
        }
    }
}