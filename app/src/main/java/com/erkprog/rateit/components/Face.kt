package com.erkprog.rateit.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity

@Composable
fun AnimatedFace(
    modifier: Modifier = Modifier,
    sliderPositioned: Boolean,
    progress: Float,
    sliderStartWindowVector: Offset,
    sliderEndWindowVector: Offset,
    focusedOnSlider: Boolean = false
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val areaWidth = constraints.maxWidth.toFloat()
        val areaHeight = constraints.maxHeight.toFloat()
        val areaWidthDp = with(LocalDensity.current) { areaWidth.toDp() }
        val areaHeightDp = with(LocalDensity.current) { areaHeight.toDp() }
        val eyeWidth = areaWidthDp * 0.33f
        val mouthWidth = areaWidthDp * 0.32f
        val mouthHeight = areaHeightDp * 0.25f


        if (sliderPositioned) {
            Eye(
                progress = progress,
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(eyeWidth)
                    .aspectRatio(1f)
                    .offset(x = eyeWidth * (-0.7f), y = areaHeightDp * (-0.23f)),
                sliderStartWindowVector = sliderStartWindowVector,
                sliderEndWindowVector = sliderEndWindowVector,
                draggedOrPressed = focusedOnSlider
            )
            Eye(
                progress = progress,
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(eyeWidth)
                    .aspectRatio(1f)
                    .offset(x = eyeWidth * (0.7f), y = areaHeightDp * (-0.23f)),
                flipHorizontally = true,
                sliderStartWindowVector = sliderStartWindowVector,
                sliderEndWindowVector = sliderEndWindowVector,
                draggedOrPressed = focusedOnSlider
            )
        }
        Mouth(
            progress = progress,
            modifier = Modifier
                .align(Alignment.Center)
                .width(mouthWidth)
                .height(mouthHeight)
                .offset(y = mouthHeight * 0.75f),
        )
    }
}