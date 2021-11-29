package com.erkprog.rateit.util

import androidx.compose.animation.core.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.random.Random

object FaceShakeAnimation {
    val animSpec: AnimationSpec<IntOffset> = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = 80000f
    )
    const val minDelay = 1000L
    const val maxDelay = 1300L
    const val shakeThreshold = 0.35f
}

suspend fun Animatable<IntOffset, AnimationVector2D>.shakeFace(
    offset: Int
) {
    while (true) {
        delay(Random.nextLong(FaceShakeAnimation.minDelay, FaceShakeAnimation.maxDelay))
        animateTo(
            IntOffset(offset, -offset),
            animationSpec = FaceShakeAnimation.animSpec
        )
        animateTo(
            IntOffset(-offset, offset),
            animationSpec = FaceShakeAnimation.animSpec
        )
        animateTo(
            IntOffset(offset, -offset),
            animationSpec = FaceShakeAnimation.animSpec
        )
        animateTo(
            IntOffset(-offset, offset),
            animationSpec = FaceShakeAnimation.animSpec
        )
        animateTo(
            IntOffset(offset, offset),
            animationSpec = FaceShakeAnimation.animSpec
        )
        animateTo(
            IntOffset(-offset, -offset),
            animationSpec = FaceShakeAnimation.animSpec
        )
        animateTo(
            IntOffset.Zero,
            animationSpec = FaceShakeAnimation.animSpec
        )
    }
}


