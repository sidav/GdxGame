package com.sidav.gdxgame.game.mana

import kotlin.random.Random

class ManaDie (private val random: Random = Random.Default): ManaSource {

    var face: Mana = Mana.WHITE

    private val faces = listOf(
        Mana.WHITE,
        Mana.BLUE,
        Mana.GREEN,
        Mana.RED,
        Mana.BLACK,
        Mana.GOLD
    )

    fun roll(): Mana {
        face = faces.random(random)
        return face
    }

    override fun isOfColor(color: Mana): Boolean {
        return color == face
    }
}

