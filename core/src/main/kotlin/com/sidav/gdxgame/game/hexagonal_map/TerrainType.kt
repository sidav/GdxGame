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
    MOUNTAIN(passable = false),

    PLAINS(2),
    HILLS(3),
    FOREST(3, 5),
    DESERT(5, 3),
    ROCKS(5), // Or is it ruins? What the heck is drawn on the actual tiles?
    SWAMP(5),
    CITY(2);

    init {
        require(
            (!passable) || (dayMoveCost > 0 && nightMoveCost > 0)
        ) {
            "Passable terrain must have positive move costs"
        }
    }
}
