package com.sidav.gdxgame.game

import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.game.state.combat.CombatState
import com.sidav.gdxgame.ui.UiStack

class CombatResolver(
    private val state: GameState,
    private val uiStack: UiStack
) {
    fun combatShouldStart(): Boolean {
        // Check 1: Moved into tile with enemy?
        val plrHex = state.getPlayersHex()
        if (plrHex.tokensHere.isNotEmpty())
            return true
        // TODO Check 2: Moved around wandering enemy?
        return false
    }

    fun initCombat() {
        val plrHex = state.getPlayersHex()
        val enemies = plrHex.tokensHere
        state.currentCombat = CombatState(enemies)
        uiStack.showCombatOverlay()
    }
}
