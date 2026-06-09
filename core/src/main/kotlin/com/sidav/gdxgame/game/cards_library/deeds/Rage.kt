package com.sidav.gdxgame.game.cards_library.deeds

import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaCost
import com.sidav.gdxgame.game.state.combat.AttackToken
import com.sidav.gdxgame.game.state.combat.BlockToken

class Rage : DeedCard(
    object : CardEffect(
        "Attack 2",
        applicability = listOf(Applicability.COMBAT_ATTACK_PHASE)
    ) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.GivePlayerCombatToken(
                AttackToken(
                    2
                )
            )
        }
    },
    object : CardEffect(
        "Block 2",
        applicability = listOf(Applicability.COMBAT_DEFEND_PHASE)
    ) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.GivePlayerCombatToken(
                BlockToken(
                    2
                )
            )
        }
    },
    object : CardEffect(
        "Attack 4",
        ManaCost.SingleManaOfColor(ManaColor.RED),
        applicability = listOf(
            Applicability.COMBAT_ATTACK_PHASE
        )
    ) {
        override fun requestOnApplying(): CardEffectRequest {
            return CardEffectRequest.GivePlayerCombatToken(
                AttackToken(
                    4
                )
            )
        }
    }
)
