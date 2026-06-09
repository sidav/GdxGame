package com.sidav.gdxgame.game.cards_library.deeds.todo

import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect.Applicability
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaCost

class Rage : DeedCard(
    CardEffect("Attack 2", applicability = listOf(Applicability.COMBAT_ATTACK_PHASE)),
    CardEffect("Block 2", applicability = listOf(Applicability.COMBAT_DEFEND_PHASE)),
    CardEffect(
        "Attack 4",
        ManaCost.SingleManaOfColor(ManaColor.RED), applicability = listOf(
            Applicability.COMBAT_ATTACK_PHASE
        )
    )
)
