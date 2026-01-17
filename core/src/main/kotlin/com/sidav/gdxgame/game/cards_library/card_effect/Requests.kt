package com.sidav.gdxgame.game.cards_library.card_effect

import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaCost
import com.sidav.gdxgame.game.mana.ManaType
import com.sidav.gdxgame.game.mana.mana_units.ManaCrystal
import com.sidav.gdxgame.game.mana.mana_units.ManaToken
import com.sidav.gdxgame.game.mana.mana_units.ManaUnit
import com.sidav.gdxgame.game.state.stats.StatChange

/** This is sent BY cardEffect as something that should happen in order so that the effect can advance */
sealed interface CardEffectRequest {
    /** Just advance the step, please */
    object NoRequest : CardEffectRequest

    data class RequestPayment(val cost: ManaCost) : CardEffectRequest

    /** "The mana provided for payment may be removed from play now" */
    class ConsumePayment(val paid: ManaUnit) : CardEffectRequest

    class RequestManaColorSelection(val manaType: ManaType) : CardEffectRequest
    class ApplyStatChange(val statChange: StatChange) : CardEffectRequest
    class GivePlayerAManaCrystal(val color: ManaColor) : CardEffectRequest
    class GivePlayerAManaToken(val color: ManaColor) : CardEffectRequest
    class DrawCards(val howMany: Int) : CardEffectRequest
    class HealWounds(val howMany: Int) : CardEffectRequest

    /** This is for debugging. */
    object ApplyUnimplemented : CardEffectRequest

    /** Applying finished */
    object Finish : CardEffectRequest
}
