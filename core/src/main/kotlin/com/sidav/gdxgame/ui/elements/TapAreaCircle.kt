package com.sidav.gdxgame.ui.elements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.sidav.gdxgame.ui.drawWrappedText
import com.sidav.gdxgame.ui.input.Gesture
import com.sidav.gdxgame.ui.overlays.drawCircleWithBorder
import com.sidav.gdxgame.ui.overlays.drawThickRect

// A circle which may be tapped. Set by RECTANGLE coords!
open class TapAreaCircle(
    x: Float,
    y: Float,
    w: Float,
    var borderThickness: Float,
    val bgColor: Color = Color.BLACK,
    val outlineColor: Color = Color.WHITE
) {
    val rect = Rectangle(x, y, w, w)
    val r = w/2
    val cx = x + r
    val cy = y + r

    open fun draw(shape: ShapeRenderer) {
        shape.color = outlineColor
        drawCircleWithBorder(shape,rect.x, rect.y, rect.width, bgColor, outlineColor, borderThickness)
    }

    fun hitByTap(tap: Gesture.Tap): Boolean {
        return Circle(cx, cy, r).contains(tap.x, tap.y)
    }
}
