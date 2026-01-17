package com.sidav.gdxgame.game.hexagonal_map

import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.game.hexagonal_map.flower_tile.FlowerTileHex
import com.sidav.gdxgame.game.monsters.tokens.MonsterToken

/**
 * Q and R are axial coordinates.
 */
class Hex(val axialCoords: AxialCoords) {
    var state: HexState = HexState.UNEXPLORED
    var terrainHere: TerrainType = TerrainType.UNREVEALED
    var featureHere: TerrainFeature? = null
    var tokensHere = mutableListOf<MonsterToken>()

    internal fun applyFlowerTileHex(fth: FlowerTileHex) {
        check(terrainHere == TerrainType.UNREVEALED) { "Applying tile on already applied hex!" }
        terrainHere = fth.terrain
        featureHere = fth.feature
    }

    fun addMonsterToken(token: MonsterToken) {
        debugMsg("HEX", "Adding token: ${token}")
        tokensHere.add(token)
    }
}

enum class HexState {
    UNEXPLORED, EXPLORED, REVEALED
}
