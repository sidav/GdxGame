package com.sidav.gdxgame.game.cards_library.deeds

import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.mana.Mana
import com.sidav.gdxgame.game.mana.ManaCost

class Crystallize : DeedCard(
    object : CardEffect(
        "When you play this, also pay one mana of a basic color. Gain a crystal of that color to your Inventory. "
    ) {

    },
    CardEffect(
        "Gain a crystal of any color to your Inventory. ",
        ManaCost.OneMana(Mana.BLUE)
    )
)
