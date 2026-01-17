package com.sidav.gdxgame.game.mana.mana_units

import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaType
import kotlin.random.Random

class ManaDie (private val random: Random = Random.Default): ManaUnit {

    override var color: ManaColor = ManaColor.WHITE
        private set

    private val faces = listOf(
        ManaColor.WHITE,
        ManaColor.BLUE,
        ManaColor.GREEN,
        ManaColor.RED,
        ManaColor.BLACK,
        ManaColor.GOLD
    )

    fun roll(): ManaColor {
        color = faces.random(random)
        return color
    }

    fun rollBasicColor(): ManaColor {
        do roll() while (color.isOfType(ManaType.NONBASIC))
        return color
    }
}
