package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.events.BaseEvent
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.game.hexagonal_map.AxialCoords
import com.sidav.gdxgame.game.hexagonal_map.Hex
import com.sidav.gdxgame.game.hexagonal_map.TerrainType
import com.sidav.gdxgame.game.hexagonal_map.worldToAxial
import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.ui.elements.TapArea
import com.sidav.gdxgame.ui.input.Gesture

class GameMapOverlay(val gameState: GameState) : OverlayBase() {
    var selectedHexCoords = AxialCoords.ZERO
    var selectedHex: Hex? = null
    val map = gameState.map
    val hexRadius = 25f
    var xOffset = 55f
    var yOffset = screenHeight / 3 - 5f

    var moveButton = TapArea(0f, 125f, 120f, 50f, "Move here")

    override fun render() {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.setColor(1f, 1f, 1f, 1f)
        for (h in map.allHexes()) {
            if (h.terrainHere == TerrainType.UNREVEALED) continue
            val (x, y) = h.axialCoords.pixelCoords(hexRadius)
//            shape.circle(x+xOffset, y + yOffset, hexRadius)
            drawHex(shape, x + xOffset, y + yOffset, hexRadius)
        }

        // Draw tapped rect as filled
        shape.setColor(0.1f, 0.3f, 0.1f, 1f)
        val (x, y) = selectedHexCoords.pixelCoords(hexRadius)
        drawHexFilled(shape, x + xOffset, y + yOffset, hexRadius)
        shape.setColor(0.5f, 1f, 0.5f, 1f)
        drawHex(shape, x + xOffset, y + yOffset, hexRadius)
        shape.end()

        batch.begin()
        font.setColor(1f, 1f, 1f, 1f)
        for (h in map.allHexes()) {
            drawHexText(batch, h)
        }
        batch.end()

        // Draw move button
        if (selectedHex != null && gameState.canPlayerMoveToTileInSingleStep(selectedHexCoords)) {
            val selectedHex = selectedHex;
            shape.begin(ShapeRenderer.ShapeType.Filled)
            moveButton.drawBackground(shape)
            moveButton.drawOutline(shape)
            shape.end()

            batch.begin()
            if (selectedHex?.featureHere?.isRampaging ?: false)
                moveButton.drawText(batch, layout, font, textOverride = "Challenge the enemy")
            else
                moveButton.drawText(batch, layout, font, textOverride = "Move here for ${gameState.getMoveCost(selectedHexCoords)} MPs")
            batch.end()
        }
    }

    fun drawHexText(batch: SpriteBatch, h: Hex) {
        if (h.terrainHere == TerrainType.UNREVEALED) return
        val (x, y) = h.axialCoords.pixelCoords(hexRadius)

        val terrainChar = when (h.terrainHere) {
            TerrainType.PORTAL -> "P"
            TerrainType.OCEAN -> "~"
            TerrainType.LAKE -> "~"
            TerrainType.PLAINS -> "."
            TerrainType.SWAMP -> ","
            TerrainType.FOREST -> "F"
            TerrainType.MOUNTAIN -> "^"
            TerrainType.HILLS -> "*"
            else -> "?"
        }

        val terrainColor = when (h.terrainHere) {
            TerrainType.PORTAL -> Color.MAGENTA
            TerrainType.OCEAN -> Color.SKY
            TerrainType.LAKE -> Color.CYAN
            TerrainType.PLAINS -> Color.WHITE
            TerrainType.SWAMP -> Color.GREEN
            TerrainType.FOREST -> Color.GREEN
            TerrainType.MOUNTAIN -> Color.WHITE
            else -> Color.FIREBRICK
        }
        font.color = terrainColor
        // font.draw(batch, h.axialCoords.toString(), x - 12f + xOffset, y + 7f + yOffset)
        font.draw(batch, terrainChar, x - 5f + xOffset, y - 7f + yOffset)

        // Draw monster tokens
        if (h.tokensHere.isNotEmpty()) {
            font.color = Color.RED
            font.draw(batch, h.tokensHere.joinToString {it.name.take(1)}, x - 5f + xOffset, y + 7f + yOffset)
        }

        if (gameState.player.position == h.axialCoords) {
            font.color = Color.WHITE
            font.draw(batch, "@", x - 5f + xOffset, y + 7f + yOffset)
        }
    }

    override fun handleGesture(g: Gesture): BaseEvent {
        when (g) {
            is Gesture.LongTap ->
                selectedHexCoords = worldToAxial(g.x - xOffset, g.y - yOffset, hexRadius)

            is Gesture.SwipeMove -> {
                xOffset = (xOffset + g.dx / 50).coerceIn(-1000f, 1000f)
                yOffset = (yOffset + g.dy / 50).coerceIn(-1000f, 2000f)
            }

            is Gesture.Tap -> {
                if (gameState.canPlayerMoveToTileInSingleStep(selectedHexCoords) && moveButton.hitByTap(g)) {
                    return GameEvent.PlayerTriesToMove(selectedHexCoords)
                }
                selectedHexCoords = worldToAxial(g.x - xOffset, g.y - yOffset, hexRadius)
                selectedHex = gameState.map.getHex(selectedHexCoords)
            }

            else -> {}
        }
        return Event.NoEvent
    }
}
