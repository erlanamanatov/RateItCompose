package com.erkprog.rateit.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.erkprog.rateit.model.VertexCoordinates
import com.erkprog.rateit.util.cubicTo
import com.erkprog.rateit.util.moveTo

@Composable
fun Mouth(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 12f,
    strokeColor: Color = Color.Black
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val vertex1 = VertexCoordinates(
            hideousPosition = Offset(
                0.12f * width, 0.58f * height
            ),
            okPosition = Offset(
                0.08f * width, 0.24f * height
            ),
            goodPosition = Offset(
                0.08f * width, 0.24f * height
            )
        )
        val vertex2 = VertexCoordinates(
            hideousPosition = Offset(
                0.38f * width, 0.38f * height
            ),
            okPosition = Offset(
                0.39f * width, 0.36f * height
            ),
            goodPosition = Offset(
                0.36f * width, 0.47f * height
            )
        )
        val vertex3 = VertexCoordinates(
            hideousPosition = Offset(
                0.86f * width, 0.49f * height
            ),
            okPosition = Offset(
                0.93f * width, 0.45f * height
            ),
            goodPosition = Offset(
                0.93f * width, 0.44f * height
            )
        )

        val cp12a = VertexCoordinates(
            hideousPosition = Offset(
                0.06f * width, -0.16f * height
            ),
            okPosition = Offset(
                0.08f * width, 0.02f * height
            ),
            goodPosition = Offset(
                0.12f * width, 0.11f * height
            )
        )
        val cp12b = VertexCoordinates(
            hideousPosition = Offset(
                -0.08f * width, -0.14f * height
            ),
            okPosition = Offset(
                -0.13f * width, -0.05f * height
            ),
            goodPosition = Offset(
                -0.14f * width, -0.11f * height
            )
        )
        val cp23a = VertexCoordinates(
            hideousPosition = Offset(
                0.14f * width, -0.23f * height
            ),
            okPosition = Offset(
                0.17f * width, 0.06f * height
            ),
            goodPosition = Offset(
                0.2f * width, 0.15f * height
            )
        )
        val cp23b = VertexCoordinates(
            hideousPosition = Offset(
                -0.15f * width, -0.3f * height
            ),
            okPosition = Offset(
                -0.17f * width, 0.02f * height
            ),
            goodPosition = Offset(
                -0.19f * width, 0.23f * height
            )
        )

        val v1 = vertex1.getCurrentPosition(progress)
        val v2 = vertex2.getCurrentPosition(progress)
        val v3 = vertex3.getCurrentPosition(progress)
        val cp12aCur = cp12a.getCurrentPosition(progress)
        val cp12bCur = cp12b.getCurrentPosition(progress)
        val cp23aCur = cp23a.getCurrentPosition(progress)
        val cp23bCur = cp23b.getCurrentPosition(progress)

        val path = Path().apply {
            moveTo(v1)
            cubicTo(v1 + cp12aCur, v2 + cp12bCur, v2)
            cubicTo(v2 + cp23aCur, v3 + cp23bCur, v3)
        }
        drawPath(path = path, color = strokeColor, style = Stroke(width = strokeWidth))
    }
}