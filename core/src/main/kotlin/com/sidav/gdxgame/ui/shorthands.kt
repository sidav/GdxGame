package com.sidav.gdxgame.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Align

fun drawWrappedText(
    text: String,
    x: Float, y: Float, width: Float,
    batch: SpriteBatch, layout: GlyphLayout, font: BitmapFont,
    color: Color = Color.WHITE, halign: Int = Align.center) {

    layout.setText(
        font,
        text,
        color,
        width,
        halign,
        true
    )
    font.draw(batch, layout, x, y-font.lineHeight/3)
}
