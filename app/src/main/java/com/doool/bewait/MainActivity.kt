package com.doool.bewait

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.doool.bewait.ui.theme.BewaitTheme
import java.lang.Math.abs
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BewaitTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Loadings()
                }
            }
        }
    }
}

@Composable
fun Loadings() {
    Column(Modifier.fillMaxSize().background(Color(0xffd35400))) {
//    WaveLoading(7, 1000, 300)
//    WaveMotionLoading()
//
//    BoxLoading()
//    BoxMotionLoading()

//        BoxBoundLoading(5, 450, 120)
//        BoxBounceMotionLoading()

//        GridCardLoading(600, 200)
//        GridCardMotionLoading()
//
//        BoxRotationLoading()
//        BoxRotationMotionLoading(
//            Modifier
//                .fillMaxSize()
//                .background(Color.White)
//        )

        CircleRotationMotionLoading(
            Modifier
                .fillMaxSize()
                .background(Color(0xffd35400))
        )
    }
}

@Composable
fun BoxRotationLoading(distance: Int = 120) {

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
        Modifier
            .height(60.dp)
            .padding(horizontal = 10.dp),
    ) {

        val scale =
            if (0 <= progress && progress <= 0.5f) 1f - progress
            else if (0.5f < progress && progress <= 1f) progress
            else 0f

        val position1 =
            if (0 <= progress && progress <= 0.5f) Offset(progress * 2 * distance, 0f)
            else if (0.5f < progress && progress <= 1f) Offset(
                distance.toFloat(),
                (progress - 0.5f) * 2 * distance
            )
            else Offset(0f, 0f)

        Box(
            modifier = Modifier
                .size(20.dp)
                .graphicsLayer(
                    translationX = position1.x,
                    translationY = position1.y,
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = -180 * progress
                )
                .background(Color.Blue)
        )

        val position2 =
            if (0 <= progress && progress <= 0.5f) Offset(
                distance - progress * 2 * distance,
                distance.toFloat()
            )
            else if (0.5f < progress && progress <= 1f) Offset(
                0f,
                distance - (progress - 0.5f) * 2 * distance
            )
            else Offset(0f, 0f)

        Box(
            modifier = Modifier
                .size(20.dp)
                .graphicsLayer(
                    translationX = position2.x,
                    translationY = position2.y,
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = -180 * progress
                )
                .background(Color.Blue)
        )
    }
}

@Composable
fun BoxRotationMotionLoading(modifier: Modifier = Modifier, distance: Int = 120) {
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

    val distance = distance / 2

    MotionLayout(
        modifier = modifier,
        motionScene = MotionScene(
            """{
                ConstraintSets: {   // all ConstraintSets
                  start:   {
                      h1: { center: 'parent' },
                      h2: { center: 'parent' },
                  },
                  end: {
                      h1: { center: 'parent' },
                      h2: { center: 'parent' },
                  }
                },
                Transitions: {           
                  default: {            
                    from: 'start',      
                    to: 'end',          
                    KeyFrames: {        
                      KeyAttributes: [  
                        {target: ['h1'], 
                         frames: [0,50,100], 
                        translationX : [${-distance},$distance,$distance], 
                        translationY : [${-distance},${-distance},$distance]},
                        
                         {target: ['h2'], 
                         frames: [0,50,100], 
                        translationX : [$distance,${-distance},${-distance}], 
                        translationY : [$distance,$distance,${-distance}]},
                        
                        {target: ['h1','h2'], 
                        frames: [0,50,100], 
                        rotationZ : [0,-90,-180], 
                        scaleX : [ 1, 0.5, 1],
                        scaleY : [ 1, 0.5, 1]
                        }
                      ]
                    }
                  }
                }
            }"""
        ),
        debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL),
        progress = progress
    ) {
        Box(
            modifier = Modifier
                .layoutId("h1", "h1")
                .size(20.dp)
                .background(Color.Green)
        )
        Box(
            modifier = Modifier
                .layoutId("h2", "h2")
                .size(20.dp)
                .background(Color.Green)
        )
    }
}

