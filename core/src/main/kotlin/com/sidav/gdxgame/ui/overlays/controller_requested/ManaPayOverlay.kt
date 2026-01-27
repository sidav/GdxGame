package com.sidav.gdxgame.ui.overlays.controller_requested

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.game.mana.ManaDie
import com.sidav.gdxgame.ui.elements.TapArea
import com.sidav.gdxgame.ui.elements.WindowWithTitle
import com.sidav.gdxgame.ui.overlays.OverlayBase
import com.sidav.gdxgame.ui.overlays.getColorForMana

class ManaPayOverlay(val manaDice: List<ManaDie>) : OverlayBase() {
    override val modal = true
    val dieRectSide = 40f
    val diceOffset = 10f

    val window =
        WindowWithTitle(
            screenWidth / 6,
            screenHeight / 3,
            4 * screenWidth / 6,
            screenHeight / 3,
            "Select mana to play"
        )

    val diceTapAreas = Array(manaDice.size) {
        TapArea(
            diceOffset + window.rect.x + (dieRectSide + diceOffset) * it,
            window.rect.y + window.heightWithoutTitlebar - dieRectSide - diceOffset,
            dieRectSide,
            dieRectSide,
            bgColor = getColorForMana(manaDice[it].face)
        )
    }
    val cancelButton = window.createButtonUnder(40f, "Cancel payment")

    override fun render(
        shape: ShapeRenderer,
        batch: SpriteBatch
    ) {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        window.fillBackground(shape)
        window.drawBorders(shape)
        for (i in diceTapAreas.indices) {
            diceTapAreas[i].drawBackground(shape)
            diceTapAreas[i].drawOutline(shape)
            cancelButton.drawBackground(shape)
            cancelButton.drawOutline(shape)
        }
        shape.end()

        batch.begin()
        window.drawHeaderText(batch, layout, font)
        cancelButton.drawText(batch, layout, font)
        batch.end()
    }

    override fun handleTap(x: Float, y: Float): UiEvent {
        for (i in 0 until diceTapAreas.size) {
            if (diceTapAreas[i].hit(x, y)) {
                return Events.PayWithDieTapped(manaDice[i], i)
            }
        }
        if (cancelButton.hit(x, y))
            return Events.PaymentCanceled
        return Event.NoEvent
    }

    sealed class Events {
        object PaymentCanceled : UiEvent
        data class PayWithDieTapped(val die: ManaDie, val dieIndex: Int) : UiEvent
    }
}
