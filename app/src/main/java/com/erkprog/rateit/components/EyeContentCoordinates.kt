package com.erkprog.rateit.components

import androidx.compose.ui.geometry.Offset
import com.erkprog.rateit.model.VertexCoordinates

class EyeContentCoordinates(width: Float, height: Float) {

    val vertex1 = VertexCoordinates(
        hideousPosition = Offset(
            0.12f * width, 0.16f * height
        ),
        okPosition = Offset(
            0.09f * width, 0.4f * height
        ),
        goodPosition = Offset(
            0.22f * width, 0.24f * height
        )
    )
    val vertex2 = VertexCoordinates(
        hideousPosition = Offset(
            0.86f * width, 0.19f * height
        ),
        okPosition = Offset(
            0.88f * width, 0.23f * height
        ),
        goodPosition = Offset(
            0.81f * width, 0.23f * height
        )
    )
    val vertex3 = VertexCoordinates(
        hideousPosition = Offset(
            0.39f * width, 0.88f * height
        ),
        okPosition = Offset(
            0.35f * width, 0.7f * height
        ),
        goodPosition = Offset(
            0.48f * width, 0.77f * height
        )
    )

    val cp12a = VertexCoordinates(
        hideousPosition = Offset(
            0.21f * width, 0.11f * height
        ),
        okPosition = Offset(
            -0.04f * width, -0.07f * height
        ),
        goodPosition = Offset(
            0.05f * width, -0.29f * height
        )
    )
    val cp12b = VertexCoordinates(
        hideousPosition = Offset(
            -0.21f * width, 0.08f * height
        ),
        okPosition = Offset(
            -0.05f * width, -0.05f * height
        ),
        goodPosition = Offset(
            -0.03f * width, -0.22f * height
        )
    )
    val cp23a = VertexCoordinates(
        hideousPosition = Offset(
            -0.08f * width, 0.32f * height
        ),
        okPosition = Offset(
            0.05f * width, 0.08f * height
        ),
        goodPosition = Offset(
            0.05f * width, 0.25f * height
        )
    )
    val cp23b = VertexCoordinates(
        hideousPosition = Offset(
            0.25f * width, 0.001f * height
        ),
        okPosition = Offset(
            0.1f * width, 0f * height
        ),
        goodPosition = Offset(
            0.29f * width, 0.01f * height
        )
    )
    val cp31a = VertexCoordinates(
        hideousPosition = Offset(
            -0.21f * width, -0.04f * height
        ),
        okPosition = Offset(
            -0.08f * width, -0.02f * height
        ),
        goodPosition = Offset(
            -0.19f * width, -0.001f * height
        )
    )
    val cp31b = VertexCoordinates(
        hideousPosition = Offset(
            -0.01f * width, 0.24f * height
        ),
        okPosition = Offset(
            -0.04f * width, 0.06f * height
        ),
        goodPosition = Offset(
            -0.04f * width, 0.27f * height
        )
    )
}