@Composable
fun CircleRotationMotionLoading(modifier: Modifier = Modifier) {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                1200,
                easing = LinearEasing
            )
        )
    )

    MotionLayout(
        modifier = Modifier.rotate(40f).requiredSize(25.dp),
        motionScene = MotionScene(
            """{
                ConstraintSets: {   // all ConstraintSets
                  start:   {
                      h1: { top : ['parent','top',0], centerHorizontally : 'parent' },
                    h2: { bottom : ['parent','bottom',0], centerHorizontally : 'parent' },
                  },
                  end: {  
                      h1: { bottom : ['parent','bottom',0], centerHorizontally : 'parent' },
                     h2: { top : ['parent','top',0], centerHorizontally : 'parent'  },
                  }
                },
                Transitions: {           
                  default: {            
                    from: 'start',      
                    to: 'end',
                    KeyFrames: {        
                     KeyPositions: [
                            {
                               target: ['h1'], 
                               frames: [12,25,50,75,88],
                               percentX: [ 0.62,0.83,1,0.83,0.62],
                               percentY: [ 0.01,0.12,0.5,0.88,0.99]
                            },
                              {
                               target: ['h2'], 
                               frames: [12,25,50,75,88],
                               percentX: [0.38,0.17,0,0.17,0.38],
                               percentY: [0.99,0.88,0.5,0.12,0.01]
                            }
                        ],
                        
                      KeyAttributes: [
                        {target: ['h1'], 
                        frames: [0,20,100],
                        scaleX  : [0, 0,  1],
                        scaleY  : [0, 0,  1]
                        },
                        {target: ['h2'], 
                        frames: [0,20,100],
                        scaleX  : [1, 0.5, 0],
                        scaleY  : [1, 0.5, 0],
                        },
                      ],

                    }
                  }
                }
            }"""
        ),
//        debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL),
        progress = progress
    ) {
        Box(
            modifier = Modifier
                .layoutId("h1", "h1")
                .size(15.dp)
                .background(Color.White, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .layoutId("h2", "h2")
                .size(15.dp)
                .background(Color.White, shape = CircleShape)
        )

//        Box(
//            modifier = Modifier
//                .layoutId("h2", "h2")
//                .size(20.dp)
//                .background(Color.Green, shape = CircleShape)
//        )
    }
}

@Composable
fun WaveLoading(count: Int, duration: Int, delay: Int) {

    val totalDuration = delay * count + duration

    val progress by rememberInfiniteTransition().animateValue(
        initialValue = 0,
        targetValue = totalDuration,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(tween(totalDuration, easing = LinearEasing))
    )


    Row() {
        for (i in 0 until count) {
            val start = delay * i
            val progress = progress - start
            val scale =
                if (0 <= progress && progress <= duration.toFloat() / 2) progress.toFloat() / duration.toFloat()
                else if (duration.toFloat() / 2 < progress && progress <= duration) 1f - (progress.toFloat() / duration.toFloat())
                else 0f

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
public fun WaveMotionLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing))
    )

    MotionLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White),
        motionScene = MotionScene(
            """{
                ConstraintSets: {   // all ConstraintSets
                  start:   {
                      h1: { },
                      h2: { translationX: 80 },
                      h3: { translationX: 160 },
                  },
                  end: {
                      h1: {},
                      h2: { translationX: 80 },
                      h3: { translationX: 160 },
                  }
                },
                Transitions: {           
                  default: {            
                    from: 'start',      
                    to: 'end',          
                    KeyFrames: {        
                      KeyAttributes: [  
                        {target: ['h1'], frames: [0,17,34,51,68,85,100], scaleX: [0,0.5,1,0.5,0,0,0], scaleY: [0,0.5,1,0.5,0,0,0]},
                        {target: ['h2'], frames: [0,17,34,51,68,85,100], scaleX: [0,0,0.5,1,0.5,0,0], scaleY: [0,0,0.5,1,0.5,0,0]},
                        {target: ['h3'], frames: [0,17,34,51,68,85,100], scaleX: [0,0,0,0.5,1,0.5,0], scaleY: [0,0,0,0.5,1,0.5,0]},
                      ]
                    }
                  }
                }
            }"""
        ),
        progress = progress
    ) {
        Box(
            modifier = Modifier
                .layoutId("h1", "h1")
                .size(20.dp)
                .background(Color.Red, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .layoutId("h2", "h2")
                .size(20.dp)
                .background(Color.Green, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .layoutId("h3", "h3")
                .size(20.dp)
                .background(Color.Blue, shape = CircleShape)
        )
    }
}

@Composable
fun BoxLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing))
    )

    val rotateX = if (0f < progress && progress <= 0.5f) -progress * 2f * 180 else 0f
    val rotateY = if (0.5f < progress && progress <= 1f) (progress - 0.5f) * 2f * 180 else 0f

    Box(
        modifier = Modifier
            .layoutId("box", "box")
            .size(40.dp)
            .graphicsLayer(rotationX = rotateX, rotationY = rotateY)
            .background(Color.Red)
    )
}

