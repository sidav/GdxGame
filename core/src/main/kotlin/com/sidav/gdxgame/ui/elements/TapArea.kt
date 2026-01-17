package com.sidav.gdxgame.ui.elements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.ui.drawWrappedText
import com.sidav.gdxgame.ui.overlays.drawThickRect

// A rectangle which may be tapped
open class TapArea(
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    val text: String? = null,
    val fgColor: Color = Color.WHITE,
    val bgColor: Color = Color.BLACK,
    val outlineColor: Color = Color.WHITE
) {
    val rect = Rectangle(x, y, w, h)

    fun drawBackground(shape: ShapeRenderer) {
        shape.color = bgColor
        shape.rect(rect.x, rect.y, rect.width, rect.height)
    }

    fun drawOutline(shape: ShapeRenderer) {
        shape.color = outlineColor
        drawThickRect(shape,rect.x, rect.y, rect.width, rect.height)
    }

    fun drawText(batch: SpriteBatch, layout: GlyphLayout, font: BitmapFont, yOffset: Float = 0f) {
        text?.let {
            drawWrappedText(
                text, rect.x, rect.y + rect.height - yOffset, rect.width,
                batch, layout, font, fgColor
            )
        }
    }

    fun hit(x: Float, y: Float): Boolean {
        return rect.contains(x, y)
    }

    fun midX(): Float = rect.x + rect.width/2
    fun topY(): Float = rect.y + rect.height
}
