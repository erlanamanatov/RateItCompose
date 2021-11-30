package com.erkprog.rateit.components

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.zIndex

@Composable
fun CustomSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val thumbRadius = 10.dp // internal value
    val outlineBoxSize = 70.dp
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val width = constraints.maxWidth
        val widthDp = with(LocalDensity.current) { width.toDp() }
        val sliderWidth = widthDp - (outlineBoxSize / 2 - thumbRadius) * 2
        val trackWidth = sliderWidth - thumbRadius * 2
        val boxOffset = lerp(
            start = 0.dp,
            stop = trackWidth,
            fraction = value
        )
        Box(
            Modifier
                .zIndex(1f)
                .offset(x = boxOffset)
                .align(Alignment.CenterStart)
                .size(outlineBoxSize)
                .border(
                    width = outlineBoxSize/10,
                    color = Color(0xfffcfcff).copy(alpha = 0.7f),
                    shape = RoundedCornerShape(25)
                )
        )

        Slider(
            modifier = Modifier
                .width(sliderWidth)
                .zIndex(0f),
            value = value,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Black.copy(alpha = 0.1f),
                inactiveTrackColor = Color.Black.copy(alpha = 0.1f),
            )
        )
    }
}