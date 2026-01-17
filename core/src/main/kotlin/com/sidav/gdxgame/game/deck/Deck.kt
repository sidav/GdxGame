package com.sidav.gdxgame.game.deck

import kotlin.random.Random

class Deck<T : Any>(
    private val stack: MutableList<T> = mutableListOf()
) {

    val contents: List<T>
        get() = stack

    fun putAll(vararg items: T) {
        for (item in items) {
            putOnTop(item)
        }
    }

    fun putOnTop(what: T) {
        stack.add(what)
    }

    fun putToBottom(what: T) {
        stack.add(0, what)
    }

    fun draw(): T = stack.removeAt(stack.lastIndex)

    fun remove(what: T) {
        stack.remove(what)
    }

    val top: T
        get() = stack.last()

    val size: Int
        get() = stack.size

    fun shuffle(random: Random = Random.Default) {
        // Fisher–Yates shuffle
        for (i in stack.lastIndex downTo 1) {
            val j = random.nextInt(i + 1)
            val tmp = stack[i]
            stack[i] = stack[j]
            stack[j] = tmp
        }
    }

}
