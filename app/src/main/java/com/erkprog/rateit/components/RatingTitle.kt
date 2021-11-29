package com.erkprog.rateit.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erkprog.rateit.Rating

@ExperimentalAnimationApi
@Composable
fun RatingTitle(
    modifier: Modifier = Modifier,
    rating: Rating
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "How was your ride?",
            style = TextStyle(
                color = Color.Black,
                fontSize = 40.sp
            ),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        AnimatedContent(
            targetState = rating,
            transitionSpec = {
                if (targetState.value > initialState.value) {
                    slideInHorizontally({ fullWidth -> 2 * fullWidth }) + fadeIn() with
                            slideOutHorizontally({ fullWidth -> -2 * fullWidth }) + fadeOut()
                } else {
                    slideInHorizontally({ fullWidth -> -2 * fullWidth }) + fadeIn() with
                            slideOutHorizontally({ fullWidth -> 2 * fullWidth }) + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) { targetRating ->
            Text(
                text = targetRating.title,
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.8f),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }

    }
}