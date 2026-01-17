package com.sidav.gdxgame.game.monsters.tokens

import com.sidav.gdxgame.game.monsters.MonsterTrait

open class MonsterToken(val attack: Int, val defense: Int, val fame: Int, vararg val traits: MonsterTrait) {
    open val name = // Token name is the class name "split" by CamelCase.
        javaClass.simpleName.replace(Regex("([a-z])([A-Z])"), "$1 $2")
    override fun toString() = "$name ($attack/$defense, $fame fame)"
}
