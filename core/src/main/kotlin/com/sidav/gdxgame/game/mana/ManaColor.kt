package com.sidav.gdxgame.game.mana

enum class ManaColor {
    WHITE, BLUE, GREEN, RED, GOLD, BLACK;

    fun isOfType(manaType: ManaType): Boolean {
        return when (manaType) {
            ManaType.ANY -> true
            ManaType.BASIC -> this != GOLD && this != BLACK
            ManaType.NONBASIC -> this == GOLD || this == BLACK
        }
    }
}

enum class ManaType {
    ANY, BASIC, NONBASIC
}
