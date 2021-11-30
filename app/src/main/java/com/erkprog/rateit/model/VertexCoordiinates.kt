package com.erkprog.rateit.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp

data class VertexCoordinates(
    private val hideousPosition: Offset,
    private val okPosition: Offset,
    private val goodPosition: Offset
) {
    fun getCurrentPosition(progress: Float): Offset {
        return if (progress <= 0.5f) {
            lerp(hideousPosition, okPosition, progress * 2)
        } else {
            lerp(okPosition, goodPosition, progress * 2 - 1f)
        }
    }
}