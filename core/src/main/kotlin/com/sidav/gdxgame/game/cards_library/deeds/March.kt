package com.sidav.gdxgame.game.cards_library.deeds

import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaCost
import com.sidav.gdxgame.game.state.stats.Stat
import com.sidav.gdxgame.game.state.stats.StatChange

class March : DeedCard(
    object : CardEffect(
        "Move 2",
        applicability = listOf(Applicability.OUTSIDE_COMBAT)
    ) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.ApplyStatChange(StatChange(Stat.MOVEMENT, 2))
        }
    },
    object : CardEffect(
        "Move 4",
        cost = ManaCost.SingleManaOfColor(ManaColor.GREEN),
        applicability = listOf(Applicability.OUTSIDE_COMBAT)
    ) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.ApplyStatChange(StatChange(Stat.MOVEMENT, 4))
        }
    },
)
