package com.sidav.gdxgame.game.state

import com.sidav.gdxgame.game.cards_library.deeds.Crystallize
import com.sidav.gdxgame.game.cards_library.deeds.March
import com.sidav.gdxgame.game.cards_library.deeds.Promise
import com.sidav.gdxgame.game.cards_library.deeds.Stamina
import com.sidav.gdxgame.game.cards_library.deeds.Tranquility
import com.sidav.gdxgame.game.cards_library.deeds.todo.Rage
import com.sidav.gdxgame.game.cards_library.deeds.todo.Swiftness
import com.sidav.gdxgame.game.hexagonal_map.AxialCoords
import com.sidav.gdxgame.game.hexagonal_map.flower_tile.tiles.CORE_TILES
import com.sidav.gdxgame.game.hexagonal_map.flower_tile.tiles.COUNTRYSIDE_TILES
import com.sidav.gdxgame.game.hexagonal_map.flower_tile.tiles.PORTAL_TILES
import com.sidav.gdxgame.game.monsters.tokens.Diggers
import com.sidav.gdxgame.game.monsters.tokens.Prowlers

fun GameState.createPlayerDeck() {
    player.playerDeck.putAll(
        // Implemented cards:
        Promise(),
        Promise(),
        March(),
        March(),
        Stamina(),
        Stamina(),
        Crystallize(),
        Crystallize(),
        Tranquility(),
        Tranquility(),
        // Todo cards:
        Rage(),
        Swiftness(),
    )
    player.playerDeck.shuffle()
}

fun GameState.createMonsterTokenPiles() {
    greenMonstersPile.putAll(
        Prowlers(),
        Prowlers(),
        Diggers(),
        Diggers(),
        // TODO
    )
    greenMonstersPile.shuffle()
}

fun GameState.createTilesDecksAndLayBase() {
    // Form tile decks
    countrysideTilesDeck.putAll(*COUNTRYSIDE_TILES)
    countrysideTilesDeck.shuffle()
    coreTilesDeck.putAll(*CORE_TILES)
    coreTilesDeck.shuffle()

    revealFlowerTile(PORTAL_TILES.first(), AxialCoords(1, 1))
    revealFlowerTile(countrysideTilesDeck.draw(), AxialCoords(0, 4))
    revealFlowerTile(countrysideTilesDeck.draw(), AxialCoords(3, 2))
}
