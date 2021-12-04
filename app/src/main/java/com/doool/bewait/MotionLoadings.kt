package com.doool.bewait

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import java.util.*

@Composable
fun MotionLoadings(modifier: Modifier = Modifier) {
    Column(modifier) {
        BoxRotationMotionLoading()
        WaveMotionLoading()
        BoxMotionLoading()
        BoxBounceMotionLoading()
        GridCardMotionLoading()
        CircleSpreadMotionLoading()
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
        modifier = Modifier
            .rotate(40f)
            .requiredSize(25.dp),
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
    val frame = mutableListOf<Int>()
    val list = mutableListOf<Float>()
    val term = 8

    if(number<=12) {
        if(number>9){
            for (j in -4..4) {
                val number = number - 12
                val f = number * term + j * 6
                if (0 <= f && f <= 100) {
                    frame.add(f)
                    list.add(1f - Math.abs(j * 0.25f))
                }
            }
        }
        for (j in -4..4) {
            val number = number
            val f = number * term + j * 5
            if (0 <= f && f <= 100) {
                frame.add(f)
                list.add(1f - Math.abs(j * 0.25f))
            }
        }

    }else {
        for (j in -4..4) {
            val number = number
            val f = number * term + j * 6
            if (0 <= f && f <= 100) {
                frame.add(f)
                list.add(1f - Math.abs(j * 0.25f))
            }
        }
        if(frame.contains(100) == false){
            frame.add(100)
            list.add(list.lastOrNull() ?: 0f)
        }else{
            val index = frame.indexOf(100)
            list[index] = 0f
        }
        if(frame.contains(0) == false){
            frame.add(0)
            list.add(list.first())
        }
    }

    if(number <= 12){

        if(frame.contains(100) == false){
            frame.add(100)
            list.add(list.lastOrNull() ?: 0f)
        }else{
            val index = frame.indexOf(100)
            list[index] = 0f
        }
        if(frame.contains(0) == false){
            frame.add(0)
            list.add(list.first())
        }
    }




    val text = """
  {
    target: ['h$number'], 
    frames: $frame,
    scaleX: $list,
    scaleY: $list
  }
  """
    Log.d("ASdgasdgasdg",text)
    return text
}

@Composable
public fun CircleSpreadMotionLoading() {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing))
    )

    val transitions = remember { (1..20).map { getString(it) }.joinToString(",") }

    Column {
        MotionLayout(
            motionScene = MotionScene(
                """{
                ConstraintSets: {   // all ConstraintSets
                  start: {
                      Variables: {
                        angle: { from: 0, step: 30 },
                        distance: 20,
                        mylist: { tag: 'circle' },
                      },
                      Generate: { mylist: { circular: ['parent', 'angle', 'distance'] } },
                  },
                  end:  {
                        Variables: {
                          angle: { from: 0, step: 30 },
                          distance: 20,
                          mylist: { tag: 'circle' },
                        },
                        Generate: { mylist: { circular: ['parent', 'angle','distance'] } },
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
//            debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL)
        ) {
            val colors = arrayListOf(Color.Red, Color.Green, Color.Blue, Color.Cyan, Color.Yellow)

            for (i in 1..20) {
                Box(
                    modifier = Modifier
                        .layoutId("h$i", "circle")
                        .size(10.dp)
                        .background(colors[i % colors.size], shape = CircleShape)
                )
            }
        }
    }
}