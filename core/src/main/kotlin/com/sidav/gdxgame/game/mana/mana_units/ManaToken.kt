package com.sidav.gdxgame.game.mana.mana_units

import com.sidav.gdxgame.game.mana.ManaColor

/** This is a mana token, a resource which don't persist between turns. */
class ManaToken(override val color: ManaColor): ManaUnit
