package com.sidav.gdxgame.game.cards_library.deeds

import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest
import com.sidav.gdxgame.game.mana.Mana
import com.sidav.gdxgame.game.mana.ManaCost
import com.sidav.gdxgame.game.state.stats.Stat
import com.sidav.gdxgame.game.state.stats.StatChange

class Swiftness : DeedCard(
    object: CardEffect("Move 2") {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.ApplyStatChange(StatChange(Stat.MOVEMENT, 2))
        }
    },
    CardEffect("Ranged Attack 3.", ManaCost.OneMana(Mana.WHITE))
)
