package com.sidav.gdxgame.game.cards_library.card_effect

import com.sidav.gdxgame.game.mana.ManaSource

/** This is sent INTO cardEffect as something that happened outside */
sealed interface CardEffectEvent {
    object NoEvent : CardEffectEvent

    // data class OptionChosen(val index: Int) : CardEffectEvent
    // data class CardChosen() : CardEffectEvent
    class ManaPaid(val manaSource: ManaSource) : CardEffectEvent
    object PaymentConsumed: CardEffectEvent
    object Cancelled : CardEffectEvent
    object Applied : CardEffectEvent
}
