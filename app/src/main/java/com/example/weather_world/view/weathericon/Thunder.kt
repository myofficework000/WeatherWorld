package com.example.weather_world.view.weathericon

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather_world.R

@Composable
fun AnimatableThunder(modifier: Modifier = Modifier, durationMillis: Int = 600) {

    val transition = rememberInfiniteTransition()

    val animateAlpha by transition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis,
                easing = CubicBezierEasing(0f, 0.2f, 0.7f, 1f),
                delayMillis = durationMillis * 3
            ),
            RepeatMode.Reverse
        )
    )

    val animateY by transition.animateFloat(
        initialValue = -5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis / 10, easing = LinearEasing,
                delayMillis = durationMillis / 5
            ),
            RepeatMode.Reverse
        )
    )

    Thunder(
        modifier = modifier
            .alpha(alpha = animateAlpha % 2f)
            .offset(0.dp, animateY.dp)
    )
}

@Composable
fun Thunder(modifier: Modifier = Modifier) {

    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.ic_thunder),
        contentDescription = "",
        modifier = modifier.clearAndSetSemantics { }
    )
}

@Preview
@Composable
fun PreviewAnimatableThunder() {
    AnimatableThunder()
}
