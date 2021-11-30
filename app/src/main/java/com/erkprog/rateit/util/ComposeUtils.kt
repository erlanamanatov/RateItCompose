package com.erkprog.rateit.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

fun Offset.normalize(): Offset {
    val m = getDistance()
    return if (m != 0.0f && m != 1.0f) {
        div(m)
    } else
        this
}

fun Path.cubicTo(cp1: Offset, cp2: Offset, destination: Offset) {
    cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, destination.x, destination.y)
}

fun Path.moveTo(destination: Offset) {
    moveTo(destination.x, destination.y)
}
