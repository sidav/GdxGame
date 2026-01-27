package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.game.hexagonal_map.AxialCoords
import com.sidav.gdxgame.game.hexagonal_map.HexagonalMap
import com.sidav.gdxgame.game.hexagonal_map.flower_tiles.PORTAL_TILES
import com.sidav.gdxgame.game.hexagonal_map.worldToAxial

class GameMapOverlay : OverlayBase() {
    val hexRadius = 25f
    val map = HexagonalMap(6, 10)
    var xOffset = 55f
    var yOffset = screenHeight/2 - 5f

    init {
         map.placeFlowerTileAt(PORTAL_TILES.first(), AxialCoords(0, 1))
    }

    override fun render(
        shape: ShapeRenderer,
        batch: SpriteBatch
    ) {
        shape.begin(ShapeRenderer.ShapeType.Line)
        shape.setColor(1f, 1f, 1f, 1f)
        for (h in map.allHexes()) {
            val (x, y) = h.axialCoords.pixelCoords(hexRadius)
            // shape.circle(x, y + screenHeight/2, hexSize)
            drawHex(shape, x + xOffset, y + yOffset, hexRadius)
        }
        shape.end()

        batch.begin()
        font.setColor(1f, 1f, 1f, 1f)
        for (h in map.allHexes()) {
            val (q, r) = h.axialCoords
            val (x, y) = h.axialCoords.pixelCoords(hexRadius)
            var text = "A${q},${r}"
            // text = "${axialDistance(2, 2, h.q, h.r)}"
            // text = "$hq,$hr"
            if (h.axialCoords == tappedHexCoords)
                font.setColor(0f, 0.5f, 0f, 1f)
            else
                font.setColor(Color.WHITE)
            font.draw(batch, text, x - 10f + xOffset, y + 7f + yOffset)
            val (ox, oy) = map.axialToOffset(AxialCoords(q, r))
            font.draw(batch, "F$ox,$oy", x - 10f + xOffset, y - 5f + yOffset)
        }
        // Draw current hex info
        map.getHex(tappedHexCoords)?.let {
            font.draw(
                batch,
                it.terrainHere.name,
                0f,
                screenHeight / 3
            )
        }
        batch.end()
    }

    var tappedHexCoords = AxialCoords.ZERO
    override fun handleTap(x: Float, y: Float): UiEvent {
        tappedHexCoords = worldToAxial(x-xOffset, y-yOffset, hexRadius)
        return Event.NoEvent
    }
}
