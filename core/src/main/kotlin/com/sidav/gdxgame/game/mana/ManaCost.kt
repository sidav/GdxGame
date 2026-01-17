package com.sidav.gdxgame.game.mana

sealed class ManaCost {
    object Free: ManaCost() {
        override fun acceptsAsPayment(manaSource: ManaSource): Boolean {
            return false
        }
    }
    class OneMana(val costManaColor: Mana): ManaCost() {
        override fun acceptsAsPayment(manaSource: ManaSource): Boolean {
            if (costManaColor == Mana.BLACK) return manaSource.isOfColor(Mana.BLACK)
            return manaSource.isOfColor(costManaColor) || manaSource.isOfColor(Mana.GOLD)
        }
    }

    abstract fun acceptsAsPayment(manaSource: ManaSource): Boolean
}
