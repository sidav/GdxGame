package com.sidav.gdxgame.ui.input

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.TimeUtils
import kotlin.math.abs

class GestureTracker {

    private var startX = 0f
    private var startY = 0f
    private var startTime = 0L

    private var active = false
    private var swipeStarted = false

    fun poll(): Gesture? {

        // TOUCH DOWN
        if (Gdx.input.justTouched()) {
            startX = Gdx.input.x.toFloat()
            startY = Gdx.graphics.height - Gdx.input.y.toFloat()
            startTime = TimeUtils.millis()
            active = true
            swipeStarted = false
            return null
        }

        // TOUCH MOVE (swipe in progress)
        if (active && Gdx.input.isTouched) {
            val x = Gdx.input.x.toFloat()
            val y = Gdx.graphics.height - Gdx.input.y.toFloat()

            val dx = x - startX
            val dy = y - startY

            if (!swipeStarted && (abs(dx) > SWIPE_THRESHOLD_PX || abs(dy) > SWIPE_THRESHOLD_PX)
            ) {
                swipeStarted = true
                return Gesture.SwipeStart(startX, startY)
            }

            if (swipeStarted) {
                return Gesture.SwipeMove(startX, startY, dx, dy)
            }
        }

        // TOUCH UP
        if (active && !Gdx.input.isTouched) {
            active = false

            val endX = Gdx.input.x.toFloat()
            val endY = Gdx.graphics.height - Gdx.input.y.toFloat()
            val dx = endX - startX
            val dy = endY - startY
            val dt = TimeUtils.millis() - startTime

            return classifyGesture(startX, startY, dx, dy, dt)
        }

        return null
    }

    private fun classifyGesture(
        sx: Float,
        sy: Float,
        dx: Float,
        dy: Float,
        dt: Long
    ): Gesture =
        when {
            swipeStarted ->
                Gesture.SwipeEnd(sx, sy, dx, dy)

            dt > LONG_TAP_MS &&
                abs(dx) < SWIPE_THRESHOLD_PX &&
                abs(dy) < SWIPE_THRESHOLD_PX ->
                Gesture.LongTap(sx, sy)

            else ->
                Gesture.Tap(sx, sy)
        }
}
