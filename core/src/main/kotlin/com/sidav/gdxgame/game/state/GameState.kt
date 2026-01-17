package com.sidav.gdxgame.game.state

import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.game.cards_library.deeds.Tranquility
import com.sidav.gdxgame.game.deck.Deck
import com.sidav.gdxgame.game.hexagonal_map.AxialCoords
import com.sidav.gdxgame.game.hexagonal_map.Hex
import com.sidav.gdxgame.game.hexagonal_map.HexState
import com.sidav.gdxgame.game.hexagonal_map.HexagonalMap
import com.sidav.gdxgame.game.hexagonal_map.TerrainFeature
import com.sidav.gdxgame.game.hexagonal_map.TerrainType
import com.sidav.gdxgame.game.hexagonal_map.flower_tile.FlowerTile
import com.sidav.gdxgame.game.mana.ManaStock
import com.sidav.gdxgame.game.mana.mana_units.ManaDie
import com.sidav.gdxgame.game.monsters.tokens.MonsterToken
import com.sidav.gdxgame.game.state.combat.CombatState

class GameState {
    val day: Boolean = true // False means night
    val player = Player()

    val map = HexagonalMap(6, 10)
    val countrysideTilesDeck = Deck<FlowerTile>()
    val coreTilesDeck = Deck<FlowerTile>()

    val greenMonstersPile = Deck<MonsterToken>()

    val manaStock = ManaStock()
    var currentCombat: CombatState? = null // CombatState

    init {
        createPlayerDeck()
        player.drawCards(5)
        manaStock.rollManaDice()
        createMonsterTokenPiles()
        createTilesDecksAndLayBase()

        val portalTile = map.findHex { it.terrainHere == TerrainType.PORTAL }
        player.position = portalTile?.axialCoords ?: error("No portal spawned, can't place player!")

        // DEBUG:
        repeat(3) {
            manaStock.addCrystal(ManaDie().rollBasicColor())
        }
        repeat(3) {
            manaStock.addToken(ManaDie().rollBasicColor())
        }
        player.stats.movement += 10
        // DEBUG END
    }

    fun canPlayerMoveToTileInSingleStep(ac: AxialCoords): Boolean {
        return (
            player.position.distanceTo(ac) == 1 &&
                map.getHex(ac)?.terrainHere?.passable ?: false
            )
    }

    fun getPlayersHex(): Hex {
        val plrHex = map.getHex(player.position) ?: error("Player hex unavailable")
        return plrHex
    }

    fun getMoveCost(ac: AxialCoords): Int {
        if (day)
            return map.getHex(ac)?.terrainHere?.dayMoveCost ?: 0
        return map.getHex(ac)?.terrainHere?.nightMoveCost ?: 0
    }

    /** This places a flower tile and places/reveals tokens as needed */
    fun revealFlowerTile(flowerTile: FlowerTile, center: AxialCoords) {
        map.placeFlowerTileAt(flowerTile, center)
        map.applyToHexAndNeighbors(center) {
            it.state = if (day) HexState.REVEALED else HexState.EXPLORED
            if (it.featureHere == TerrainFeature.RAMPAGING_MONSTER_GREEN) {
                it.addMonsterToken(greenMonstersPile.draw())
            }
        }
    }
}
