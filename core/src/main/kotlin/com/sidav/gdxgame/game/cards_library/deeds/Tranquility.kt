package com.sidav.gdxgame.game.cards_library.deeds

import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaCost

class Tranquility : DeedCard(
    object : CardEffect("Heal 1", applicability = listOf(Applicability.OUTSIDE_COMBAT)) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.HealWounds(1)
        }
    },

    object : CardEffect("Draw a card") {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.DrawCards(1)
        }
    },

    object : CardEffect(
        "Heal 2",
        ManaCost.SingleManaOfColor(ManaColor.GREEN),
        applicability = listOf(Applicability.OUTSIDE_COMBAT)
    ) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.HealWounds(2)
        }
    },

    object : CardEffect("Draw two cards", ManaCost.SingleManaOfColor(ManaColor.GREEN)) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.DrawCards(2)
        }
    }
)
