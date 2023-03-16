package com.example.weather_world.view

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ComposeInfo(
    val sun: IconInfo,
    val cloud: IconInfo,
    val lightCloud: IconInfo,
    val rains: IconInfo,
    val lightRain: IconInfo,
    val snow: IconInfo,
    val thunder: IconInfo

) {
    operator fun times(float: Float): ComposeInfo =
        copy(
            sun = sun * float,
            cloud = cloud * float,
            lightCloud = lightCloud * float,
            rains = rains * float,
            lightRain = lightRain * float,
            snow = snow * float,
            thunder = thunder * float
        )

    operator fun minus(composeInfo: ComposeInfo): ComposeInfo =
        copy(
            sun = sun - composeInfo.sun,
            cloud = cloud - composeInfo.cloud,
            lightCloud = lightCloud - composeInfo.lightCloud,
            rains = rains - composeInfo.rains,
            lightRain = lightRain - composeInfo.lightRain,
            snow = snow - composeInfo.snow,
            thunder = thunder - composeInfo.thunder
        )

    operator fun plus(composeInfo: ComposeInfo): ComposeInfo =
        copy(
            sun = sun + composeInfo.sun,
            cloud = cloud + composeInfo.cloud,
            lightCloud = lightCloud + composeInfo.lightCloud,
            rains = rains + composeInfo.rains,
            lightRain = lightRain + composeInfo.lightRain,
            snow = snow + composeInfo.snow,
            thunder = thunder + composeInfo.thunder
        )
}

data class IconInfo(
    val sizeRatio: Float = 1f,
    val offset: Offset = Offset(0f, 0f),
    val alpha: Float = 1f,
) {
    val size: Dp = 50.dp // max size

    operator fun times(float: Float): IconInfo =
        copy(
            sizeRatio = sizeRatio * float,
            offset = Offset(
                offset.x * float,
                offset.y * float
            ),
            alpha = alpha * float,
        )

    operator fun minus(iconInfo: IconInfo): IconInfo =
        copy(
            sizeRatio = sizeRatio - iconInfo.sizeRatio,
            offset = Offset(
                offset.x - iconInfo.offset.x,
                offset.y - iconInfo.offset.y
            ),
            alpha = alpha - iconInfo.alpha,
        )

    operator fun plus(iconInfo: IconInfo): IconInfo =
        copy(
            sizeRatio = sizeRatio + iconInfo.sizeRatio,
            offset = Offset(
                offset.x + iconInfo.offset.x,
                offset.y + iconInfo.offset.y
            ),
            alpha = alpha + iconInfo.alpha,
        )
}
