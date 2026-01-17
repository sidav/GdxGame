package com.sidav.gdxgame.game.hexagonal_map.flower_tile

import com.sidav.gdxgame.game.hexagonal_map.TerrainFeature
import com.sidav.gdxgame.game.hexagonal_map.TerrainType

/** A hex of a flower tile. Not the hex that is already placed: this is sort of a template of what to place on map reveal */
class FlowerTileHex(val terrain: TerrainType, val feature: TerrainFeature? = null) {
    override fun toString(): String {
        feature?.let { return "${terrain.name} + ${feature.name}" }
        return terrain.name
    }
}

