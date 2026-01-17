package com.sidav.gdxgame.game

import com.sidav.gdxgame.game.hexagonal_map.AxialCoords
import com.sidav.gdxgame.game.state.GameState

class PlayerMoveResolver(private val gameState: GameState) {

    fun execute(newCoords: AxialCoords): Boolean {
        if (gameState.canPlayerMoveToTileInSingleStep(newCoords) &&
            gameState.player.stats.movement >= gameState.getMoveCost(newCoords)
        ) {
            gameState.player.stats.movement -= gameState.getMoveCost(newCoords)
            gameState.player.position = newCoords
            return true
        }
        return false
    }
}
