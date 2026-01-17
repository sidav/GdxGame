package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Null
import com.sidav.gdxgame.ui.elements.TapArea

class CurrentHighlightedTileOverlay(val gameMapOverlay: GameMapOverlay) : OverlayBase() {
    val rect = TapArea(0f, 100f, screenWidth, 24f)

    override fun update() {
        val hex = gameMapOverlay.selectedHex
        hex?.let {
            rect.text = "${gameMapOverlay.selectedHexCoords}: ${it.terrainHere.name}"
            if (hex.tokensHere.isNotEmpty()) {
                rect.text += hex.tokensHere.joinToString(prefix = ": ", separator = ",") { tok -> tok.toString() }
            }
        }
    }

    override fun render() {
        if (gameMapOverlay.selectedHex == null) return
        shape.begin(ShapeRenderer.ShapeType.Filled)
        rect.drawBackground(shape)
        rect.drawOutline(shape)
        shape.end()

        batch.begin()
        rect.drawText(batch, layout, font)
        batch.end()
    }
}
