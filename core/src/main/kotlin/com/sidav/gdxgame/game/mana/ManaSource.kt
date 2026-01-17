package com.sidav.gdxgame.game.mana

// Describes anything that can pay for mana actions
interface ManaSource {
    fun isOfColor(color: Mana): Boolean
}
