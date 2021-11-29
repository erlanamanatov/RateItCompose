package com.erkprog.rateit.components.mouth

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

@Composable
fun Eye(
    progress: Float,
    modifier: Modifier = Modifier,
    flipHorizontally: Boolean = false,
    strokeWidth: Float = 12f,
    strokeColor: Color = Color.Black,
    sliderStartWindowVector: Offset = Offset.Zero,
    sliderEndWindowVector: Offset = Offset.Zero,
    draggedOrPressed: Boolean = false
) {
    var sliderStartEyeVector by remember { mutableStateOf(Offset.Zero) }
    var sliderEndEyeVector by remember { mutableStateOf(Offset.Zero) }
    BoxWithConstraints(modifier = modifier
        .fillMaxSize()
        .onGloballyPositioned {
            sliderStartEyeVector = it.windowToLocal(sliderStartWindowVector)
            sliderEndEyeVector = it.windowToLocal(sliderEndWindowVector)
        }
    ) {

        var sliderCurrentEyeVector by remember(progress) {
            mutableStateOf(Offset.Zero)
        }
        var pupilTargetEyeVector by remember {
            mutableStateOf(Offset.Zero)
        }
        val sliderVectorMaxLength by remember {
            derivedStateOf { sliderEndEyeVector - sliderStartEyeVector }
        }
        sliderCurrentEyeVector =
            sliderStartEyeVector + sliderVectorMaxLength * progress
        val boxHeight = constraints.maxHeight.toFloat()
        val boxWidth = constraints.maxWidth.toFloat()

        val pupilCenter =
            Offset(if (flipHorizontally) boxWidth * 0.55f else boxWidth * 0.45f, boxHeight * 0.45f)
        var pupilCurrentPosition = remember {
            Animatable(
                Offset(pupilCenter.x, pupilCenter.y),
                Offset.VectorConverter
            )
        }
        val pupilMovementRadius = 0.17f * boxHeight

        pupilTargetEyeVector = (sliderCurrentEyeVector - pupilCenter).normalize()
            .times(pupilMovementRadius) + pupilCenter


//        Column(Modifier.align(Alignment.TopCenter)) {
////            Text("start: $sliderStartEyeVector")
////            Text("end: $sliderEndEyeVector")
////            Text("start: $sliderStartEyeVector")
////            Text("current: $sliderCurrentEyeVector")
////            Text("current: $pupilCurrentEyeVector")
//        }

        val pupilSize =
            with(LocalDensity.current) {
                (minOf(
                    constraints.maxHeight,
                    constraints.maxWidth
                ) * 0.07f).toDp()
            }

        Box(modifier = Modifier
            .size(pupilSize)
            .zIndex(1f)
            .offset {
                IntOffset(
                    pupilCurrentPosition.value.x.roundToInt() - (pupilSize / 2)
                        .toPx()
                        .roundToInt(),
                    pupilCurrentPosition.value.y.roundToInt() - (pupilSize / 2)
                        .toPx()
                        .roundToInt()
                )
            }
            .background(color = Color.Black, shape = CircleShape))

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            val width = size.width
            val height = size.height
            val scaleX = if (flipHorizontally) -1f else 1f
            scale(scaleX = scaleX, scaleY = 1f, pivot = Offset(width / 2, height / 2)) {

                val v1Values = VertexValues(
                    xValues = ValueLimits(
                        0.12f * width, 0.09f * width, 0.22f * width
                    ),
                    yValues = ValueLimits(
                        0.16f * height, 0.4f * height, 0.24f * height
                    )
                )

                val v2Values = VertexValues(
                    xValues = ValueLimits(
                        0.86f * width, 0.88f * width, 0.81f * width
                    ),
                    yValues = ValueLimits(
                        0.19f * height, 0.23f * height, 0.23f * height
                    )
                )
                val v3Values = VertexValues(
                    xValues = ValueLimits(
                        0.39f * width, 0.35f * width, 0.48f * width
                    ),
                    yValues = ValueLimits(
                        0.88f * height, 0.7f * height, 0.77f * height
                    )
                )

                val cp12aValues = VertexValues(
                    xValues = ValueLimits(
                        0.21f * width, -0.04f * width, 0.05f * width
                    ),
                    yValues = ValueLimits(
                        0.11f * height, -0.07f * height, -0.29f * height
                    )
                )
                val cp12bValues = VertexValues(
                    xValues = ValueLimits(
                        -0.21f * width, -0.05f * width, -0.03f * width
                    ),
                    yValues = ValueLimits(
                        0.08f * height, -0.05f * height, -0.22f * height
                    )
                )
                val cp23aValues = VertexValues(
                    xValues = ValueLimits(
                        -0.08f * width, 0.05f * width, 0.05f * width
                    ),
                    yValues = ValueLimits(
                        0.32f * height, 0.08f * height, 0.25f * height
                    )
                )
                val cp23bValues = VertexValues(
                    xValues = ValueLimits(
                        0.25f * width, 0.1f * width, 0.29f * width
                    ),
                    yValues = ValueLimits(
                        0.001f * height, 0f, 0.01f * height
                    )
                )
                val cp31aValues = VertexValues(
                    xValues = ValueLimits(
                        -0.21f * width, -0.08f * width, -0.19f * width
                    ),
                    yValues = ValueLimits(
                        -0.04f * height, -0.02f * height, -0.001f * height
                    )
                )
                val cp31bValues = VertexValues(
                    xValues = ValueLimits(
                        -0.01f * width, -0.04f * width, -0.04f * width
                    ),
                    yValues = ValueLimits(
                        0.24f * height, 0.06f, 0.27f * height
                    )
                )

                val v1 = v1Values.getOffsetValue(progress)
                val v2 = v2Values.getOffsetValue(progress)
                val v3 = v3Values.getOffsetValue(progress)
                val cp12a = cp12aValues.getOffsetValue(progress)
                val cp12b = cp12bValues.getOffsetValue(progress)
                val cp23a = cp23aValues.getOffsetValue(progress)
                val cp23b = cp23bValues.getOffsetValue(progress)
                val cp31a = cp31aValues.getOffsetValue(progress)
                val cp31b = cp31bValues.getOffsetValue(progress)

//            drawCircle(
//                color = Color.Red,
//                radius = 5f,
//                center = v1
//            )
//            drawCircle(
//                color = Color.Red,
//                radius = 5f,
//                center = v2
//            )
//            drawCircle(
//                color = Color.Red,
//                radius = 5f,
//                center = v3
//            )

                val path = Path().apply {
                    moveTo(v1.x, v1.y)
                    cubicTo(
                        v1.x + cp12a.x,
                        v1.y + cp12a.y,
                        v2.x + cp12b.x,
                        v2.y + cp12b.y,
                        v2.x,
                        v2.y
                    )
                    cubicTo(
                        v2.x + cp23a.x,
                        v2.y + cp23a.y,
                        v3.x + cp23b.x,
                        v3.y + cp23b.y,
                        v3.x,
                        v3.y
                    )
                    cubicTo(
                        v3.x + cp31a.x,
                        v3.y + cp31a.y,
                        v1.x + cp31b.x,
                        v1.y + cp31b.y,
                        v1.x,
                        v1.y
                    )
                }

                drawPath(path = path, color = Color.White)
                drawPath(path = path, color = strokeColor, style = Stroke(width = strokeWidth))
            }
        }

        LaunchedEffect(draggedOrPressed) {
            if (draggedOrPressed) {
                while (true) {
                    pupilCurrentPosition.animateTo(pupilTargetEyeVector)
                }
            } else pupilCurrentPosition.animateTo(pupilCenter)
        }
    }
}

fun Offset.normalize(): Offset {
    val m = getDistance()
    return if (m != 0.0f && m != 1.0f) {
        div(m)
    } else
        this
}