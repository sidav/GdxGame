package com.sidav.gdxgame.game.cards_library.card_effect

import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.mana_units.ManaUnit

/** This is sent INTO cardEffect as something that happened outside */
sealed interface CardEffectEvent {
    object NoEvent : CardEffectEvent

    // data class OptionChosen(val index: Int) : CardEffectEvent
    // data class CardChosen() : CardEffectEvent
    class ManaPaid(val manaUnit: ManaUnit) : CardEffectEvent
    object PaymentConsumed: CardEffectEvent
    object Cancelled : CardEffectEvent
    class ManaColorSelected(val manaColor: ManaColor) : CardEffectEvent
    class Applied(val request: CardEffectRequest) : CardEffectEvent
}
