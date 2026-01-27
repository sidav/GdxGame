package com.sidav.gdxgame.game.hexagonal_map

import kotlin.math.sqrt

class HexagonalMap(val width: Int, val height: Int) {
    val cells = Array(height) { r ->
        Array(width) { q ->
            Hex(r, q)
        }
    }

    fun allHexes(): Sequence<Hex> {
        return sequence {
            for (r in 0 until height)
                for (q in 0 until width)
                    yield(Hex(q, r))
        }
    }
}
