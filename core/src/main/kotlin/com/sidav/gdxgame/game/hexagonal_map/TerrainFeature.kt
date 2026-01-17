package com.sidav.gdxgame.game.hexagonal_map

/**What's in the tile besides the terrain */
enum class TerrainFeature {
    RAMPAGING_MONSTER_GREEN,
    KEEP,
    VILLAGE,
    MINE,
    GLADE;

    val isRampaging: Boolean
        get() = this == RAMPAGING_MONSTER_GREEN
}
