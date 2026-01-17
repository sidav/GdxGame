package com.sidav.gdxgame.events

import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.mana.ManaDie

interface BaseEvent {}

interface GameEvent : BaseEvent {
    object NoEvent: GameEvent
    class EffectTriesToBePlayed(val effectSourceCard: CardBase, val effect: CardEffect): GameEvent
    object PaymentCanceled: GameEvent
    class PlayerPaysWithManaDie(val die: ManaDie, val dieIndex: Int): GameEvent
}

interface UiEvent: BaseEvent {}
