package com.erkprog.rateit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.erkprog.rateit.components.*
import com.erkprog.rateit.ui.theme.RateItTheme
import com.erkprog.rateit.util.FaceShakeAnimation
import com.erkprog.rateit.util.shakeFace
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RateItTheme {
                Surface(color = Color(0xfffff5ee)) {
                    BoxWithConstraints(Modifier.fillMaxSize()) {
                        val width = constraints.maxWidth
                        val height = constraints.maxHeight
                        val widthDp = with(LocalDensity.current) { width.toDp() }
                        val heightDp = with(LocalDensity.current) { height.toDp() }
                        var progress by remember { mutableStateOf(0.5f) }

                        var sliderStartWindowVector by remember { mutableStateOf(Offset.Zero) }
                        var sliderEndWindowVector by remember { mutableStateOf(Offset.Zero) }

                        val interactionSource = remember { MutableInteractionSource() }
                        var sliderPositioned by remember { mutableStateOf(false) }

                        val dragged by interactionSource.collectIsDraggedAsState()
                        val pressed by interactionSource.collectIsPressedAsState()
                        val draggedOrPressed by remember {
                            derivedStateOf { dragged || pressed }
                        }

                        val shake by remember {
                            derivedStateOf { progress < FaceShakeAnimation.shakeThreshold }
                        }
                        val shakeValue = with(LocalDensity.current) {
                            (widthDp * 0.005f).toPx().roundToInt()
                        }
                        val shakeOffset =
                            remember { Animatable(IntOffset.Zero, IntOffset.VectorConverter) }

                        BgColor(
                            progress = progress, modifier = Modifier
                                .fillMaxSize()
                                .zIndex(0f)
                        )

                        if (sliderPositioned) {
                            Eye(
                                progress = progress,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .offset {
                                        IntOffset(
                                            50.dp
                                                .toPx()
                                                .roundToInt(),
                                            (-60).dp
                                                .toPx()
                                                .roundToInt()
                                        ) + shakeOffset.value
                                    }
                                    .fillMaxWidth(0.35f)
                                    .aspectRatio(1f),
                                sliderStartWindowVector = sliderStartWindowVector,
                                sliderEndWindowVector = sliderEndWindowVector,
                                draggedOrPressed = draggedOrPressed
                            )
                            Eye(
                                progress = progress,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .offset {
                                        IntOffset(
                                            x = (-50).dp
                                                .toPx()
                                                .roundToInt(),
                                            y = (-60).dp
                                                .toPx()
                                                .roundToInt()
                                        ) + shakeOffset.value
                                    }
                                    .fillMaxWidth(0.35f)
                                    .aspectRatio(1f),
                                flipHorizontally = true,
                                sliderStartWindowVector = sliderStartWindowVector,
                                sliderEndWindowVector = sliderEndWindowVector,
                                draggedOrPressed = draggedOrPressed
                            )
                        }

                        Mouth(
                            progress = progress,
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        x = (widthDp * 0.26f)
                                            .toPx()
                                            .roundToInt(),
                                        y = (heightDp * 0.6f)
                                            .toPx()
                                            .roundToInt()
                                    ) + shakeOffset.value
                                }
                                .width(widthDp * 0.45f)
                                .height(heightDp * 0.18f)
                        )

                        Slider(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(bottom = 100.dp)
                                .padding(horizontal = 24.dp)
                                .onGloballyPositioned {
                                    sliderStartWindowVector = it.positionInWindow()
                                    sliderEndWindowVector =
                                        it.positionInWindow() + Offset(it.size.width.toFloat(), 0f)
                                    sliderPositioned = true
                                },
                            value = progress,
                            onValueChange = {
                                progress = it
                            },
                            interactionSource = interactionSource
                        )

                        LaunchedEffect(shake) {
                            if (shake) {
                                shakeOffset.shakeFace(shakeValue)
                            } else {
                                shakeOffset.animateTo(IntOffset.Zero)
                            }
                        }

                    }
                }
            }
        }
    }

}

