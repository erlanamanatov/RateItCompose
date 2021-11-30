package com.erkprog.rateit

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.erkprog.rateit.components.*
import com.erkprog.rateit.model.Rating
import com.erkprog.rateit.util.FaceShakeAnimation
import com.erkprog.rateit.util.shakeFace
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@Composable
fun RateItScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        var progress by remember { mutableStateOf(0.5f) }

        val rating: State<Rating> = remember {
            derivedStateOf {
                when (progress) {
                    in 0f..0.35f -> Rating.HIDEOUS
                    in 0.35f..0.7f -> Rating.OK
                    else -> Rating.GOOD
                }
            }
        }

        var sliderStartWindowVector by remember { mutableStateOf(Offset.Zero) }
        var sliderEndWindowVector by remember { mutableStateOf(Offset.Zero) }

        val interactionSource = remember { MutableInteractionSource() }
        var sliderPositioned by remember { mutableStateOf(false) }

        val dragged by interactionSource.collectIsDraggedAsState()
        val pressed by interactionSource.collectIsPressedAsState()

        val shake by remember {
            derivedStateOf { progress < FaceShakeAnimation.shakeThreshold }
        }
        val width = LocalConfiguration.current.screenWidthDp
        val shakeValue = (width * 0.01f).roundToInt().coerceAtLeast(2)
        val shakeOffset =
            remember { Animatable(IntOffset.Zero, IntOffset.VectorConverter) }

        BgColor(
            progress = progress, modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        )

        Column(
            modifier = Modifier
                .padding(vertical = 35.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RatingTitle(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .weight(0.3f),
                rating = rating.value
            )
            AnimatedFace(
                modifier = Modifier
                    .weight(0.55f)
                    .fillMaxWidth()
                    .offset { shakeOffset.value },
                sliderPositioned = sliderPositioned,
                progress = progress,
                sliderStartWindowVector = sliderStartWindowVector,
                sliderEndWindowVector = sliderEndWindowVector,
                focusedOnSlider = dragged || pressed
            )

            CustomSlider(
                modifier = Modifier
                    .weight(0.15f)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
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
        }

        LaunchedEffect(shake) {
            if (shake) {
                shakeOffset.shakeFace(shakeValue)
            } else {
                shakeOffset.animateTo(IntOffset.Zero)
            }
        }
    }
}

