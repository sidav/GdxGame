package com.sidav.gdxgame.game.hexagonal_map

import com.sidav.gdxgame.game.hexagonal_map.flower_tiles.FlowerTile

class HexagonalMap(val width: Int, val height: Int) {
    private val grid = Array(height) { row ->
        Array(width) { col ->
            val ax = offsetToAxial(col, row)
            Hex(ax)
        }
    }

    fun getHex(ac: AxialCoords): Hex? {
        val (offsetX, offsetY) = axialToOffset(ac)
        return grid.getOrNull(offsetX)?.getOrNull(offsetY)
    }

    fun allHexes(): Sequence<Hex> {
        return sequence {
            for (row in 0 until height)
                for (col in 0 until width)
                    yield(grid[row][col])
        }
    }

    fun allNeighboringHexes(ac: AxialCoords): Sequence<Hex?> = ac.neighbors().map { getHex(it) }

    private fun offsetToAxial(col: Int, row: Int): AxialCoords = AxialCoords(
        col - (row + (row and 1)) / 2,
        row
    )

    // Needed only for debug? Should be private - that's for certain.
    fun axialToOffset(ac: AxialCoords): Pair<Int, Int> = Pair(
        ac.q + (ac.r + (ac.r and 1)) / 2,
        ac.r
    )

    fun placeFlowerTileAt(flowerTile: FlowerTile, center: AxialCoords) {
        for ((i,  n) in vectorsToNeighbors.withIndex()) {
            getHex(center + n)?.apply {
                terrainHere = flowerTile.terrainAround[i]
            }
        }
    }
}
