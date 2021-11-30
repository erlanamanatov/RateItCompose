package com.erkprog.rateit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

@Composable
fun BgColor(progress: Float, modifier: Modifier) {
    val hideousColor = Color(0xfff4ddff)
    val okColor = Color(0xfffff5ee)
    val goodColor = Color(0xffdbf4ff)

    var bgColor by remember { mutableStateOf(okColor) }

    bgColor = if (progress <= 0.5f) {
        lerp(
            start = hideousColor,
            stop = okColor,
            progress * 2f
        )
    } else {
        lerp(
            start = okColor,
            stop = goodColor,
            progress * 2f - 1f
        )
    }
    Box(modifier = modifier.background(color = bgColor))
}
