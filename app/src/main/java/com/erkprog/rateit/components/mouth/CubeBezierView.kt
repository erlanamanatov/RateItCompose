package com.erkprog.rateit.components.mouth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun CubicBezierViewer(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

//        var v1x by remember { mutableStateOf(0f) }
//        var v1y by remember { mutableStateOf(0f) }
//        var v2x by remember { mutableStateOf(0f) }
//        var v2y by remember { mutableStateOf(0f) }
//        var v3x by remember { mutableStateOf(0f) }
//        var v3y by remember { mutableStateOf(0f) }

//        var cp12ax by remember { mutableStateOf(0f) }
//        var cp12ay by remember { mutableStateOf(0f) }
//        var cp12bx by remember { mutableStateOf(0f) }
//        var cp12by by remember { mutableStateOf(0f) }
//        var cp23ax by remember { mutableStateOf(0f) }
//        var cp23ay by remember { mutableStateOf(0f) }
//        var cp23bx by remember { mutableStateOf(0f) }
//        var cp23by by remember { mutableStateOf(0f) }
//        var cp31ax by remember { mutableStateOf(0f) }
//        var cp31ay by remember { mutableStateOf(0f) }
//        var cp31bx by remember { mutableStateOf(0f) }
//        var cp31by by remember { mutableStateOf(0f) }

        var v1x by remember { mutableStateOf(0.1f * width) }
        var v1y by remember { mutableStateOf(0.1f * height) }
        var v2x by remember { mutableStateOf(0.85f * width) }
        var v2y by remember { mutableStateOf(0.1f * height) }
        var v3x by remember { mutableStateOf(0.45f * width) }
        var v3y by remember { mutableStateOf(0.9f * height) }

        var cp12ax by remember { mutableStateOf(v1x + 100f) }
        var cp12ay by remember { mutableStateOf(v1y + 100f) }
        var cp12bx by remember { mutableStateOf(v2x - 100f) }
        var cp12by by remember { mutableStateOf(v2y + 100f) }
        var cp23ax by remember { mutableStateOf(v2x) }
        var cp23ay by remember { mutableStateOf(v2y + 150f) }
        var cp23bx by remember { mutableStateOf(v3x + 150f) }
        var cp23by by remember { mutableStateOf(v3y + 20f) }
//        var cp31ax by remember { mutableStateOf(v3x - 150f) }
//        var cp31ay by remember { mutableStateOf(v3y + 20f) }
//        var cp31bx by remember { mutableStateOf(v1x - 100f) }
//        var cp31by by remember { mutableStateOf(v1y + 100f) }

        val v1Vector = Offset(v1x / width, v1y / height)
        val v2Vector = Offset(v2x / width, v2y / height)
        val v3Vector = Offset(v3x / width, v3y / height)

        var cp12aVector = -Offset(v1x, v1y) + Offset(cp12ax, cp12ay)
        cp12aVector = Offset(cp12aVector.x / width, cp12aVector.y / height)
        var cp12bVector = -Offset(v2x, v2y) + Offset(cp12bx, cp12by)
        cp12bVector = Offset(cp12bVector.x / width, cp12bVector.y / height)
        var cp23aVector = -Offset(v2x, v2y) + Offset(cp23ax, cp23ay)
        cp23aVector = Offset(cp23aVector.x / width, cp23aVector.y / height)
        var cp23bVector = -Offset(v3x, v3y) + Offset(cp23bx, cp23by)
        cp23bVector = Offset(cp23bVector.x / width, cp23bVector.y / height)
//        var cp31aVector = -Offset(v3x, v3y) + Offset(cp31ax, cp31ay)
//        cp31aVector = Offset(cp31aVector.x / width, cp31aVector.y / height)
//        var cp31bVector = -Offset(v1x, v1y) + Offset(cp31bx, cp31by)
//        cp31bVector = Offset(cp31bVector.x / width, cp31bVector.y / height)

        Column(
            Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .offset(y = (-220).dp)
        ) {
//            Text(text = "v1(%.2f, %.2f)".format(v1x, v1y))
            InfoText(pointName = "v1", xAbs = v1x, yAbs = v1y, xRel = v1Vector.x, yRel = v1Vector.y)
            InfoText(pointName = "v2", xAbs = v2x, yAbs = v2y, xRel = v2Vector.x, yRel = v2Vector.y)
            InfoText(pointName = "v3", xAbs = v3x, yAbs = v3y, xRel = v3Vector.x, yRel = v3Vector.y)
//            Text(text = "v2(%.2f, %.2f)".format(v2x, v2y))
//            Text(text = "v3(%.2f, %.2f)".format(v3x, v3y))
            Text(text = "  ")
            InfoText(
                pointName = "cp12a",
                xAbs = cp12ax,
                yAbs = cp12ay,
                xRel = cp12aVector.x,
                yRel = cp12aVector.y
            )
            InfoText(
                pointName = "cp12b",
                xAbs = cp12bx,
                yAbs = cp12by,
                xRel = cp12bVector.x,
                yRel = cp12bVector.y
            )
            InfoText(
                pointName = "cp23a",
                xAbs = cp23ax,
                yAbs = cp23ay,
                xRel = cp23aVector.x,
                yRel = cp23aVector.y
            )
            InfoText(
                pointName = "cp23b",
                xAbs = cp23bx,
                yAbs = cp23by,
                xRel = cp23bVector.x,
                yRel = cp23bVector.y
            )
//            InfoText(
//                pointName = "cp31a",
//                xAbs = cp31ax,
//                yAbs = cp31ay,
//                xRel = cp31aVector.x,
//                yRel = cp31aVector.y
//            )
//            InfoText(
//                pointName = "cp31b",
//                xAbs = cp31bx,
//                yAbs = cp31by,
//                xRel = cp31bVector.x,
//                yRel = cp31bVector.y
//            )
        }

        DraggableCircle(
            offsetX = v1x,
            offsetY = v1y,
            onDragX = { v1x += it },
            onDragY = { v1y += it }
        )
        DraggableCircle(
            offsetX = v2x,
            offsetY = v2y,
            onDragX = { v2x += it },
            onDragY = { v2y += it }
        )
        DraggableCircle(
            offsetX = v3x,
            offsetY = v3y,
            onDragX = { v3x += it },
            onDragY = { v3y += it }
        )

        DraggableCircle(
            offsetX = cp12ax,
            offsetY = cp12ay,
            onDragX = { cp12ax += it },
            onDragY = { cp12ay += it },
            color = Color.Green
        )
        DraggableCircle(
            offsetX = cp12bx,
            offsetY = cp12by,
            onDragX = { cp12bx += it },
            onDragY = { cp12by += it },
            color = Color.Green
        )
        DraggableCircle(
            offsetX = cp23ax,
            offsetY = cp23ay,
            onDragX = { cp23ax += it },
            onDragY = { cp23ay += it },
            color = Color.Green
        )
        DraggableCircle(
            offsetX = cp23bx,
            offsetY = cp23by,
            onDragX = { cp23bx += it },
            onDragY = { cp23by += it },
            color = Color.Green
        )

//        DraggableCircle(
//            offsetX = cp31ax,
//            offsetY = cp31ay,
//            onDragX = { cp31ax += it },
//            onDragY = { cp31ay += it },
//            color = Color.Green
//        )
//        DraggableCircle(
//            offsetX = cp31bx,
//            offsetY = cp31by,
//            onDragX = { cp31bx += it },
//            onDragY = { cp31by += it },
//            color = Color.Green
//        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path().apply {
                moveTo(v1x, v1y)
                cubicTo(cp12ax, cp12ay, cp12bx, cp12by, v2x, v2y)
                cubicTo(cp23ax, cp23ay, cp23bx, cp23by, v3x, v3y)
//                cubicTo(cp31ax, cp31ay, cp31bx, cp31by, v1x, v1y)
            }

            drawPath(path = path, color = Color.Red, style = Stroke(width = 2f))
        }
    }
}

@Composable
private fun DraggableCircle(
    offsetX: Float, offsetY: Float,
    onDragX: (Float) -> Unit,
    onDragY: (Float) -> Unit,
    color: Color = Color.Red
) {
    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .size(10.dp)
            .offset((-5).dp, (-5).dp)
            .background(color = color, shape = CircleShape)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    onDragX(dragAmount.x)
                    onDragY(dragAmount.y)
//                    offsetX += dragAmount.x
//                    offsetY += dragAmount.y
                }
            }
    )

}

@Composable
private fun InfoText(pointName: String, xAbs: Float, yAbs: Float, xRel: Float, yRel: Float) {
    Text(
        text = "%s(%.2f, %.2f)-vect(%.2f, %.2f)".format(
            pointName,
            xAbs,
            yAbs,
            xRel,
            yRel
        ),
        fontSize = 8.sp
    )

}