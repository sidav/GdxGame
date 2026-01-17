package com.sidav.gdxgame.game.cards_library.deeds

import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.mana.Mana
import com.sidav.gdxgame.game.mana.ManaCost

class Tranquility : DeedCard(
    CardEffect("Heal 1"),
    CardEffect("Draw a card"),
    CardEffect("Heal 2", ManaCost.OneMana(Mana.GREEN)),
    CardEffect("Draw two cards", ManaCost.OneMana(Mana.GREEN))
)
