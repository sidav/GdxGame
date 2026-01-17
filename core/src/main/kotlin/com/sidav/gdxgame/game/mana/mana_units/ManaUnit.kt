package com.sidav.gdxgame.game.mana.mana_units

import com.sidav.gdxgame.game.mana.ManaColor

// Describes anything that can pay for mana actions
interface ManaUnit {
    val color: ManaColor
    fun isOfColor(which: ManaColor): Boolean =
        which == color
}
