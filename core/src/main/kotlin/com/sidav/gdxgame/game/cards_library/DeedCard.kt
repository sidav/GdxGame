package com.sidav.gdxgame.game.cards_library

import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect

// Two-effect card, has top and bottom actions, may be used as +1 atk/def/mov/inf.
abstract class DeedCard(override vararg val effects: CardEffect) : CardBase()

