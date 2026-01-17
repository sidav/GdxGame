package com.sidav.gdxgame.ui.overlays.combat

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import com.sidav.gdxgame.game.monsters.tokens.MonsterToken
import com.sidav.gdxgame.ui.drawTextLine
import com.sidav.gdxgame.ui.drawWrappedText
import com.sidav.gdxgame.ui.elements.TapAreaCircle

class EnemyTapCircle(x: Float, y: Float, w: Float, val token: MonsterToken) :
    TapAreaCircle(x, y, w, 3f) {

    override fun draw(shape: ShapeRenderer) {
        super.draw(shape)
    }

    fun drawTexts(batch: SpriteBatch, layout: GlyphLayout, font: BitmapFont) {
        drawTextLine(
            token.name, cx, cy-10,
            batch, layout, font, halign = Align.center
        )
        drawTextLine(
            token.defense.toString(), cx, cy+rect.height/2-10,
            batch, layout, font, halign = Align.center
        )
    }
}
