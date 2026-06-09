package com.sidav.gdxgame.game.state.combat

// Represents attack/defense which is given by deeds and may be applied to enemies
open class CombatToken {}

class BlockToken(val defenseValue: Int, val properties: List<DefenseProperty> = listOf()) :
    CombatToken() {
    enum class DefenseProperty {
        FIRE, ICE
    }
}

class AttackToken(val attackValue: Int, val properties: List<AttackProperty> = listOf()) :
    CombatToken() {
    enum class AttackProperty {
        RANGED, SIEGE, FIRE, ICE
    }
}
