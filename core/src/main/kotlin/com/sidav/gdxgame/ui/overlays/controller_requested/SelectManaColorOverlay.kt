package com.sidav.gdxgame.ui.overlays.controller_requested

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.events.BaseEvent
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaType
import com.sidav.gdxgame.ui.elements.TapArea
import com.sidav.gdxgame.ui.elements.WindowWithTitle
import com.sidav.gdxgame.ui.input.Gesture
import com.sidav.gdxgame.ui.overlays.OverlayBase
import com.sidav.gdxgame.ui.overlays.getColorForMana

/** This is NOT FOR PAYMENT. This is used for effects requiring to "select mana color" as part of the effect */
class SelectManaColorOverlay(manaType: ManaType) : OverlayBase() {
    override val modal = true
    val dieRectSide = 42f
    val diceOffset = 10f

    val window = WindowWithTitle(
        screenWidth / 6,
        screenHeight / 3,
        4 * screenWidth / 6,
        screenHeight / 3,
        "Select mana color"
    )

    val colorTapAreas = Array(ManaColor.entries.size) {
        val columns = 4
        val row = it / columns
        var col = it % columns + row
        TapArea(
            diceOffset + window.rect.x + (dieRectSide + diceOffset) * col,
            window.rect.y + window.heightWithoutTitlebar - (dieRectSide + 3*diceOffset) * (row + 1),
            dieRectSide,
            dieRectSide,
            bgColor = getColorForMana(ManaColor.entries[it])
        )
    }

    override fun render() {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        window.fillBackground(shape)
        window.drawBorders(shape)
        for (i in colorTapAreas.indices) {
            colorTapAreas[i].drawBackground(shape)
            colorTapAreas[i].drawOutline(shape)
        }
        shape.end()

        batch.begin()
        window.drawHeaderText(batch, layout, font)
        batch.end()
    }

    override fun handleGesture(g: Gesture): BaseEvent {
        if (g !is Gesture.Tap) return Event.NoEvent

        for (i in 0 until colorTapAreas.size) {
            if (colorTapAreas[i].hitByTap(g)) {
                return GameEvent.PlayerSelectsManaColor(ManaColor.entries[i])
            }
        }
        return Event.NoEvent
    }
}
