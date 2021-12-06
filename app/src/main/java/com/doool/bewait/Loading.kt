package com.doool.bewait

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Loadings(modifier: Modifier = Modifier) {
    HorizontalPager(modifier = modifier, count = 7) {
        when (it) {
            0 -> BoxRotationLoading()
            1 -> WaveLoading(7, 1000, 300)
            2 -> BoxLoading()
            3 -> BoxBoundLoading(5, 450, 120)
            4 -> GridCardLoading(600, 200)
            5 -> CircleSpreadLoading(80)
            6 -> CircleTailLoading()
        }
    }
}

@Composable
fun CircleTailLoading() {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        val start = System.currentTimeMillis()
        while (true) {
            withInfiniteAnimationFrameMillis {
                progress = (System.currentTimeMillis() - start).toFloat() / 2000f
            }
        }
    }

    val radius = 80
    val delay = 0.05f
    val duration = 1f

    val boxDegree = (300 * progress) % 360

    Box(
        modifier = Modifier
            .defaultMinSize(60.dp, 60.dp)
            .rotate(boxDegree),
        contentAlignment = Alignment.Center
    ) {
        for (i in 0 until 6) {
            val itemProgress = (progress - delay * i) % 1f

            val itemDegree = when (itemProgress) {
                in 0f..0.8f -> 360f * FastOutFastInEasing.transform(itemProgress * (10f / 8f))
                else -> 0
            }
            val x = radius * Math.cos(Math.toRadians(itemDegree.toDouble()))
            val y = radius * Math.sin(Math.toRadians(itemDegree.toDouble()))

            val scale = AnimationUtils.toScale(itemProgress, duration)

            Box(
                modifier = Modifier
                    .size(size = 10.dp)
                    .graphicsLayer(translationX = x.toFloat(), translationY = y.toFloat())
                    .scale(1f - scale)
                    .background(Color.Blue, CircleShape)
            )
        }
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

        val scale = 1f - AnimationUtils.toScale(progress)

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
            val itemProgress by derivedStateOf {
                (progress - delay * i).toFloat()
            }

            val scale = AnimationUtils.toScale(itemProgress, duration.toFloat())

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

    Row(
        Modifier.height(60.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until count) {
            val itemProgress by derivedStateOf {
                val start = delay * i
                (progress - start).toFloat()
            }

            val scale = AnimationUtils.toScale(itemProgress, duration.toFloat())

            Box(
                modifier = Modifier
                    .size(width = 6.dp, height = 20.dp)
                    .scale(scaleX = 1f, scaleY = 1f + FastOutFastInEasing.transform(scale) * 4)
                    .background(Color.Blue)
            )
        }
    }
}

object AnimationUtils {
    fun toScale(progress: Float, duration: Float = 1f) = when (progress) {
        in 0f..duration / 2f -> progress / duration
        in duration / 2f..duration -> 1f - (progress / duration)
        else -> 0f
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

    Box(Modifier.height(30.dp)) {
        val delayList = listOf(0, 1, 2, 1, 2, 3, 2, 3, 4)
        val posList = listOf(
            Pair(0, 0),
            Pair(1, 0),
            Pair(2, 0),
            Pair(0, 1),
            Pair(1, 1),
            Pair(2, 1),
            Pair(0, 2),
            Pair(1, 2),
            Pair(2, 2)
        )

        for (i in 0 until 9) {
            val itemProgress by derivedStateOf {
                val start = delay * delayList[i]
                (progress - start).toFloat()
            }

            val scale = AnimationUtils.toScale(itemProgress, duration.toFloat())

            Box(
                modifier = Modifier
                    .size(width = 10.dp, height = 10.dp)
                    .offset(10.dp * posList[i].first, 10.dp * posList[i].second)
                    .scale(1f - FastOutFastInEasing.transform(scale) * 2)
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

    Box(
        modifier = Modifier.defaultMinSize(80.dp, 80.dp),
        contentAlignment = Alignment.Center
    ) {
        for (i in 0 until 12) {
            val (x, y) = remember(i, radius) {
                val degree = i * 30.0
                Pair(
                    radius * Math.cos(Math.toRadians(degree)),
                    radius * Math.sin(Math.toRadians(degree))
                )
            }

            val itemProgress by derivedStateOf {
                (progress - delay * i) % 1f
            }
            val scale = AnimationUtils.toScale(itemProgress, duration)

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
