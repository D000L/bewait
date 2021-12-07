package com.doool.bewait.loading

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import com.doool.bewait.AnimationUtils
import com.doool.bewait.easing.FastOutFastInEasing
import com.doool.bewait.easing.KeyframeEasing
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun Loadings(modifier: Modifier = Modifier) {
    LazyVerticalGrid(modifier = modifier, cells = GridCells.Adaptive(80.dp)) {
        items(12) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF2980b9)),
                contentAlignment = Alignment.Center
            ) {
                when (it) {
                    0 -> PlaneLoading()
                    1 -> ChaseCircleLoading()
                    2 -> BounceCircleLoading()
                    3 -> BounceRectLoading(5, 450, 120)
                    4 -> RotationBoxLoading()
                    5 -> FadeCircleLoading()
                    6 -> SwingCircleLoading()
                    7 -> WaveCircleLoading(3, 1000, 300)
                    8 -> ChaseDotLoading()
                    9 -> GridCardLoading(600, 200)
                    10 -> FadeDotLoading()
                    11 -> FoldRectLoading()
                }
            }
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun PlaneLoading() {
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
            .background(Color.White)
    )
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun ChaseCircleLoading() {
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

            val scale = AnimationUtils.to1f0f1f(itemProgress, duration)

            Box(
                modifier = Modifier
                    .size(size = 10.dp)
                    .graphicsLayer(translationX = x.toFloat(), translationY = y.toFloat())
                    .scale(1f - scale)
                    .background(Color.White, CircleShape)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun BounceCircleLoading() {
    Box() {
        val progress by rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(1000, easing = FastOutFastInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            Modifier
                .size(40.dp)
                .alpha(0.6f)
                .scale(progress)
                .background(Color.White, shape = CircleShape)
        )
        Box(
            Modifier
                .size(40.dp)
                .alpha(0.6f)
                .scale(1f - progress)
                .background(Color.White, shape = CircleShape)
        )
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun BounceRectLoading(count: Int = 5, duration: Int = 450, delay: Int = 120) {
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

            val scale = AnimationUtils.to1f0f1f(itemProgress, duration.toFloat())

            Box(
                modifier = Modifier
                    .size(width = 6.dp, height = 20.dp)
                    .scale(scaleX = 1f, scaleY = 1f + FastOutFastInEasing.transform(scale) * 4)
                    .background(Color.White)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun RotationBoxLoading(modifier: Modifier = Modifier, size: Dp = 20.dp, distance: Dp = 40.dp) {

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

        val scale = 1f - AnimationUtils.to1f0f1f(progress)

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
                .background(Color.White)
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
                .background(Color.White)
        )
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun FadeCircleLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1000, easing = FastOutFastInEasing))
    )

    Box(
        Modifier
            .size(40.dp)
            .alpha(0.6f * (1f - progress))
            .scale(progress)
            .background(Color.White, shape = CircleShape)
    )
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun SwingCircleLoading() {
    val transition = rememberInfiniteTransition()

    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing))
    )

    Box(Modifier.rotate(360 * progress)) {
        val progress2 by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(tween(2000, easing = FastOutFastInEasing))
        )

        val progress3 by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(2000, easing = FastOutFastInEasing),
                initialStartOffset = StartOffset(1000)
            )
        )

        Box(
            Modifier
                .size(30.dp)
                .graphicsLayer(translationX = 30f, translationY = -24f)
                .scale(AnimationUtils.to1f0f1f(progress2, 1f) * 2)
                .background(Color.White, shape = CircleShape)
        )
        Box(
            Modifier
                .size(30.dp)
                .graphicsLayer(translationX = 30f, translationY = 24f)
                .scale(AnimationUtils.to1f0f1f(progress3, 1f) * 2)
                .background(Color.White, shape = CircleShape)
        )
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun WaveCircleLoading(count: Int = 7, duration: Int = 1000, delay: Int = 300) {

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

            val scale = AnimationUtils.to1f0f1f(itemProgress, duration.toFloat())

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .scale(scale * 2)
                    .background(Color.White, shape = CircleShape)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun ChaseDotLoading() {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        val start = System.currentTimeMillis()
        while (true) {
            withInfiniteAnimationFrameMillis {
                progress = (System.currentTimeMillis() - start).toFloat() / 1200f
            }
        }
    }

    val delay = 1f / 12f

    Box(
        modifier = Modifier.defaultMinSize(80.dp, 80.dp),
        contentAlignment = Alignment.Center
    ) {
        for (i in 0 until 12) {
            val (x, y) = remember(i) {
                val degree = i * 30.0
                Pair(
                    50 * Math.cos(Math.toRadians(degree)),
                    50 * Math.sin(Math.toRadians(degree))
                )
            }

            val itemProgress by derivedStateOf {
                (progress - delay * i) % 1f
            }
            val scale = AnimationUtils.to1f0f1f(itemProgress)

            Box(
                modifier = Modifier
                    .size(size = 6.dp)
                    .graphicsLayer(translationX = x.toFloat(), translationY = y.toFloat())
                    .scale(scale * 2)
                    .background(Color.White, CircleShape)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

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
            Pair(-1, 0),
            Pair(0, 0),
            Pair(1, 0),
            Pair(-1, 1),
            Pair(0, 1),
            Pair(1, 1),
            Pair(-1, 2),
            Pair(0, 2),
            Pair(1, 2)
        )

        for (i in 0 until 9) {
            val itemProgress by derivedStateOf {
                val start = delay * delayList[i]
                (progress - start).toFloat()
            }

            val scale = AnimationUtils.to1f0f1f(itemProgress, duration.toFloat())

            Box(
                modifier = Modifier
                    .size(width = 10.dp, height = 10.dp)
                    .offset(10.dp * posList[i].first, 10.dp * posList[i].second)
                    .scale(1f - FastOutFastInEasing.transform(scale) * 2)
                    .background(Color.White)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun FadeDotLoading() {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        val start = System.currentTimeMillis()
        while (true) {
            withInfiniteAnimationFrameMillis {
                progress = (System.currentTimeMillis() - start).toFloat() / 1200f
            }
        }
    }

    val delay = 1f / 12f

    Box(
        modifier = Modifier.defaultMinSize(80.dp, 80.dp),
        contentAlignment = Alignment.Center
    ) {
        for (i in 0 until 12) {
            val itemProgress by derivedStateOf {
                (progress - delay * i) % 1f
            }

            val (x, y) = remember(i) {
                val degree = i * 30.0
                Pair(
                    50 * Math.cos(Math.toRadians(degree)),
                    50 * Math.sin(Math.toRadians(degree))
                )
            }

            val alpha = AnimationUtils.to1f0f(itemProgress)

            Box(
                modifier = Modifier
                    .size(size = 6.dp)
                    .graphicsLayer(translationX = x.toFloat(), translationY = y.toFloat())
                    .alpha(alpha)
                    .background(Color.White, CircleShape)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun FoldRectLoading() {
    Box(Modifier.rotate(45f)) {
        val transition = rememberInfiniteTransition()
        val pos = listOf(Pair(-1, -1), Pair(1, -1), Pair(1, 1), Pair(-1, 1))

        val rotationXEasing = remember {
            KeyframeEasing(
                listOf(0f, 0.1f, 0.25f, 0.75f, 0.9f, 1f),
                listOf(-180f, -180f, 0f, 0f, 0f, 0f)
            )
        }
        val rotationYEasing = remember {
            KeyframeEasing(
                listOf(0f, 0.1f, 0.25f, 0.75f, 0.9f, 1f),
                listOf(0f, 0f, 0f, 0f, 180f, 180f)
            )
        }

        for (i in 0 until 4) {
            val progress by transition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    tween(2400, easing = LinearEasing),
                    initialStartOffset = StartOffset(300 * i)
                )
            )

            val alphaAnimation by transition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 2400
                        0f.at(0)
                        0f.at((2400 * 0.1).toInt())
                        1f.at((2400 * 0.25).toInt())
                        1f.at((2400 * 0.75).toInt())
                        0f.at((2400 * 0.9).toInt())
                        0f.at((2400 * 1))
                    },
                    initialStartOffset = StartOffset(300 * i)
                )
            )

            Box(
                Modifier
                    .size(22.dp)
                    .offset(10.dp * pos[i].first, 10.dp * pos[i].second)
                    .rotate(90f * i)
                    .graphicsLayer(
                        rotationX = rotationXEasing.transform(progress),
                        rotationY = rotationYEasing.transform(progress),
                        transformOrigin = TransformOrigin(1f, 1f)
                    )
                    .alpha(alphaAnimation)
                    .background(Color.White)
            )
        }
    }
}




