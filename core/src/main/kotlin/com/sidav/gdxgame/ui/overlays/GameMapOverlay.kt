package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.game.hexagonal_map.HexagonalMap

class GameMapOverlay : OverlayBase() {
    val hexRadius = 25f
    val map = HexagonalMap(5, 5)
    override fun render(
        shape: ShapeRenderer,
        batch: SpriteBatch
    ) {
        shape.begin(ShapeRenderer.ShapeType.Line)
        shape.setColor(1f, 1f, 1f, 1f)
        for (h in map.allHexes()) {
            val (x, y) = h.axialToPixel(hexRadius)
            // shape.circle(x, y + screenHeight/2, hexSize)
            drawHex(shape, x, y + screenHeight / 2, hexRadius)
        }
        shape.end()

        batch.begin()
        font.setColor(1f, 1f, 1f, 1f)
        for (h in map.allHexes()) {
            val (x, y) = h.axialToPixel(hexRadius)
            font.draw(batch, "${h.q},${h.r}", x-10f, y + screenHeight/2 + 5f)
        }
        batch.end()
    }
}
