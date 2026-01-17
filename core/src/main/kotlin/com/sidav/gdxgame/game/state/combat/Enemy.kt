package com.sidav.gdxgame.game.state.combat

import com.sidav.gdxgame.game.monsters.tokens.MonsterToken

/** This is created from MonsterToken and unlike it exists ONLY inside the combat. */
class Enemy(val token: MonsterToken) {
    var health: Int = 0
    init {
        health = token.defense
    }
}
