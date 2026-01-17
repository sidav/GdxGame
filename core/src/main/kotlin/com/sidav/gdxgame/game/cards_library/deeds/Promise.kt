package com.sidav.gdxgame.game.cards_library.deeds

import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaCost
import com.sidav.gdxgame.game.state.stats.Stat
import com.sidav.gdxgame.game.state.stats.StatChange

class Promise: DeedCard(
    object: CardEffect("Influence 2") {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.ApplyStatChange(StatChange(Stat.INFLUENCE, 2))
        }
    },
    object: CardEffect("Influence 4", cost = ManaCost.SingleManaOfColor(ManaColor.WHITE)) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.ApplyStatChange(StatChange(Stat.INFLUENCE, 4))
        }
    },
)
