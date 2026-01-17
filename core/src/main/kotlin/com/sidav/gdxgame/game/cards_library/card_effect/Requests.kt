package com.sidav.gdxgame.game.cards_library.card_effect

import com.sidav.gdxgame.game.mana.ManaCost
import com.sidav.gdxgame.game.state.stats.StatChange

/** This is sent BY cardEffect as something that should happen in order so that the effect can advance */
sealed interface CardEffectRequest {
    /** Just advance the step, please */
    object NoRequest : CardEffectRequest

    data class RequestPayment(val cost: ManaCost) : CardEffectRequest
    /** "The mana provided for payment may be removed from play now" */
    data class ConsumePayment(val cost: ManaCost) : CardEffectRequest
    class ApplyStatChange(val statChange: StatChange): CardEffectRequest
    /** This is for debugging. */
    object ApplyUnimplemented : CardEffectRequest
    object Finish : CardEffectRequest
}
