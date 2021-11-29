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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import com.doool.bewait.ui.theme.BewaitTheme
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
  Column {
//    WaveLoading(7, 1000, 300)
//    WaveMotionLoading()
//
//    BoxLoading()
//    BoxMotionLoading()

    CircleSpreadMotionLoading()
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
public fun CircleSpreadMotionLoading() {
  val progress by rememberInfiniteTransition().animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing))
  )

  var baseConstraintSetStart = """
            {
                Variables: {
                  angle: { from: 0, step: 30 },
                  distance: 100,
                  mylist: { tag: 'circle' },
                   offset : { from: 1.0, step: 1.0}, 
                },
                Generate: {
                  mylist: {
                  circular: ['parent', 'angle', 100],
                   custom: {
                    offset : 'offset'
                  },
                    offset : 'offset'
//                        top: ['parent', 'top', 0],
//                       bottom: ['parent', 'bottom', 0],
//                           start: ['parent', 'start', 0],
//                       end: ['parent', 'end', 0],
//                        circular: ['parent', 'angle', 'distance'],
//                    circular: ['parent', 'angle', 'distance'],
//                    rotationZ: 'rotation'
                  }
                }
            }
        """

  var baseConstraintSetEnd = """
            {
                Variables: {
//                  angle: { from: 0, step: 20 },
//                  rotation: { from: 200, step: -260 },
                  angle: { from: 0, step: 30 },
                  offset : { from: 1.0, step: 1.0}, 
                  distance: 100,
                  mylist: { tag: 'circle' },
                },
                Generate: {
                  mylist: {
                  circular: ['parent', 'angle', 100],
                  custom: {
                    offset : 'offset'
                  },
                  offset : 'offset'
//                      rotationZ: 'angle',
//                         top: ['parent', 'top', 0],
//                       bottom: ['parent', 'bottom', 0],
//                           start: ['parent', 'start', 0],
//                       end: ['parent', 'end', 0]
                  }
                }
            }
        """

  var cs1 = ConstraintSet(baseConstraintSetStart)
  var cs2 = ConstraintSet(baseConstraintSetEnd)


  Column {
    MotionLayout(
      cs1, cs2,
      progress = progress,
      modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
      debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL)
    ) {
      val colors = arrayListOf(Color.Red, Color.Green, Color.Blue, Color.Cyan, Color.Yellow)
      for (i in 1..12) {
        val properties by motionProperties(id = "circle")
        val offset = properties.float("offset")


        Box(
          modifier = Modifier
            .layoutId("h$i", properties.id())
            .size(30.dp)
            .scale(offset)
            .background(colors[i % colors.size])
        )
      }
    }
  }
}