@Composable
public fun BoxMotionLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing))
    )

    MotionLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White),
        motionScene = MotionScene(
            """{
                ConstraintSets: {   // all ConstraintSets
                  start: {box: { }},
                  end: {box: { }}
                },
                Transitions: {           
                  default: {            
                    from: 'start',      
                    to: 'end',     
                    KeyFrames: {
                     KeyAttributes: [
                      {
                          target: ['box'],
                          frames: [0, 25, 50, 75, 100],
                           rotationX : [0, -90, 0, 0, 0],
                            rotationY: [0, 0, 0, 90, 0], 
                          }
                      ],
                    },
                  }
                }
            }"""
        ),
        progress = progress
    ) {
        Box(
            modifier = Modifier
                .layoutId("box", "box")
                .size(40.dp)
                .background(Color.Red)
        )
    }
}

@Composable
fun BoxBoundLoading(count: Int, duration: Int, delay: Int) {
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
fun GridCardLoading(duration: Int, delay: Int) {
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
fun GridCardMotionLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                1500,
                easing = CubicBezierEasing(0.42f, 0f, 0.58f, 1.0f)
            )
        )
    )

    Column {
        MotionLayout(
            motionScene = MotionScene(
                """{
                ConstraintSets: {   // all ConstraintSets
                  start: {
                      h1: { width : 10, height: 10, },
                      h2: { width : 10, height: 10, start: ['h1', 'end', 0], bottom: ['h1', 'bottom', 0] },
                      h3: { width : 10, height: 10, start: ['h2', 'end', 0], bottom: ['h2', 'bottom', 0] },
                      h4: { width : 10, height: 10, start: ['h1', 'start', 0], top: ['h1', 'bottom', 0] },
                      h5: { width : 10, height: 10, start: ['h2', 'start', 0], top: ['h2', 'bottom', 0] },
                      h6: { width : 10, height: 10, start: ['h3', 'start', 0], top: ['h3', 'bottom', 0] },
                      h7: { width : 10, height: 10, start: ['h4', 'start', 0], top: ['h4', 'bottom', 0] },
                      h8: { width : 10, height: 10, start: ['h5', 'start', 0], top: ['h5', 'bottom', 0] },
                      h9: { width : 10, height: 10, start: ['h6', 'start', 0], top: ['h6', 'bottom', 0] },
                  },
                  end: {
                      h1: { width : 10, height: 10, },
                      h2: { width : 10, height: 10, start: ['h1', 'end', 0], bottom: ['h1', 'bottom', 0] },
                      h3: { width : 10, height: 10, start: ['h2', 'end', 0], bottom: ['h2', 'bottom', 0] },
                      h4: { width : 10, height: 10, start: ['h1', 'start', 0], top: ['h1', 'bottom', 0] },
                      h5: { width : 10, height: 10, start: ['h2', 'start', 0], top: ['h2', 'bottom', 0] },
                      h6: { width : 10, height: 10, start: ['h3', 'start', 0], top: ['h3', 'bottom', 0] },
                      h7: { width : 10, height: 10, start: ['h4', 'start', 0], top: ['h4', 'bottom', 0] },
                      h8: { width : 10, height: 10, start: ['h5', 'start', 0], top: ['h5', 'bottom', 0] },
                      h9: { width : 10, height: 10, start: ['h6', 'start', 0], top: ['h6', 'bottom', 0] },
                  }
                },
               Transitions: {           
                  default: {            
                    from: 'start',      
                    to: 'end',          
                    KeyFrames: {        
                      KeyAttributes: [  
                        {target: ['h1'], frames: [0,10,20,30,40,50,60,70,80,90,100], scaleX: [1,0.5,0.1,0,0.1,0.5,1,1,1,1,1], scaleY: [1,0.5,0.1,0,0.1,0.5,1,1,1,1,1]},    
                        {target: ['h2'], frames: [0,10,20,30,40,50,60,70,80,90,100], scaleX: [1,1,0.5,0.1,0,0.1,0.5,1,1,1,1], scaleY: [1,1,0.5,0.1,0,0.1,0.5,1,1,1,1]},
                        {target: ['h4'], frames: [0,10,20,30,40,50,60,70,80,90,100], scaleX: [1,1,0.5,0.1,0,0.1,0.5,1,1,1,1], scaleY: [1,1,0.5,0.1,0,0.1,0.5,1,1,1,1]},
                        {target: ['h3'], frames: [0,10,20,30,40,50,60,70,80,90,100], scaleX: [1,1,1,0.5,0.1,0,0.1,0.5,1,1,1], scaleY: [1,1,1,0.5,0.1,0,0.1,0.5,1,1,1]},
                        {target: ['h5'], frames: [0,10,20,30,40,50,60,70,80,90,100], scaleX: [1,1,1,0.5,0.1,0,0.1,0.5,1,1,1], scaleY: [1,1,1,0.5,0.1,0,0.1,0.5,1,1,1]},
                        {target: ['h7'], frames: [0,10,20,30,40,50,60,70,80,90,100], scaleX: [1,1,1,0.5,0.1,0,0.1,0.5,1,1,1], scaleY: [1,1,1,0.5,0.1,0,0.1,0.5,1,1,1]},
                        {target: ['h6'], frames: [0,10,20,30,40,50,60,70,80,90,100], scaleX: [1,1,1,1,0.5,0.1,0,0.1,0.5,1,1], scaleY: [1,1,1,1,0.5,0.1,0,0.1,0.5,1,1]},
                        {target: ['h8'], frames: [0,10,20,30,40,50,60,70,80,90,100], scaleX: [1,1,1,1,0.5,0.1,0,0.1,0.5,1,1], scaleY: [1,1,1,1,0.5,0.1,0,0.1,0.5,1,1]},
                        {target: ['h9'], frames: [0,10,20,30,40,50,60,70,80,90,100], scaleX: [1,1,1,1,1,0.5,0.1,0,0.1,0.5,1], scaleY: [1,1,1,1,1,0.5,0.1,0,0.1,0.5,1]},
                      ]
                    }
                  }
                }
            }"""
            ),
            progress = progress,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val colors = arrayListOf(Color.Black, Color.Green, Color.Blue, Color.Cyan)

            for (i in 1..9) {
                Box(
                    modifier = Modifier
                        .layoutId("h$i", "h$i")
                        .background(colors[i % colors.size])
                )
            }
        }
    }
}

