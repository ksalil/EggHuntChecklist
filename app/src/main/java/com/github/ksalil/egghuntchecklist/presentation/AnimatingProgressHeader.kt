package com.github.ksalil.egghuntchecklist.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.Brown
import com.github.ksalil.egghuntchecklist.presentation.ui.theme.Yellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimatingProgressHeader(
    isEggChecked: Boolean,
    animationDurationMillis: Int = 4000,
    seconds: Int = 4
) {
    val progress = remember { Animatable(1f) }
    var secondsRemaining by remember { mutableIntStateOf(seconds) }

    // Custom progress bar implementation
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Progress bar container
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
            ) {
                // Background track (full width)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Brown) // Dark brown/orange background
                )

                // Foreground progress (animated width)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.value)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Yellow)
                ) {
                    // Circular indicator at the end of the progress bar
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .offset(x = 4.dp) // Move slightly outside the bar
                            .size(10.dp)
                            .background(
                                Yellow,
                                shape = RoundedCornerShape(percent = 50)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${secondsRemaining}s",
                color = Yellow,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }

    LaunchedEffect(key1 = isEggChecked) {
        // Reset progress when the dialog is shown
        progress.snapTo(1f)
        secondsRemaining = 4

        // First coroutine: animate progress from 1f to 0f over 4 seconds
        launch {
            progress.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = animationDurationMillis,
                    easing = LinearEasing
                )
            )
        }

        // Second coroutine: update the seconds remaining text
        launch {
            while (secondsRemaining > 0) {
                delay(1000)
                secondsRemaining--
            }
        }
    }
}