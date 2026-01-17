package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.game.mana.Mana
import sun.java2d.marlin.FloatMath

internal fun getColorForMana(mana: Mana) = when (mana) {
    Mana.WHITE -> Color(0.7f, 0.7f, 0.8f, 1f)
    Mana.BLUE -> Color(0f, 0.2f, 1f, 1f)
    Mana.GREEN -> Color(0f, 0.6f, 0f, 1f)
    Mana.RED -> Color(0.6f, 0f, 0f, 1f)
    Mana.GOLD -> Color(0.7f, 0.7f, 0f, 1f)
    Mana.BLACK -> Color(0.05f, 0.05f, 0.05f, 1f)
}

internal fun drawThickRect(shape: ShapeRenderer, x: Float, y: Float, w: Float, h: Float, thickness: Float = 2f) {
    shape.rectLine(x, y, x + w, y, thickness)
    shape.rectLine(x, y + h, x + w, y + h, thickness)
    shape.rectLine(x, y, x, y + h, thickness)
    shape.rectLine(x + w, y, x + w, y + h, thickness)
}

internal fun drawAdjacentThickRects(shape: ShapeRenderer, x: Float, y: Float, w: Float, h: Float, topRectHeight: Float, thickness: Float = 2f) {
    shape.rectLine(x, y, x + w, y, thickness)
    shape.rectLine(x, y + h, x + w, y + h, thickness)
    shape.rectLine(x, y, x, y + h, thickness)
    shape.rectLine(x + w, y, x + w, y + h, thickness)
    shape.rectLine(x, y+h-topRectHeight, x + w, y+h-topRectHeight, thickness)
}
