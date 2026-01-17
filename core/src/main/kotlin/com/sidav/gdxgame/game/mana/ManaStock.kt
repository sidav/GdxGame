package com.sidav.gdxgame.game.mana

import com.sidav.gdxgame.game.mana.mana_units.ManaCrystal
import com.sidav.gdxgame.game.mana.mana_units.ManaDie
import com.sidav.gdxgame.game.mana.mana_units.ManaToken
import com.sidav.gdxgame.game.mana.mana_units.ManaUnit

class ManaStock {
    val ambientManaDice = MutableList(3) { ManaDie() }
    var diceUsedThisTurn = 0
    val manaCrystals = mutableListOf<ManaCrystal>()
    val manaTokens = mutableListOf<ManaToken>()

    fun rollManaDice() {
        ambientManaDice.forEach { it.roll() }
    }

    fun addCrystal(color: ManaColor) {
        manaCrystals.add(ManaCrystal(color))
    }

    fun addToken(color: ManaColor) {
        manaTokens.add(ManaToken(color))
    }

    fun consume(manaUnit: ManaUnit) {
        when (manaUnit) {
            is ManaDie -> {
                diceUsedThisTurn++
            }
            is ManaCrystal -> {
                val i = manaCrystals.indexOfLast { it.isOfColor(manaUnit.color) }
                manaCrystals.removeAt(i)
            }
            is ManaToken -> {
                val i = manaTokens.indexOfLast { it.isOfColor(manaUnit.color) }
                manaTokens.removeAt(i)
            }
        }
    }
}
