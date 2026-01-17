package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.game.state.GameState

/** Shows current ambient mana, movement, leadership etc */
class ThisTurnDataOverlay(val gameState: GameState) : OverlayBase() {

    val bg = Color.DARK_GRAY
    val diceSide = 14f
    val diceHOffset = 7f
    val diceVOffset = 3f
    val ovlWidth = screenWidth * 4f/5f
    val ovlHeight = 20f

    override fun render(
        shape: ShapeRenderer,
        batch: SpriteBatch
    ) {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.color = bg
        shape.rect(0f, screenHeight - ovlHeight, ovlWidth, ovlHeight)
        drawThickRect(shape, 0f, Gdx.graphics.height.toFloat() - ovlHeight, ovlWidth, ovlHeight)
        // Dice rects
        for ((i, dice) in gameState.ambientManaDice.withIndex()) {
            shape.color = getColorForMana(dice.face)
            shape.rect(
                diceHOffset * (i + 1) + i * diceSide, screenHeight - ovlHeight + diceVOffset,
                diceSide, diceSide
            )
            shape.color = Color.WHITE
            drawThickRect(
                shape,
                diceHOffset * (i + 1) + i * diceSide, screenHeight - ovlHeight + diceVOffset,
                diceSide, diceSide, 2f
            )
        }
        shape.end()

//        shape.begin(ShapeRenderer.ShapeType.Line)
//        shape.end()

        batch.begin()
        font.draw(batch, "MOV ${gameState.playerStats.movement}", 100f, screenHeight - 5)
        font.draw(batch, "LDR ${gameState.playerStats.leadership}", 200f, screenHeight - 5)
        batch.end()
    }
}
