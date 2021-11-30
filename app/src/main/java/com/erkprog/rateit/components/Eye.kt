package com.erkprog.rateit.components

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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.erkprog.rateit.model.VertexCoordinates
import com.erkprog.rateit.util.cubicTo
import com.erkprog.rateit.util.moveTo
import com.erkprog.rateit.util.normalize
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
    var sliderStartLocalVector by remember { mutableStateOf(Offset.Zero) }
    var sliderEndLocalVector by remember { mutableStateOf(Offset.Zero) }
    BoxWithConstraints(modifier = modifier
        .fillMaxSize()
        .onGloballyPositioned {
            sliderStartLocalVector = it.windowToLocal(sliderStartWindowVector)
            sliderEndLocalVector = it.windowToLocal(sliderEndWindowVector)
        }
    ) {

        var sliderCurrentPositionVector by remember {
            mutableStateOf(Offset.Zero)
        }
        var pupilTargetVector by remember {
            mutableStateOf(Offset.Zero)
        }
        val sliderVectorMaxLength by remember {
            derivedStateOf { sliderEndLocalVector - sliderStartLocalVector }
        }
        sliderCurrentPositionVector =
            sliderStartLocalVector + sliderVectorMaxLength * progress

        val componentHeight = constraints.maxHeight.toFloat()
        val componentWidth = constraints.maxWidth.toFloat()
        val coords = remember(componentWidth, componentHeight) {
            EyeContentCoordinates(componentWidth, componentHeight)
        }

        val pupilCenter =
            Offset(
                if (flipHorizontally) componentWidth * 0.55f else componentWidth * 0.45f,
                componentHeight * 0.45f
            )
        val pupilCurrentPosition = remember {
            Animatable(
                Offset(pupilCenter.x, pupilCenter.y),
                Offset.VectorConverter
            )
        }
        val pupilMovementRadius = 0.17f * componentHeight

        pupilTargetVector = (sliderCurrentPositionVector - pupilCenter).normalize()
            .times(pupilMovementRadius) + pupilCenter

        val pupilSize =
            with(LocalDensity.current) {
                (minOf(
                    componentHeight,
                    componentWidth
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
                val path = Path()
                with(coords) {
                    val v1 = vertex1.getCurrentPosition(progress)
                    val v2 = vertex2.getCurrentPosition(progress)
                    val v3 = vertex3.getCurrentPosition(progress)
                    val cp12aCur = cp12a.getCurrentPosition(progress)
                    val cp12bCur = cp12b.getCurrentPosition(progress)
                    val cp23aCur = cp23a.getCurrentPosition(progress)
                    val cp23bCur = cp23b.getCurrentPosition(progress)
                    val cp31aCur = cp31a.getCurrentPosition(progress)
                    val cp31bCur = cp31b.getCurrentPosition(progress)

                    path.apply {
                        moveTo(v1)
                        cubicTo(v1 + cp12aCur, v2 + cp12bCur, v2)
                        cubicTo(v2 + cp23aCur, v3 + cp23bCur, v3)
                        cubicTo(v3 + cp31aCur, v1 + cp31bCur, v1)
                    }
                }

                drawPath(path = path, color = Color.White)
                drawPath(
                    path = path,
                    color = strokeColor,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }

        LaunchedEffect(draggedOrPressed) {
            if (draggedOrPressed) {
                while (true) {
                    pupilCurrentPosition.animateTo(pupilTargetVector)
                }
            } else pupilCurrentPosition.animateTo(pupilCenter)
        }
    }
}