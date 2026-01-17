package com.sidav.gdxgame.game.state.stats

enum class Stat {
    MOVEMENT, INFLUENCE
}

class StatChange(
    val stat: Stat,
    val change: Int
)

class PlayerStats(
    var movement: Int = 0,
    var influence: Int = 0
) {
    fun applyStatChange(statChange: StatChange) {
        when (statChange.stat) {
            Stat.MOVEMENT -> movement += statChange.change
            Stat.INFLUENCE -> influence += statChange.change
        }
    }
}
