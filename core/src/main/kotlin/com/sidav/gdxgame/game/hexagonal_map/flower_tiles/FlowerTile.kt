package com.sidav.gdxgame.game.hexagonal_map.flower_tiles

import com.sidav.gdxgame.game.hexagonal_map.TerrainType

/** It's a tile of 7 hexes of terrain: a center and 6 tiles around it.
 * @param terrainAround contains 6 terrains in clockwise order (top-right, right, bottom-right, bottom-left, left, top-left)
 **/
class FlowerTile(
    val center: TerrainType,
    val terrainAround: List<TerrainType>
) {
    init {
        require(terrainAround.size == 6) {
            "Wrong FlowerTile init, 6 around hexes awaited, got ${terrainAround.size}"
        }
    }
}
