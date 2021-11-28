package com.erkprog.rateit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.erkprog.rateit.components.mouth.CubicBezierViewer
import com.erkprog.rateit.components.mouth.Eye
import com.erkprog.rateit.components.mouth.Mouth
import com.erkprog.rateit.ui.theme.RateItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RateItTheme {
                Surface(color = Color(0xFFc4eafc)) {
                    BoxWithConstraints(Modifier.fillMaxSize()) {
                        val width = constraints.maxWidth
                        val height = constraints.maxHeight
                        val widthDp = with(LocalDensity.current) { width.toDp() }
                        val heightDp = with(LocalDensity.current) { height.toDp() }
                        var progress by remember { mutableStateOf(0f) }
//                        CubicBezierViewer(
//                            modifier = Modifier
//                                .align(Alignment.Center)
//                                .size(250.dp)
//                                .border(width = 2.dp, color = Color.Blue)
//                                .zIndex(2f)
//                        )
                        Eye(
                            progress = progress,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset(x = 50.dp, y = (-120).dp)
                                .fillMaxWidth(0.35f)
                                .aspectRatio(1f)
                        )
                        Eye(
                            progress = progress,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .offset(x = (-50).dp, y = (-120).dp)
                                .fillMaxWidth(0.35f)
                                .aspectRatio(1f),
                            flipHorizontally = true
                        )
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
                                    y = heightDp * 0.5f
                                )
                                .width(widthDp * 0.45f)
                                .height(heightDp * 0.18f)
                        )

                        Slider(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(bottom = 40.dp)
                                .padding(horizontal = 24.dp),
                            value = progress,
                            onValueChange = {
                                progress = it
                            }
                        )
                    }
                }
            }
        }
    }
}

