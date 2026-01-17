package com.sidav.gdxgame.game.state.combat

import com.sidav.gdxgame.game.monsters.tokens.MonsterToken

class CombatState(enemyTokens: List<MonsterToken>) {
    val enemies = mutableListOf<Enemy>()
    var phase = CombatPhase.RANGED

    init {
        for (mt in enemyTokens) {
            enemies.add(
                Enemy(mt)
            )
        }
    }
}

enum class CombatPhase {
    RANGED,
    DEFEND,
    ATTACK
}
