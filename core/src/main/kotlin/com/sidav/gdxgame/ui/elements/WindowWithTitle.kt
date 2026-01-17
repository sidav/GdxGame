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
    borderColor: Color = Color.WHITE,
    bgColor: Color = Color.BLACK,
    val titlebarHeight: Float = 40f,
): Window(x, y, w, h, borderColor, bgColor) {

    val heightWithoutTitlebar = h - titlebarHeight

    override fun drawBorders(shape: ShapeRenderer) {
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
