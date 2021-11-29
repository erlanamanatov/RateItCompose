package com.erkprog.rateit.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

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
        val v1Values = VertexValues(
            xValues = ValueLimits(
                0.12f * width, 0.08f * width, 0.08f * width
            ),
            yValues = ValueLimits(
                0.58f * height, 0.24f * height, 0.24f * height
            )
        )

        val v2Values = VertexValues(
            xValues = ValueLimits(
                0.38f * width, 0.39f * width, 0.36f * width
            ),
            yValues = ValueLimits(
                0.38f * height, 0.36f * height, 0.47f * height
            )
        )
        val v3Values = VertexValues(
            xValues = ValueLimits(
                0.86f * width, 0.93f * width, 0.93f * width
            ),
            yValues = ValueLimits(
                0.49f * height, 0.45f * height, 0.44f * height
            )
        )

        val cp1Values = VertexValues(
            xValues = ValueLimits(
                0.06f * width, 0.08f * width, 0.12f * width
            ),
            yValues = ValueLimits(
                -0.16f * height, 0.02f * height, 0.11f * height
            )
        )
        val cp2Values = VertexValues(
            xValues = ValueLimits(
                -0.08f * width, -0.13f * width, -0.14f * width
            ),
            yValues = ValueLimits(
                -0.14f * height, -0.05f * height, -0.11f * height
            )
        )
        val cp3Values = VertexValues(
            xValues = ValueLimits(
                0.14f * width, 0.17f * width, 0.2f * width
            ),
            yValues = ValueLimits(
                -0.23f * height, 0.06f * height, 0.15f * height
            )
        )
        val cp4Values = VertexValues(
            xValues = ValueLimits(
                -0.15f * width, -0.17f * width, -0.19f * width
            ),
            yValues = ValueLimits(
                -0.3f * height, 0.02f, 0.23f * height
            )
        )

        val v1 = v1Values.getOffsetValue(progress)
        val v2 = v2Values.getOffsetValue(progress)
        val v3 = v3Values.getOffsetValue(progress)
        val cp1 = cp1Values.getOffsetValue(progress)
        val cp2 = cp2Values.getOffsetValue(progress)
        val cp3 = cp3Values.getOffsetValue(progress)
        val cp4 = cp4Values.getOffsetValue(progress)

//        drawCircle(
//            color = Color.Red,
//            radius = 5f,
//            center = v1
//        )
//        drawCircle(
//            color = Color.Red,
//            radius = 5f,
//            center = v2
//        )
//        drawCircle(
//            color = Color.Red,
//            radius = 5f,
//            center = v3
//        )

        val path = Path().apply {
            moveTo(v1.x, v1.y)
            cubicTo(
                v1.x + cp1.x,
                v1.y + cp1.y,
                v2.x + cp2.x,
                v2.y + cp2.y,
                v2.x,
                v2.y
            )
            cubicTo(
                v2.x + cp3.x,
                v2.y + cp3.y,
                v3.x + cp4.x,
                v3.y + cp4.y,
                v3.x,
                v3.y
            )
        }
        drawPath(path = path, color = strokeColor, style = Stroke(width = strokeWidth))
    }
}

data class VertexValues(val xValues: ValueLimits, val yValues: ValueLimits) {
    fun getYvalue(progress: Float): Float {
        return if (progress <= 0.5f) {
            ((yValues.average - yValues.sad) * progress + 0.5f * yValues.sad) / 0.5f
        } else {
            ((yValues.good - yValues.average) * (progress - 0.5f) + 0.5f * yValues.average) / 0.5f
        }
    }

    fun getXvalue(progress: Float): Float {
        return if (progress <= 0.5f) {
            ((xValues.average - xValues.sad) * progress + 0.5f * xValues.sad) / 0.5f
        } else {
            ((xValues.good - xValues.average) * (progress - 0.5f) + 0.5f * xValues.average) / 0.5f
        }
    }

    fun getOffsetValue(progress: Float): Offset {
        return Offset(getXvalue(progress), getYvalue(progress))
    }
}

data class ValueLimits(val sad: Float = 0f, val average: Float = 0f, val good: Float = 0f)

