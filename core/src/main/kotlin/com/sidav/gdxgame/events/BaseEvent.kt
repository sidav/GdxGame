package com.sidav.gdxgame.events

import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.hexagonal_map.AxialCoords
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.mana_units.ManaCrystal
import com.sidav.gdxgame.game.mana.mana_units.ManaDie
import com.sidav.gdxgame.game.mana.mana_units.ManaToken

interface BaseEvent {}

interface GameEvent : BaseEvent {
    object NoEvent : GameEvent
    class EffectTriesToBePlayed(val effectSourceCard: CardBase, val effect: CardEffect) : GameEvent
    object PaymentCanceled : GameEvent
    class PlayerPaysWithManaDie(val die: ManaDie, val dieIndex: Int) : GameEvent
    class PlayerPaysWithManaCrystal(val crystal: ManaCrystal, val index: Int) : GameEvent
    class PlayerPaysWithManaToken(val token: ManaToken, val index: Int) : GameEvent

//    class PlayerSelectsManaDie(val die: ManaDie, val dieIndex: Int) : GameEvent
    class PlayerSelectsManaColor(val color: ManaColor) : GameEvent

    class PlayerTriesToMove(val newCoords: AxialCoords) : GameEvent
    class PlayerAppliesCombatTokenToEnemy(val tokenIndex: Int, val enemyIndex: Int) : GameEvent
    object PlayerAdvancesCombatPhase : GameEvent
}

interface UiEvent : BaseEvent {}
