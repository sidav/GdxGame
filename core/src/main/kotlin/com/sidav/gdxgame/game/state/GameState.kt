package com.sidav.gdxgame.game.state

import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.deeds.Crystallize
import com.sidav.gdxgame.game.cards_library.deeds.Rage
import com.sidav.gdxgame.game.cards_library.deeds.Swiftness
import com.sidav.gdxgame.game.cards_library.deeds.Tranquility
import com.sidav.gdxgame.game.deck.Deck
import com.sidav.gdxgame.game.mana.ManaDie
import com.sidav.gdxgame.game.state.stats.PlayerStats
import com.sidav.gdxgame.game.state.stats.Stat
import com.sidav.gdxgame.game.state.stats.StatChange

class GameState {
    val playerHand = Deck<CardBase>()

    val playerStats = PlayerStats()
    val ambientManaDice = MutableList(3) { ManaDie() }

    init {
        ambientManaDice.forEach { it.roll() }
        playerHand.putAll(
            Crystallize(),
            Rage(),
            Swiftness(),
            Rage(),
            Tranquility()
        )
    }

    fun applyStatChange(statChange: StatChange) {
        when (statChange.stat) {
            Stat.MOVEMENT -> playerStats.movement += statChange.change
            Stat.LEADERSHIP -> playerStats.leadership += statChange.change
        }
    }
}
