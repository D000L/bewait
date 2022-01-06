package com.doool.bewait

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.doool.bewait.loading.Loadings
import com.doool.bewait.ui.theme.BewaitTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BewaitTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Row(Modifier.fillMaxSize()) {
                        Loadings()

//                        Box(Modifier.fillMaxHeight().width(5.dp).background(Color.Gray))
//                        MotionLoadings(Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

