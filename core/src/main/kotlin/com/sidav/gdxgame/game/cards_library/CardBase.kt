package com.sidav.gdxgame.game.cards_library

import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaCost

abstract class CardBase() {
    open val cardName = // Card name is the class name "split" by CamelCase.
        javaClass.simpleName.replace(Regex("([a-z])([A-Z])"), "$1 $2")
    abstract val effects: Array<out CardEffect>

    fun getManaCostColor(): ManaColor? {
        for (eff in effects) when (eff.cost) {
            is ManaCost.Free, is ManaCost.SingleManaOfType -> {}
            is ManaCost.SingleManaOfColor -> return eff.cost.costManaColor
        }
        return null
    }
}
