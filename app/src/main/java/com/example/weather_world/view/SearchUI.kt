package com.example.weather_world.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weather_world.R
import com.example.weather_world.view.ui.theme.Pink80
import kotlin.reflect.KFunction1

@Composable
fun SearchUI(
    city: MutableState<String>,
    searchCityFunc: KFunction1<String, Unit>
) {
    OutlinedTextField(
        value = city.value,
        onValueChange = { city.value = it },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        label = { Text(text = stringResource(id = R.string.search_city)) },
        placeholder = { Text(text = stringResource(id = R.string.city)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.LocationCity, contentDescription = stringResource(
                    id = R.string.loaction_city
                )
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                searchCityFunc(city.value)
            }) {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = stringResource(
                        id = R.string.loaction_city
                    )
                )
            }
        }
    )
}