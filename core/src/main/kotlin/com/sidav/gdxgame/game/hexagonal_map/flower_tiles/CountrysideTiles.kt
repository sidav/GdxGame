package com.sidav.gdxgame.game.hexagonal_map.flower_tiles

import com.sidav.gdxgame.game.hexagonal_map.TerrainType.*

val CountrysideTiles: Array<FlowerTile> = arrayOf(
    // #1
    FlowerTile(
        SHRINE,
        listOf(
            LAKE,
            VILLAGE,
            PLAINS,
            PLAINS,
            FOREST,
            FOREST
        )
    ),
    // #2
    FlowerTile(
        MOUNTAIN,
        listOf(
            SHRINE,
            VILLAGE,
            PLAINS,
            MINE,
            PLAINS,
            MOUNTAIN
        )
    )
)
