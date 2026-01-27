package com.sidav.gdxgame.game.hexagonal_map

import kotlin.math.abs

const val SQRT3 = 1.7320508f
const val SQRT3_HALF = SQRT3 * 0.5f
const val THREE_HALVES = 1.5f

fun axialDistance(q1: Int, r1: Int, q2: Int, r2: Int): Int {
    val dq = abs(q1 - q2)
    val dr = abs(r1 - r2)
    val ds = abs(q1 + r1 - q2 - r2) // S is a cubic coordinate: s = -q-r. Here we're comparing them.
    return maxOf(dq, dr, ds)
}

fun worldToAxial(x: Float, y: Float, radius: Float): AxialCoords {
    val (q, r) = roundAxial(
        (SQRT3 / 3f * x - 1f / 3f * y) / radius,
        (2f / 3f * y) / radius
    )
    return AxialCoords(q, r)
}

internal val vectorsToNeighbors = arrayOf(
    // Order is important! It's clockwise.
    AxialCoords(0, 1),
    AxialCoords(1, 0),
    AxialCoords(1, -1),
    AxialCoords(0, -1),
    AxialCoords(-1, 0),
    AxialCoords(-1, 1)
)

private fun roundAxial(qf: Float, rf: Float): Pair<Int, Int> {
    val xf = qf
    val zf = rf
    val yf = -xf - zf

    var rx = kotlin.math.round(xf)
    var ry = kotlin.math.round(yf)
    var rz = kotlin.math.round(zf)

    val dx = abs(rx - xf)
    val dy = abs(ry - yf)
    val dz = abs(rz - zf)

    if (dx > dy && dx > dz) {
        rx = -ry - rz
    } else if (dy > dz) {
        // Unneeded operation:
        // ry = -rx - rz
    } else {
        rz = -rx - ry
    }

    return Pair(rx.toInt(), rz.toInt())
}
