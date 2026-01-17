package com.sidav.gdxgame.game.hexagonal_map.flower_tile.tiles

import com.sidav.gdxgame.game.hexagonal_map.TerrainType.*
import com.sidav.gdxgame.game.hexagonal_map.flower_tile.FlowerTile
import com.sidav.gdxgame.game.hexagonal_map.flower_tile.FlowerTileHex

internal typealias H = FlowerTileHex // Shorthand

val PORTAL_TILES: Array<FlowerTile> = arrayOf(
    FlowerTile(
        H(PORTAL),
        listOf(
            H(PLAINS),
            H(PLAINS),
            H(PLAINS),
            H(OCEAN),
            H(OCEAN),
            H(OCEAN),
        )
    )
)

