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

private const val SQRT3_HALF = 0.8660254f
/** Pointy-top. Receives on-screen coords */
internal fun drawHex(shape: ShapeRenderer, x: Float, y: Float, size: Float) {
    val x0 = x
    val y0 = y - size

    val x1 = x + SQRT3_HALF * size
    val y1 = y - 0.5f * size

    val x2 = x + SQRT3_HALF * size
    val y2 = y + 0.5f * size

    val x3 = x
    val y3 = y + size

    val x4 = x - SQRT3_HALF * size
    val y4 = y + 0.5f * size

    val x5 = x - SQRT3_HALF * size
    val y5 = y - 0.5f * size

    shape.line(x0, y0, x1, y1)
    shape.line(x1, y1, x2, y2)
    shape.line(x2, y2, x3, y3)
    shape.line(x3, y3, x4, y4)
    shape.line(x4, y4, x5, y5)
    shape.line(x5, y5, x0, y0)
}

