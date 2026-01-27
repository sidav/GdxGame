package com.sidav.gdxgame.game.hexagonal_map

enum class TerrainType(
    val dayMoveCost: Int = 0,
    val nightMoveCost: Int = dayMoveCost,
    val passable: Boolean = true,
) {
    // Hex was not placed yet
    UNREVEALED(passable = false),
    PORTAL(passable = false),
    OCEAN(passable = false),
    LAKE(passable = false),

    PLAINS(2),
    SWAMP(1),
    FOREST(3, 5),
    VILLAGE(1),
    MOUNTAIN(1),
    MINE(1),
    SHRINE(2, 4);

    init {
        require(
            (!passable) || (dayMoveCost > 0 && nightMoveCost > 0)
        ) {
            "Passable terrain must have positive move costs"
        }
    }
}