@Composable
fun BoxBounceMotionLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(
                1500,
                easing = CubicBezierEasing(0.42f, 0f, 0.58f, 1.0f)
            )
        )
    )

    Column {
        MotionLayout(
            motionScene = MotionScene(
                """{
                ConstraintSets: {   // all ConstraintSets
                  start: {
                      Variables: {
                        distance: { from :  0, step : 40 } ,
                        mylist: { tag: 'box' },
                      },
                      Generate: { mylist: { width : 10, height : 20, translationX : 'distance', translationY : 60 } },
                  },
                  end:  {
                        Variables: {
                         distance: { from :  0, step : 40 } ,
                          mylist: { tag: 'box' },
                        },
                        Generate: { mylist: { width : 10, height : 20, translationX : 'distance', translationY : 60 } },
                    }
                },
               Transitions: {           
                  default: {            
                    from: 'start',      
                    to: 'end',          
                    KeyFrames: {        
                      KeyAttributes: [  
                          {target: ['h1'], frames: [0,17,34,51,68,85,100], scaleY: [1,3,1,1,1,1,1]},    
                          {target: ['h2'], frames: [0,17,34,51,68,85,100], scaleY: [1,1,3,1,1,1,1]},    
                          {target: ['h3'], frames: [0,17,34,51,68,85,100], scaleY: [1,1,1,3,1,1,1]},    
                          {target: ['h4'], frames: [0,17,34,51,68,85,100], scaleY: [1,1,1,1,3,1,1]},    
                          {target: ['h5'], frames: [0,17,34,51,68,85,100], scaleY: [1,1,1,1,1,3,1]}
                      ]
                    }
                  }
                }
            }"""
            ),
            progress = progress,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL)
        ) {
            val colors = arrayListOf(Color.Red, Color.Green, Color.Blue, Color.Cyan, Color.Yellow)

            for (i in 1..5) {
                Box(
                    modifier = Modifier
                        .layoutId("h$i", "box")
                        .background(colors[i % colors.size])
                )
            }
        }
    }
}

