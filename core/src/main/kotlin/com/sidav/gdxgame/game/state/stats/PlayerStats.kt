package com.sidav.gdxgame.game.state.stats

enum class Stat {
    MOVEMENT, LEADERSHIP
}

class StatChange(
    val stat: Stat,
    val change: Int
)

class PlayerStats(
    var movement: Int = 0,
    var leadership: Int = 0
)
