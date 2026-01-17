package com.sidav.gdxgame.game.hexagonal_map.flower_tile.tiles

import com.sidav.gdxgame.game.hexagonal_map.TerrainFeature.GLADE
import com.sidav.gdxgame.game.hexagonal_map.TerrainFeature.KEEP
import com.sidav.gdxgame.game.hexagonal_map.TerrainFeature.MINE
import com.sidav.gdxgame.game.hexagonal_map.TerrainFeature.RAMPAGING_MONSTER_GREEN
import com.sidav.gdxgame.game.hexagonal_map.TerrainFeature.VILLAGE
import com.sidav.gdxgame.game.hexagonal_map.TerrainType.FOREST
import com.sidav.gdxgame.game.hexagonal_map.TerrainType.HILLS
import com.sidav.gdxgame.game.hexagonal_map.TerrainType.LAKE
import com.sidav.gdxgame.game.hexagonal_map.TerrainType.PLAINS
import com.sidav.gdxgame.game.hexagonal_map.flower_tile.FlowerTile


val COUNTRYSIDE_TILES: Array<FlowerTile> = arrayOf(
    // #1
    FlowerTile(
        H(FOREST, GLADE),
        listOf(
            H(FOREST, RAMPAGING_MONSTER_GREEN),
            H(LAKE),
            H(PLAINS, VILLAGE),
            H(PLAINS),
            H(PLAINS),
            H(FOREST),
        )
    ),
    // #2
    FlowerTile(
        H(HILLS),
        listOf(
            H(HILLS, RAMPAGING_MONSTER_GREEN),
            H(FOREST, GLADE),
            H(PLAINS, VILLAGE),
            H(PLAINS),
            H(HILLS, MINE),
            H(PLAINS)
        )
    ),
    // #3
    FlowerTile(
        H(FOREST),
        listOf(
            H(PLAINS),
            H(HILLS, KEEP),
            H(HILLS),
            H(HILLS, MINE),
            H(PLAINS, VILLAGE),
            H(PLAINS)
        )
    )
)