private fun getString(number: Int): String {
    val list = MutableList(20, { 0.0f })

    for (i in -2 until 2) {
        if ((i + number) in 0 until 12) {
            list[i + number] = (1f - abs(i) * 0.2f)
        }
        if ((i + number + 12) in 0 until 12) {
            list[i + number + 12] = (1f - abs(i) * 0.2f)
        }
    }


    return """
  {
    target: ['h$number'], 
    frames: [0, 7, 14, 21, 28, 35, 42, 49, 56, 63, 70, 77, 84, 91, 100],
    scaleX: $list,
    scaleY: $list
  }
  """
}

@Composable
public fun CircleSpreadMotionLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing))
    )

    val transitions = (1..12).map { getString(it) }.joinToString(",")

    Column {
        MotionLayout(
            motionScene = MotionScene(
                """{
                ConstraintSets: {   // all ConstraintSets
                  start: {
                      Variables: {
                        angle: { from: 0, step: 30 },
                        distance: 100,
                        mylist: { tag: 'circle' },
                      },
                      Generate: { mylist: { circular: ['parent', 'angle', 100] } },
                  },
                  end:  {
                        Variables: {
                          angle: { from: 0, step: 30 },
                          distance: 100,
                          mylist: { tag: 'circle' },
                        },
                        Generate: { mylist: { circular: ['parent', 'angle', 100] } },
                    }
                },
               Transitions: {           
                  default: {            
                    from: 'start',      
                    to: 'end',          
                    KeyFrames: {        
                      KeyAttributes: [  
                        $transitions                                                          
                      ]
                    }
                  }
                }
            }"""
            ),
            progress = progress,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL)
        ) {
            val colors = arrayListOf(Color.Red, Color.Green, Color.Blue, Color.Cyan, Color.Yellow)

            for (i in 1..12) {
                Box(
                    modifier = Modifier
                        .layoutId("h$i", "circle")
                        .size(30.dp)
                        .background(colors[i % colors.size])
                )
            }
        }
    }
}