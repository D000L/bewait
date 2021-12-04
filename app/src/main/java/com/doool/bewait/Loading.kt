package com.doool.bewait

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId

@Composable
fun Loadings(modifier: Modifier = Modifier) {
    Column(modifier) {
        BoxRotationLoading()
        WaveLoading(7, 1000, 300)
        BoxLoading()
        BoxBoundLoading(5, 450, 120)
        GridCardLoading(600, 200)
        CircleSpreadLoading(80)
    }
}

@Composable
fun BoxRotationLoading(modifier: Modifier = Modifier, size: Dp = 20.dp, distance: Dp = 40.dp) {

    val density = LocalDensity.current
    val distancePx = remember(distance) { with(density) { distance.toPx() } }

    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                900,
                easing = CubicBezierEasing(0.76f, 0.99f, 0.28f, .03f)
            )
        )
    )

    Box(
        modifier
            .defaultMinSize(minWidth = size + distance, minHeight = size + distance),
        contentAlignment = Alignment.Center
    ) {

        val scale = when (progress) {
            in 0.0f..0.5f -> 1f - progress
            in 0.5f..1f -> progress
            else -> 0f
        }

        val position = when (progress) {
            in 0.0f..0.5f -> Offset(progress * 2 * distancePx, 0f)
            in 0.5f..1f -> Offset(distancePx, (progress - 0.5f) * 2f * distancePx)
            else -> Offset(0f, 0f)
        }.run { Offset(x - distancePx / 2f, y - distancePx / 2f) }

        Box(
            modifier = Modifier
                .size(size)
                .graphicsLayer(
                    translationX = position.x,
                    translationY = position.y,
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = -180 * progress
                )
                .background(Color.Blue)
        )

        Box(
            modifier = Modifier
                .size(size)
                .graphicsLayer(
                    translationX = -position.x,
                    translationY = -position.y,
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = -180 * progress
                )
                .background(Color.Blue)
        )
    }
}

@Composable
fun WaveLoading(count: Int = 7, duration: Int = 1000, delay: Int = 300) {

    val totalDuration = remember(count, duration, delay) { delay * count + duration }

    val progress by rememberInfiniteTransition().animateValue(
        initialValue = 0,
        targetValue = totalDuration,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(tween(totalDuration, easing = LinearEasing))
    )

    Row() {
        for (i in 0 until count) {
            val itemProgress = progress - delay * i

            val scale = when (itemProgress.toFloat()) {
                in 0f..duration / 2f -> itemProgress.toFloat() / duration.toFloat()
                in duration / 2f..duration.toFloat() -> 1f - (itemProgress.toFloat() / duration.toFloat())
                else -> 0f
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .scale(scale * 2)
                    .background(Color.Red, shape = CircleShape)
            )
        }
    }
}

@Composable
fun BoxLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing))
    )

    val rotateX = if (progress in 0f..0.5f) -progress * 2f * 180 else 0f
    val rotateY = if (progress in 0.5f..1f) (progress - 0.5f) * 2f * 180 else 0f

    Box(
        modifier = Modifier
            .layoutId("box", "box")
            .size(40.dp)
            .graphicsLayer(rotationX = rotateX, rotationY = rotateY)
            .background(Color.Red)
    )
}

val FastOutFastInEasing = CubicBezierEasing(0.42f, 0f, 0.58f, 1.0f)

@Composable
fun BoxBoundLoading(count: Int = 5, duration: Int = 450, delay: Int = 120) {
    val totalDuration = delay * count + duration

    val progress by rememberInfiniteTransition().animateValue(
        initialValue = 0,
        targetValue = totalDuration,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(tween(totalDuration, easing = LinearEasing))
    )

    val easing = remember { CubicBezierEasing(0.42f, 0f, 0.58f, 1.0f) }

    Row(
        Modifier
            .height(60.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until count) {
            val start = delay * i
            val progress = progress - start

            val scale =
                if (0 <= progress && progress <= duration.toFloat() / 2) progress.toFloat() / duration.toFloat()
                else if (duration.toFloat() / 2 < progress && progress <= duration) 1f - (progress.toFloat() / duration.toFloat())
                else 0f

            Box(
                modifier = Modifier
                    .size(width = 6.dp, height = 20.dp)
                    .scale(scaleX = 1f, scaleY = 1f + easing.transform(scale) * 4)
                    .background(Color.Blue)
            )
        }
    }
}

@Composable
fun GridCardLoading(duration: Int = 600, delay: Int = 200) {
    val totalDuration = delay * 5 + duration

    val progress by rememberInfiniteTransition().animateValue(
        initialValue = 0,
        targetValue = totalDuration,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(tween(totalDuration, easing = LinearEasing))
    )

    val easing = remember { CubicBezierEasing(0.42f, 0f, 0.58f, 1.0f) }

    Box(
        Modifier
            .height(60.dp)
            .padding(horizontal = 10.dp),
    ) {
        val delayList = listOf(
            0,
            delay,
            delay * 2,
            delay,
            delay * 2,
            delay * 3,
            delay * 2,
            delay * 3,
            delay * 4
        )
        val posList = listOf(
            DpOffset(0.dp, 0.dp),
            DpOffset(10.dp, 0.dp),
            DpOffset(20.dp, 0.dp),
            DpOffset(0.dp, 10.dp),
            DpOffset(10.dp, 10.dp),
            DpOffset(20.dp, 10.dp),
            DpOffset(0.dp, 20.dp),
            DpOffset(10.dp, 20.dp),
            DpOffset(20.dp, 20.dp),
        )

        for (i in 0 until 9) {
            val offset = posList[i]
            val start = delayList[i]
            val progress = progress - start

            val scale =
                if (0 <= progress && progress <= duration.toFloat() / 2) progress.toFloat() / duration.toFloat()
                else if (duration.toFloat() / 2 < progress && progress <= duration) 1f - (progress.toFloat() / duration.toFloat())
                else 0f

            Box(
                modifier = Modifier
                    .size(width = 10.dp, height = 10.dp)
                    .offset(offset.x, offset.y)
                    .scale(1f - easing.transform(scale) * 2)
                    .background(Color.Blue)
            )
        }
    }
}

@Composable
fun CircleSpreadLoading(radius: Int = 80) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        val start = System.currentTimeMillis()
        while (true) {
            withInfiniteAnimationFrameMillis {
                progress = (System.currentTimeMillis() - start).toFloat() / 1000f
            }
        }
    }

    val delay = 1f / 12f
    val duration = 0.6f

    BoxWithConstraints(
        Modifier
            .height(80.dp)
            .padding(horizontal = 10.dp),
    ) {
        val density = LocalDensity.current
        val center = with(density) { Offset(maxWidth.toPx() / 2f, maxHeight.toPx() / 2f) }

        for (i in 0 until 12) {
            val degree = i * 30.0
            val x = radius * Math.cos(Math.toRadians(degree)) + center.x
            val y = radius * Math.sin(Math.toRadians(degree)) + center.y

            val delay = delay * i

            val realProgress = (progress - delay) % 1f
            val scale =
                if (0f <= realProgress && realProgress <= duration / 2) (realProgress) / duration
                else if (duration / 2 <= realProgress && realProgress <= duration) 1f - ((realProgress) / duration)
                else 0f

            Box(
                modifier = Modifier
                    .size(size = 10.dp)
                    .graphicsLayer(translationX = x.toFloat(), translationY = y.toFloat())
                    .scale(scale * 2)
                    .background(Color.Blue, CircleShape)
            )
        }
    }
}