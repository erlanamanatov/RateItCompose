package com.erkprog.rateit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.erkprog.rateit.components.CubicBezierViewer
import com.erkprog.rateit.components.*
import com.erkprog.rateit.ui.theme.RateItTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
                            derivedStateOf { progress < 0.4f }
                        }
                        val shakeAmpl = (LocalConfiguration.current.screenWidthDp * 0.05f).dp
                        val shakeX = remember { Animatable(0.dp, Dp.VectorConverter) }
                        val shakeY = remember { Animatable(0.dp, Dp.VectorConverter) }
                        val shakeValue =
                            remember { Animatable(IntOffset.Zero, IntOffset.VectorConverter) }

                        BgColor(
                            progress = progress, modifier = Modifier
                                .fillMaxSize()
                                .zIndex(0f)
                        )

//                        Column(modifier = Modifier.align(Alignment.TopCenter)) {
////                            Text(text = "start: $sliderStartWindowVector")
////                            Text(text = "start: $sliderEndWindowVector")
//                            Text(text = "dragged: $dragged")
//                            Text(text = "focused: $focused")
//                            Text(text = "pressed: $pressed")
//                            Text(text = "combined: $draggedOrPressed")
//                        }

//                        CubicBezierViewer(
//                            modifier = Modifier
//                                .align(Alignment.Center)
//                                .size(250.dp)
//                                .border(width = 2.dp, color = Color.Blue)
//                                .zIndex(2f)
//                        )
                        if (sliderPositioned) {
                            Eye(
                                progress = progress,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
//                                    .offset(x = 50.dp, y = (-60).dp)
                                    .offset {
                                        IntOffset(
                                            50.dp
                                                .toPx()
                                                .roundToInt(),
                                            (-60).dp
                                                .toPx()
                                                .roundToInt()
                                        ) + shakeValue.value
                                    }
                                    .fillMaxWidth(0.35f)
                                    .aspectRatio(1f),
//                                    .border(width = 3.dp, color = Color.Red),
                                sliderStartWindowVector = sliderStartWindowVector,
                                sliderEndWindowVector = sliderEndWindowVector,
                                draggedOrPressed = draggedOrPressed
                            )
                            Eye(
                                progress = progress,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .offset(x = (-50).dp, y = (-60).dp)
                                    .fillMaxWidth(0.35f)
                                    .aspectRatio(1f),
                                flipHorizontally = true,
                                sliderStartWindowVector = sliderStartWindowVector,
                                sliderEndWindowVector = sliderEndWindowVector,
                                draggedOrPressed = draggedOrPressed
                            )
                        }

//                        CubicBezierViewer(
//                            modifier = Modifier
//                                .offset(
//                                    x = widthDp * 0.26f,
//                                    y = heightDp * 0.5f
//                                )
//                                .width(widthDp * 0.45f)
//                                .height(heightDp * 0.18f)
////                                .size(250.dp)
//                                .border(width = 2.dp, color = Color.Blue)
//                                .zIndex(2f)
//                        )

                        Mouth(
                            progress = progress,
                            modifier = Modifier
                                .offset(
                                    x = widthDp * 0.26f,
                                    y = heightDp * 0.6f
                                )
                                .width(widthDp * 0.45f)
                                .height(heightDp * 0.18f)
                        )

                        Slider(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
//                                .border(width = 3.dp, color = Color.Green)
                                .padding(bottom = 100.dp)
                                .padding(horizontal = 24.dp)
//                                .border(width = 3.dp, color = Color.Red)
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
                            val stiffness = 80000f
//                            val dRatio = Spring.DampingRatioHighBouncy
                            val dRatio = Spring.DampingRatioLowBouncy
                            val sss = 3
                            if (shake) {
                                while (true) {
                                    delay(1000)
                                    shakeValue.animateTo(
                                        IntOffset(sss, sss),
                                        animationSpec = spring(
                                            dampingRatio = dRatio,
                                            stiffness = stiffness
                                        )
                                    )
                                    shakeValue.animateTo(
                                        IntOffset(-sss, -sss),
                                        animationSpec = spring(
                                            dampingRatio = dRatio,
                                            stiffness = stiffness
                                        )

                                    )
                                    shakeValue.animateTo(
                                        IntOffset(sss, sss),
                                        animationSpec = spring(
                                            dampingRatio = dRatio,
                                            stiffness = stiffness
                                        )
                                    )
                                    shakeValue.animateTo(
                                        IntOffset(-sss, -sss),
                                        animationSpec = spring(
                                            dampingRatio = dRatio,
                                            stiffness = stiffness
                                        )

                                    )
                                }

                            } else {
//                                launch { shakeX.animateTo(0.dp) }
//                                launch { shakeY.animateTo(0.dp) }
                                shakeValue.animateTo(IntOffset.Zero)
                            }


                        }

                    }
                }
            }
        }
    }

}

