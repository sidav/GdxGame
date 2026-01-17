package com.sidav.gdxgame.ui.elements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.sidav.gdxgame.ui.drawWrappedText
import com.sidav.gdxgame.ui.input.Gesture
import com.sidav.gdxgame.ui.overlays.drawAdjacentThickRects
import com.sidav.gdxgame.ui.overlays.drawThickRect

open class Window(
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    val borderColor: Color = Color.WHITE,
    val bgColor: Color = Color.BLACK,
) {
    val rect = Rectangle(x, y, w, h)

    fun fillBackground(shape: ShapeRenderer) {
        shape.color = bgColor
        shape.rect(rect.x, rect.y, rect.width, rect.height)
    }

    open fun drawBorders(shape: ShapeRenderer) {
        shape.color = borderColor
        drawThickRect(shape, rect.x, rect.y, rect.width, rect.height)
    }

    fun hitByTap(tap: Gesture.Tap): Boolean {
        return rect.contains(tap.x, tap.y)
    }
}
