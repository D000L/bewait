package com.doool.bewait

object AnimationUtils {

    fun to1f0f(progress: Float, duration: Float = 1f) = when (progress) {
        in 0f..duration -> 1f - progress / duration
        else -> 0f
    }

    fun to1f0f1f(progress: Float, duration: Float = 1f) = when (progress) {
        in 0f..duration / 2f -> progress / duration
        in duration / 2f..duration -> 1f - (progress / duration)
        else -> 0f
    }
}