package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.game.mana.ManaColor

internal fun getColorForMana(mana: ManaColor?) = when (mana) {
    null -> Color.WHITE
    ManaColor.WHITE -> Color(0.7f, 0.7f, 0.8f, 1f)
    ManaColor.BLUE -> Color(0f, 0.2f, 1f, 1f)
    ManaColor.GREEN -> Color(0f, 0.6f, 0f, 1f)
    ManaColor.RED -> Color(0.6f, 0f, 0f, 1f)
    ManaColor.GOLD -> Color(0.7f, 0.7f, 0f, 1f)
    ManaColor.BLACK -> Color(0.05f, 0.05f, 0.05f, 1f)
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

/** Draws a diamond by rect coords.*/
fun drawDiamondWithBorder(
    shape: ShapeRenderer,
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    bgColor: Color,
    borderColor: Color,
    borderThickness: Float
) {
    shape.setColor(bgColor)
    shape.triangle(x, y + h / 2, x + w / 2, y + h, x + w, y + h / 2)
    shape.triangle(x, y + h / 2, x + w / 2, y, x + w, y + h / 2)
    shape.setColor(borderColor)
    shape.rectLine(x, y + h / 2, x + w / 2, y + h, borderThickness)
    shape.rectLine(x + w / 2, y + h, x + w, y + h / 2, borderThickness)
    shape.rectLine(x + w, y + h / 2, x + w / 2, y, borderThickness)
    shape.rectLine(x + w / 2, y, x, y + h / 2, borderThickness)
}

/** Draws inside square rect coords.*/
fun drawCircleWithBorder(
    shape: ShapeRenderer,
    x: Float,
    y: Float,
    w: Float,
    bgColor: Color,
    borderColor: Color,
    borderThickness: Float,
) {
    shape.setColor(borderColor)
    shape.circle(x + w / 2, y + w / 2, w / 2)
    shape.setColor(bgColor)
    shape.circle(x + w / 2, y + w / 2, w / 2 - borderThickness)
}

// Draws
fun drawDropletWithBorder(
    shape: ShapeRenderer,
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    bgColor: Color,
    borderColor: Color,
    borderThickness: Float,
) {
    val ellipseH = h * 0.4f

    shape.color = borderColor
    shape.ellipse(x, y, w, ellipseH)
    shape.triangle(x, y + ellipseH / 2, x + w / 2, y + h, x + w, y + ellipseH / 2)

    shape.color = bgColor
    shape.ellipse(
        x + borderThickness,
        y + borderThickness,
        w - borderThickness * 2,
        ellipseH - borderThickness * 2
    )
    shape.triangle(
        x + borderThickness,
        y + ellipseH / 2,
        x + w / 2,
        y + h - borderThickness * 2,
        x + w - borderThickness,
        y + ellipseH / 2
    )
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

internal fun drawHexFilled(shape: ShapeRenderer, x: Float, y: Float, size: Float) {
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

    shape.triangle(x, y, x0, y0, x1, y1)
    shape.triangle(x, y, x1, y1, x2, y2)
    shape.triangle(x, y, x2, y2, x3, y3)
    shape.triangle(x, y, x3, y3, x4, y4)
    shape.triangle(x, y, x4, y4, x5, y5)
    shape.triangle(x, y, x5, y5, x0, y0)
}

data class Vec2(val x: Float, val y: Float)
