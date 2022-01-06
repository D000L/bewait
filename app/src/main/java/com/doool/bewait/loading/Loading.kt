package com.doool.bewait.loading

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import com.doool.bewait.AnimationUtils
import com.doool.bewait.easing.BounceEasing
import com.doool.bewait.easing.FastOutFastInEasing
import com.doool.bewait.easing.KeyframeEasing
import com.doool.bewait.easing.QuickEasing
import com.doool.gooey.GooeyBox
import com.doool.gooey.GooeyIntensity
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun Loadings(modifier: Modifier = Modifier) {
    LazyVerticalGrid(modifier = modifier, cells = GridCells.Adaptive(80.dp)) {
        items(13) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF2980b9)),
                contentAlignment = Alignment.Center
            ) {
                if (it != 0) Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = "#$it",
                    color = Color.White
                )
                when (it) {
                    0 -> Text(
                        text = "Loadings",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    1 -> Loading1()
                    2 -> Loading2()
                    3 -> Loading3()
                    4 -> Loading4(5, 450, 120)
                    5 -> Loading5()
                    6 -> Loading6()
                    7 -> Loading7()
                    8 -> Loading8(3, 1000, 300)
                    9 -> Loading9()
                    10 -> Loading10(600, 200)
                    11 -> Loading11()
                    12 -> Loading12()
                }
            }
        }

        items(6) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF2980b9)),
                contentAlignment = Alignment.Center
            ) {
                if (it != 0) Text(
                    modifier = Modifier.align(Alignment.TopStart),
                    text = "#$it",
                    color = Color.White
                )
                when (it) {
                    0 -> Text(
                        text = "Gooey Loadings",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    1 -> GooeyLoading()
                    2 -> GooeyLoading2()
                    3 -> GooeyLoading3()
                    4 -> GooeyLoading4()
                    5 -> GooeyLoading5()
                }
            }
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun Loading1() {
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
fun Loading2() {
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
                    .scale(scale / 2f + 0.5f)
                    .background(Color.White, CircleShape)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun Loading3() {
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
fun Loading4(count: Int = 5, duration: Int = 450, delay: Int = 120) {
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

            val scale = AnimationUtils.to0f1f0f(itemProgress, duration.toFloat())

            Box(
                modifier = Modifier
                    .size(width = 6.dp, height = 20.dp)
                    .scale(scaleX = 1f, scaleY = 1f + FastOutFastInEasing.transform(scale) * 2)
                    .background(Color.White)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun Loading5(modifier: Modifier = Modifier, size: Dp = 20.dp, distance: Dp = 40.dp) {

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

        val scale = AnimationUtils.to0f1f0f(progress) / 2f + 0.5f

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
fun Loading6() {
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
fun Loading7() {
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
                .offset(10.dp, -8.dp)
                .scale(AnimationUtils.to0f1f0f(progress2))
                .background(Color.White, shape = CircleShape)
        )
        Box(
            Modifier
                .size(30.dp)
                .offset(10.dp, 8.dp)
                .scale(AnimationUtils.to0f1f0f(progress3))
                .background(Color.White, shape = CircleShape)
        )
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun Loading8(count: Int = 7, duration: Int = 1000, delay: Int = 300) {

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

            val scale = AnimationUtils.to0f1f0f(itemProgress, duration.toFloat())

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .scale(scale)
                    .background(Color.White, shape = CircleShape)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun Loading9() {
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
            val scale = AnimationUtils.to0f1f0f(itemProgress)

            Box(
                modifier = Modifier
                    .size(size = 6.dp)
                    .graphicsLayer(translationX = x.toFloat(), translationY = y.toFloat())
                    .scale(scale)
                    .background(Color.White, CircleShape)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun Loading10(duration: Int = 600, delay: Int = 200) {
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

            val scale = AnimationUtils.to0f1f0f(itemProgress, duration.toFloat())

            Box(
                modifier = Modifier
                    .size(width = 10.dp, height = 10.dp)
                    .offset(10.dp * posList[i].first, 10.dp * posList[i].second)
                    .scale(1f - FastOutFastInEasing.transform(scale))
                    .background(Color.White)
            )
        }
    }
}

/*
 * @ design by [tobiasahlin] (https://tobiasahlin.com/spinkit/)
 */

@Composable
fun Loading11() {
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
fun Loading12() {
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

@Composable
fun GooeyLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing))
    )

    GooeyBox(contentAlignment = Alignment.Center) {
        for (i in 0 until 3) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .offset(x = 25.dp * i - 25.dp)
                    .gooey(Color(0xFF4DFF94), shape = CircleShape)
            )
        }
        Box(
            modifier = Modifier
                .size(8.dp)
                .offset(50.dp * AnimationUtils.to0f1f0f(progress) - 25.dp)
                .gooey(Color(0xFFC4FF70), CircleShape)
        )
    }
}

@Composable
fun GooeyLoading2() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing))
    )

    GooeyBox(
        contentAlignment = Alignment.Center,
        intensity = GooeyIntensity.Low
    ) {
        for (i in 0 until 12) {
            val (x, y) = remember(i) {
                val degree = i * 30.0
                Pair(
                    80 * Math.cos(Math.toRadians(degree)),
                    80 * Math.sin(Math.toRadians(degree))
                )
            }
            Box(
                modifier = Modifier
                    .size(size = 6.dp)
                    .graphicsLayer(translationX = x.toFloat(), translationY = y.toFloat())
                    .gooey(Color(0xFF4DFF94), CircleShape)
            )
        }

        val degree = progress * 360.0

        Box(
            Modifier
                .size(size = 6.dp)
                .graphicsLayer(
                    translationX = 80 * Math
                        .cos(Math.toRadians(degree))
                        .toFloat(),
                    translationY = 80 * Math
                        .sin(Math.toRadians(degree))
                        .toFloat()
                )
                .gooey(Color(0xFFC4FF70), CircleShape)
        )
    }
}

/*
 * @ design by [Vincent Durand] (https://codepen.io/onediv/pen/pjgNqJ)
 */
@Composable
fun GooeyLoading3() {
    var isOk by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit, block = {
        var time = System.currentTimeMillis()
        while (true) {
            withInfiniteAnimationFrameMillis {
                val current = System.currentTimeMillis()
                if (current - time > 1000) {
                    time = current
                    isOk = !isOk
                }
            }
        }
    })

    Box() {
        GooeyBox(
            contentAlignment = Alignment.Center,
            intensity = GooeyIntensity.High
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .offset(x = -21.dp)
                    .gooey(Color(0xFF243548), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .gooey(Color(0xFF243548), CircleShape)

            )
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .offset(x = 21.dp)
                    .gooey(Color(0xFF243548), CircleShape)
            )
        }
        GooeyBox(
            contentAlignment = Alignment.Center,
            intensity = GooeyIntensity.Medium
        ) {
            val progress by animateFloatAsState(
                targetValue = if (isOk) 0f else 1f, animationSpec = tween(
                    400,
                    easing = LinearEasing
                )
            )

            val color by animateColorAsState(
                targetValue = if (isOk) Color.White else Color(0xFF4DFF94)
            )

            if (0f < progress && progress < 1f) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .offset(x = 28.dp * QuickEasing.transform(progress) - 14.dp)
                        .gooey(color, CircleShape)
                )
            }

            Box(
                modifier = Modifier
                    .size(
                        width = 26.dp,
                        height = 26.dp - 6.dp * AnimationUtils.to0f1f0f(progress)
                    )
                    .offset(x = 41.dp * progress - 20.5.dp)
                    .gooey(color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(12.dp)
                        .alpha(AnimationUtils.to1f0f1f(QuickEasing.transform(progress))),
                    imageVector = if (isOk) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun GooeyLoading4() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2400, easing = LinearEasing))
    )

    GooeyBox(contentAlignment = Alignment.Center) {
        val degree = when (progress) {
            in 0f..0.25f -> progress * 4 * 180.0
            in 0.25f..0.5f -> (progress - 0.25f) * 4 * -180.0
            in 0.5f..0.75f -> (progress - 0.5f) * 4 * -180.0 + 180.0
            else -> (progress - 0.75f) * 4 * 180.0 + 180.0
        }

        val rotate = when (progress) {
            in 0f..0.25f -> 180f
            in 0.25f..0.5f -> -180f
            in 0.5f..0.75f -> -180f
            else -> 180f
        }

        val x = when (progress) {
            in 0f..0.25f -> 12.5.dp
            in 0.25f..0.5f -> -12.5.dp
            in 0.5f..0.75f -> -12.5.dp
            else -> 12.5.dp
        }
        for (i in 0 until 3) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .offset(x = 25.dp * i - 25.dp)
                    .gooey(Color(0xFF4DFF94), shape = CircleShape)
            )
        }
        Box(
            modifier = Modifier
                .rotate(rotate)
                .size(10.dp)
                .offset(
                    x = 12.5.dp * Math
                        .cos(Math.toRadians(degree))
                        .toFloat() + x,
                    y = 12.5.dp * Math
                        .sin(Math.toRadians(degree))
                        .toFloat()
                )
                .gooey(Color(0xFFC4FF70), CircleShape)
        )
    }
}

@Composable
fun GooeyLoading5() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween<Float>(
                durationMillis = 1200,
                easing = BounceEasing
            )
        )
    )

    GooeyBox(contentAlignment = Alignment.Center, intensity = GooeyIntensity.High) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .offset(y = 20.dp)
                .gooey(Color(0xFF4DFF94), shape = CircleShape)
        )

        Box(
            modifier = Modifier
                .size(16.dp)
                .offset(y = 40.dp * AnimationUtils.to0f1f0f(progress) + -20.dp)
                .gooey(Color(0xFF4DFF94), shape = CircleShape)
        )
    }
}