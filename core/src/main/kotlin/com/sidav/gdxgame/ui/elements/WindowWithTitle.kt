package com.sidav.gdxgame.ui.elements

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.sidav.gdxgame.ui.drawWrappedText
import com.sidav.gdxgame.ui.overlays.drawAdjacentThickRects

class WindowWithTitle(
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    var titleText: String,
    val textColor: Color = Color.WHITE,
    val borderColor: Color = Color.WHITE,
    val bgColor: Color = Color.BLACK,
    val titlebarHeight: Float = 40f,
) {

    val heightWithoutTitlebar = h - titlebarHeight
    val rect = Rectangle(x, y, w, h)

    fun fillBackground(shape: ShapeRenderer) {
        shape.color = bgColor
        shape.rect(rect.x, rect.y, rect.width, rect.height)
    }

    fun drawBorders(shape: ShapeRenderer) {
        shape.color = borderColor
        drawAdjacentThickRects(shape, rect.x, rect.y, rect.width, rect.height, titlebarHeight)
    }

    fun drawHeaderText(batch: SpriteBatch, layout: GlyphLayout, font: BitmapFont, yOffset: Float = 0f) {
        drawWrappedText(
            titleText, rect.x, rect.y + rect.height - yOffset, rect.width,
            batch, layout, font, textColor
        )
    }

    fun createButtonUnder(height: Float, text: String, vOffset: Float = 10f): TapArea {
        return TapArea(rect.x, rect.y - vOffset - height,  rect.width, height, text)
    }
}
