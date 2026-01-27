package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.game.mana.Mana
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

internal fun getColorForMana(mana: Mana) = when (mana) {
    Mana.WHITE -> Color(0.7f, 0.7f, 0.8f, 1f)
    Mana.BLUE -> Color(0f, 0.2f, 1f, 1f)
    Mana.GREEN -> Color(0f, 0.6f, 0f, 1f)
    Mana.RED -> Color(0.6f, 0f, 0f, 1f)
    Mana.GOLD -> Color(0.7f, 0.7f, 0f, 1f)
    Mana.BLACK -> Color(0.05f, 0.05f, 0.05f, 1f)
}

internal fun drawThickRect(
    shape: ShapeRenderer,
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    thickness: Float = 2f
) {
    shape.rectLine(x, y, x + w, y, thickness)
    shape.rectLine(x, y + h, x + w, y + h, thickness)
    shape.rectLine(x, y, x, y + h, thickness)
    shape.rectLine(x + w, y, x + w, y + h, thickness)
}

internal fun drawAdjacentThickRects(
    shape: ShapeRenderer,
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    topRectHeight: Float,
    thickness: Float = 2f
) {
    shape.rectLine(x, y, x + w, y, thickness)
    shape.rectLine(x, y + h, x + w, y + h, thickness)
    shape.rectLine(x, y, x, y + h, thickness)
    shape.rectLine(x + w, y, x + w, y + h, thickness)
    shape.rectLine(x, y + h - topRectHeight, x + w, y + h - topRectHeight, thickness)
}

/** Pointy-top. Receives on-screen coords. TODO: get rid of sin/cos stuff, it's not needed there */
internal fun drawHex(shape: ShapeRenderer, centerX: Float, centerY: Float, radius: Float) {
    val angleOffset = -PI / 2.0  // pointy-top: первый угол вверх

    var prevX = 0f
    var prevY = 0f
    var firstX = 0f
    var firstY = 0f

    for (i in 0 until 6) {
        val angle = angleOffset + i * (PI / 3.0)
        val vx = centerX + (radius * cos(angle)).toFloat()
        val vy = centerY + (radius * sin(angle)).toFloat()

        if (i == 0) {
            firstX = vx
            firstY = vy
        } else {
            shape.line(prevX, prevY, vx, vy)
        }

        prevX = vx
        prevY = vy
    }

    shape.line(prevX, prevY, firstX, firstY)
}
