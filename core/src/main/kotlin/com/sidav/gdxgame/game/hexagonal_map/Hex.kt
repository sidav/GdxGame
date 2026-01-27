package com.sidav.gdxgame.game.hexagonal_map

class Hex(val q: Int, val r: Int) {
    fun axialToPixel(radius: Float): Pair<Float, Float> {
        val x = radius * (SQRT3 * q + SQRT3_HALF * r)
        val y = radius * (THREE_HALVES * r)
        return x to y
    }
}
