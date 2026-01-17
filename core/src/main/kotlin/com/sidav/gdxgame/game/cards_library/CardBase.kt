package com.sidav.gdxgame.game.cards_library

import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.mana.Mana
import com.sidav.gdxgame.game.mana.ManaCost

abstract class CardBase() {
    val cardName = // Card name is the class name "split" by CamelCase.
        javaClass.simpleName.replace(Regex("([a-z])([A-Z])"), "$1 $2")
    abstract val effects: Array<out CardEffect>

    fun getManaCostColor(): Mana {
        for (eff in effects)
            if (eff.cost is ManaCost.OneMana) return eff.cost.costManaColor
        TODO("Implement other mana cost color variants lol")
    }
}
