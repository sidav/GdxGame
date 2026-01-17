package com.sidav.gdxgame.game.cards_library.deeds

import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.mana.Mana
import com.sidav.gdxgame.game.mana.ManaCost

class Rage : DeedCard(
    CardEffect("Attack 2"),
    CardEffect("Block 2"),
    CardEffect("Attack 4", ManaCost.OneMana(Mana.RED))
)

