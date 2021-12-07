package com.doool.bewait.easing

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable

@Immutable
class KeyframeEasing(
    private val fractions: List<Float> = listOf(),
    private val values: List<Float> = listOf()
) : Easing {

    private fun findIndex(fraction: Float): Pair<Int, Int> {
        val (a, b) = fractions.withIndex().windowed(2).first {
            it[0].value <= fraction && fraction <= it[1].value
        }.map { it.index }
        return Pair(a, b)
    }

    override fun transform(fraction: Float): Float {
        val (a, b) = findIndex(fraction)
        val easingFraction = (fraction - fractions[a]) / (fractions[b] - fractions[a])
        return values[a] + (values[b] - values[a]) * easingFraction
    }

    override fun equals(other: Any?): Boolean {
        return other is KeyframeEasing &&
                fractions.toTypedArray().contentEquals(other.fractions.toTypedArray()) &&
                values.toTypedArray().contentEquals(other.values.toTypedArray())
    }

    override fun hashCode(): Int {
        return (fractions + values).hashCode()
    }
}