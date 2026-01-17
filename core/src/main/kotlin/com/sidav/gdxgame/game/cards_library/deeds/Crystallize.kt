package com.sidav.gdxgame.game.cards_library.deeds

import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectEvent
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaCost
import com.sidav.gdxgame.game.mana.ManaType

class Crystallize : DeedCard(
    object : CardEffect(
        "When you play this, also pay one mana of a basic color. Gain a crystal of that color to your Inventory. ",
        ManaCost.SingleManaOfType(ManaType.BASIC)
    ) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.GivePlayerAManaCrystal(
                paidWith?.color ?: error("Nothing to give: null paid mana.")
            )
        }

        override fun onApplying(ev: CardEffectEvent) {
            if (ev is CardEffectEvent.Applied) {
                switchState(States.DONE)
            }
        }
    },

    object : CardEffect(
        "Gain a crystal of any color to your Inventory. ",
        ManaCost.SingleManaOfColor(ManaColor.BLUE)
    ) {

        var selectedColor: ManaColor? = null
        override fun requestOnApplying(): CardEffectRequest {
            val col = selectedColor
            if (col != null) return CardEffectRequest.GivePlayerAManaCrystal(col)
            return CardEffectRequest.RequestManaColorSelection(ManaType.ANY)
        }

        override fun onApplying(ev: CardEffectEvent) {
            when (ev) {
                is CardEffectEvent.ManaColorSelected -> selectedColor = ev.manaColor
                is CardEffectEvent.Applied -> switchState(States.DONE)
                else -> {}
            }
        }
    })
