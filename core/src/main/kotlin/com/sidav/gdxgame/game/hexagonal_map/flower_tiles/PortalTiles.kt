package com.sidav.gdxgame.game.hexagonal_map.flower_tiles

import com.sidav.gdxgame.game.hexagonal_map.TerrainType.*

val PORTAL_TILES: Array<FlowerTile> = arrayOf(
    FlowerTile(
        PORTAL,
        listOf(
            PLAINS,
            PLAINS,
            OCEAN,
            OCEAN,
            OCEAN,
            PLAINS,
        )
    )
)

