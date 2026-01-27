package com.sidav.gdxgame.game.hexagonal_map

import kotlin.math.abs

@JvmInline
value class AxialCoords private constructor(private val packed: Long) {

    val q: Int
        get() = (packed shr 32).toInt()

    val r: Int
        get() = packed.toInt()

    constructor(q: Int, r: Int) : this(
        (q.toLong() shl 32) or (r.toLong() and 0xffffffffL)
    )

    companion object {
        val ZERO = AxialCoords(0, 0)
    }

    operator fun component1() = q
    operator fun component2() = r

    operator fun plus(other: AxialCoords): AxialCoords =
        AxialCoords(q + other.q, r + other.r)

    operator fun minus(other: AxialCoords): AxialCoords =
        AxialCoords(q - other.q, r - other.r)

    fun distanceTo(other: AxialCoords): Int {
        val dq = abs(q - other.q)
        val dr = abs(r - other.r)
        val ds =
            abs(q + r - other.q - other.r) // S is a cubic coordinate: s = -q-r. Here we're comparing them.
        return maxOf(dq, dr, ds)
    }

    fun pixelCoords(hexRadius: Float): Pair<Float, Float> {
        val x = hexRadius * (SQRT3 * q + SQRT3_HALF * r)
        val y = hexRadius * (THREE_HALVES * r)
        return x to y
    }

    fun neighbors(): Sequence<AxialCoords> {
        val self = this // inside sequence "this" would mean different thing
        return sequence {
            for (i in 0 until vectorsToNeighbors.size) {
                val newCoords = self + vectorsToNeighbors[i]
                yield(newCoords)
            }
        }
    }
}
