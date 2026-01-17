package com.sidav.gdxgame.game.hexagonal_map.flower_tile

/** It's a tile of 7 hexes of terrain: a center and 6 tiles around it.
 * @param terrainAround contains 6 terrains in clockwise order as defined in [com.sidav.gdxgame.game.hexagonal_map.vectorsToNeighbors]
 **/
class FlowerTile(
    val center: FlowerTileHex, val terrainAround: List<FlowerTileHex>
) {
    init {
        require(terrainAround.size == 6) {
            "Wrong FlowerTile init, 6 around hexes awaited, got ${terrainAround.size}"
        }
    }
}
