package com.sidav.gdxgame.game.mana

import com.sidav.gdxgame.game.mana.mana_units.ManaUnit

sealed class ManaCost {
    object Free : ManaCost() {
        override fun acceptsAsPayment(manaUnit: ManaUnit): Boolean {
            return false
        }
    }

    class SingleManaOfColor(val costManaColor: ManaColor) : ManaCost() {
        override fun acceptsAsPayment(manaUnit: ManaUnit): Boolean {
            if (costManaColor == ManaColor.BLACK) return manaUnit.isOfColor(ManaColor.BLACK)
            return manaUnit.isOfColor(costManaColor) || manaUnit.isOfColor(ManaColor.GOLD)
        }
    }

    class SingleManaOfType(val manaType: ManaType) : ManaCost() {
        override fun acceptsAsPayment(manaUnit: ManaUnit): Boolean =
            manaUnit.color.isOfType(manaType)
    }

    abstract fun acceptsAsPayment(manaUnit: ManaUnit): Boolean
}
