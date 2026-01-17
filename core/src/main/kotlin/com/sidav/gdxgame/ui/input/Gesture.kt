package com.sidav.gdxgame.ui.input

sealed class Gesture(val x: Float, val y: Float) {
    class Tap(x: Float, y: Float) : Gesture(x, y)
    class LongTap(x: Float, y: Float) : Gesture(x, y)
    class SwipeStart(x0: Float, y0: Float) : Gesture(x0, y0)
    class SwipeMove(x0: Float, y0: Float, val dx: Float, val dy: Float) : Gesture(x0, y0)
    class SwipeEnd(x0: Float, y0: Float, val dx: Float, val dy: Float) : Gesture(x0, y0)
}

