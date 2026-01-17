package com.sidav.gdxgame.game.state

import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.WoundCard
import com.sidav.gdxgame.game.deck.Deck
import com.sidav.gdxgame.game.hexagonal_map.AxialCoords
import com.sidav.gdxgame.game.state.stats.PlayerStats

class Player() {
    val stats = PlayerStats()

    var position = AxialCoords.ZERO

    val playerHand = Deck<CardBase>()
    val playerDiscard = Deck<CardBase>()
    val playerDeck = Deck<CardBase>()

    fun drawCards(howMany: Int) {
        repeat(howMany) {
            if (playerDeck.size == 0) return
            val drawn = playerDeck.draw()
            debugMsg("PLAYER", "Drawn card: ${drawn.cardName}")
            playerHand.putOnTop(drawn)
        }
    }

    fun gainWounds(howMany: Int) {
        repeat(howMany) {
            playerHand.put(WoundCard())
        }
    }

    fun hasWoundInHand() = playerHand.contents.any { it is WoundCard }

    fun healWounds(howMany: Int) {
        repeat(howMany) {
            val wnd = playerHand.contents.firstOrNull { it is WoundCard } ?: return
            playerHand.remove(wnd)
        }
    }

    fun discardCard(which: CardBase) {
        playerDiscard.putOnTop(which)
        playerHand.remove(which)
    }
}
