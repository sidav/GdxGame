package com.sidav.gdxgame.game.hexagonal_map

/**
 * Q and R are axial coordinates.
 */
class Hex(val axialCoords: AxialCoords) {
    constructor(q: Int, r: Int) : this(AxialCoords(q, r))
    var explored: Boolean = false
    var terrainHere: TerrainType = TerrainType.UNREVEALED
}
