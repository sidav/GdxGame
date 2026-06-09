package com.sidav.gdxgame.game

import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.game.state.combat.AttackToken
import com.sidav.gdxgame.game.state.combat.CombatPhase
import com.sidav.gdxgame.game.state.combat.CombatState
import com.sidav.gdxgame.ui.UiStack

class CombatResolver(
    private val state: GameState,
    private val uiStack: UiStack
) {
    fun combatShouldStart(): Boolean {
        val plrHex = state.getPlayersHex()
        if (plrHex.tokensHere.isNotEmpty())
            return true
        return false
    }

    fun initCombat() {
        val plrHex = state.getPlayersHex()
        val enemies = plrHex.tokensHere
        state.currentCombat = CombatState(enemies)
        uiStack.showCombatOverlay()
    }

    fun applyToken(tokenIndex: Int, enemyIndex: Int) {
        val combat = state.currentCombat ?: return
        if (tokenIndex >= combat.playerTokens.size) return
        if (enemyIndex >= combat.enemies.size) return

        val token = combat.playerTokens.removeAt(tokenIndex)
        if (token is AttackToken) {
            val enemy = combat.enemies[enemyIndex]
            enemy.health -= token.attackValue
            if (enemy.health <= 0) {
                combat.enemies.removeAt(enemyIndex)
            }
        }

        if (combat.enemies.isEmpty()) {
            endCombat()
        }
    }

    fun advancePhase() {
        val combat = state.currentCombat ?: return
        when (combat.phase) {
            CombatPhase.RANGED -> combat.phase = CombatPhase.DEFEND
            CombatPhase.DEFEND -> combat.phase = CombatPhase.ATTACK
            CombatPhase.ATTACK -> endCombat()
        }
    }

    private fun endCombat() {
        // TODO: damage to player, after battle rewards
        val combat = state.currentCombat ?: return
        val hex = state.getPlayersHex()
        val remainingTokens = combat.enemies.map { it.token }.toSet()
        hex.tokensHere.retainAll(remainingTokens)
        state.currentCombat = null
        uiStack.removeCombatOverlay()
    }
}
