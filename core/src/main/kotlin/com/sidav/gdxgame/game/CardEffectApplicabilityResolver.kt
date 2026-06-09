package com.sidav.gdxgame.game

import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectEvent
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest
import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.game.state.combat.CombatPhase
import com.sidav.gdxgame.ui.UiStack

class CardEffectApplicabilityResolver(
    private val state: GameState,
) {
    fun canCardEffectBePlayedNow(cardEffect: CardEffect): Boolean =
        cardEffect.applicability.any { isApplicable(it) }

    private fun isApplicable(applicability: CardEffect.Applicability): Boolean =
        when (applicability) {
            CardEffect.Applicability.ALWAYS -> true
            CardEffect.Applicability.OUTSIDE_COMBAT -> state.currentCombat == null
            CardEffect.Applicability.COMBAT_ANYTIME -> state.currentCombat != null
            CardEffect.Applicability.COMBAT_RANGED_PHASE -> isInPhase(CombatPhase.RANGED)
            CardEffect.Applicability.COMBAT_DEFEND_PHASE -> isInPhase(CombatPhase.DEFEND)
            CardEffect.Applicability.COMBAT_ATTACK_PHASE -> isInPhase(CombatPhase.ATTACK)
        }

    private fun isInPhase(phase: CombatPhase): Boolean =
        state.currentCombat?.phase == phase
}
