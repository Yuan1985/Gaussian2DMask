package com.joanmora.gaussianmask.utils

import android.graphics.Color


class GaussianMask(val size: Byte, val sigma: Float) {
    var mask: Array<FloatArray> = getGaussianWeightedMask(size, sigma)
    fun publicgetGaussianWeightedMask () = getGaussianWeightedMask(size, sigma)
    private fun getGaussianWeightedMask(size: Byte, sigma: Float): Array<FloatArray> {
        val maskFloat: Array<FloatArray> = Array((size * 2) + 1) { FloatArray((size * 2) + 1) }
        val maskFloat2 = maskFloat.clone()
        val e = 2.71828182845904 // Euler number
        var proportion = 0f
        for (a in -size..size) {
            for (b in -size..size) {
                val a2 = Math.pow(a.toDouble(), 2.0).toInt()
                val b2 = Math.pow(b.toDouble(), 2.0).toInt()
                val den = Math.pow((sigma.toDouble() * 2), 2.0).toFloat()
                val exp = (a2 + b2) / den
                val weight = Math.pow(e, (-exp).toDouble()).toFloat()
                if (a == -size && b == -size) {
                    proportion = 1.toFloat() / weight
                }
                val positionY = a + size
                val positionX = b + size
                maskFloat[positionX][positionY] = weight
            }
        }
        for (a in -size..size) {
            for (b in -size..size) {
                val positionX = a + size
                val positionY = b + size
                val intresult = maskFloat[positionX][positionY] * proportion * 10
                maskFloat2[positionX][positionY] = intresult / 10
            }
        }
        println(maskFloat2)
        return maskFloat2
    }
    fun getMinValue(mask: Array<FloatArray>): Float {
        var min: Float? = null
        for (x in mask.indices) {
            val actual = mask[x]
            for (y in actual.indices) {
                val value = mask[x][y]
                if (min == null) {
                    min = value
                } else {
                    if (value < min) {
                        min = value
                    }
                }
            }
        }
        return min ?: 0f
    }
    fun getMaxValue(mask: Array<FloatArray>): Float {
        var max: Float? = null
        for (x in mask.indices) {
            val actual = mask[x]
            for (y in actual.indices) {
                val value = mask[x][y]
                if (max == null) {
                    max = value
                } else {
                    if (value > max) {
                        max = value
                    }
                }
            }
        }
        return max ?: 99f
    }

}
