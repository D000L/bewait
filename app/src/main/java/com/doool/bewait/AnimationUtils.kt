package com.doool.bewait

object AnimationUtils {

    fun to1f0f(progress: Float, duration: Float = 1f) = when (progress) {
        in 0f..duration -> 1f - progress / duration
        else -> 0f
    }

    fun to1f0f1f(progress: Float, duration: Float = 1f) = when (progress) {
        in 0f..duration / 2f -> 1f - (progress / duration) * 2
        in duration / 2f..duration -> ((progress / duration) - 0.5f) * 2
        else -> 0f
    }

    fun to0f1f0f(progress: Float, duration: Float = 1f) = when (progress) {
        in 0f..duration / 2f -> progress / duration
        in duration / 2f..duration -> 1f - (progress / duration)
        else -> 0f
    } * 2
}