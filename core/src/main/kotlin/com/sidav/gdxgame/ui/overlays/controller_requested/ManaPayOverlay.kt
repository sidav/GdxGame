package com.sidav.gdxgame.ui.overlays.controller_requested

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.events.BaseEvent
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.game.mana.ManaStock
import com.sidav.gdxgame.ui.elements.TapArea
import com.sidav.gdxgame.ui.elements.WindowWithTitle
import com.sidav.gdxgame.ui.input.Gesture
import com.sidav.gdxgame.ui.overlays.OverlayBase
import com.sidav.gdxgame.ui.overlays.drawCircleWithBorder
import com.sidav.gdxgame.ui.overlays.drawDiamondWithBorder
import com.sidav.gdxgame.ui.overlays.getColorForMana

class ManaPayOverlay(val manaStock: ManaStock) : OverlayBase() {
    override val modal = true
    val dieRectSide = 40f
    val diceOffset = 10f

    val window = WindowWithTitle(
        screenWidth / 6,
        screenHeight / 3,
        4 * screenWidth / 6,
        screenHeight / 3,
        "Select mana to play"
    )

    val manaDice = manaStock.ambientManaDice
    val diceTapAreas = Array(manaDice.size) {
        TapArea(
            diceOffset + window.rect.x + (dieRectSide + diceOffset) * it,
            window.rect.y + window.heightWithoutTitlebar - dieRectSide - diceOffset,
            dieRectSide,
            dieRectSide,
            bgColor = getColorForMana(manaDice[it].color)
        )
    }

    val manaCrystals = manaStock.manaCrystals
    val crystalTapAreas = Array(manaCrystals.size) {
        TapArea(
            diceOffset + window.rect.x + (dieRectSide + diceOffset) * it,
            window.rect.y + window.heightWithoutTitlebar - 2 * (dieRectSide + diceOffset),
            dieRectSide,
            dieRectSide,
            bgColor = getColorForMana(manaCrystals[it].color)
        )
    }

    val manaTokens = manaStock.manaTokens
    val tokenTapAreas = Array(manaTokens.size) {
        TapArea(
            diceOffset + window.rect.x + (dieRectSide + diceOffset) * it,
            window.rect.y + window.heightWithoutTitlebar - 3 * (dieRectSide + diceOffset),
            dieRectSide,
            dieRectSide,
            bgColor = getColorForMana(manaTokens[it].color)
        )
    }

    val cancelButton = window.createButtonUnder(40f, "Cancel payment")

    override fun render() {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        window.fillBackground(shape)
        window.drawBorders(shape)
        for (i in diceTapAreas.indices) {
            diceTapAreas[i].drawBackground(shape)
            diceTapAreas[i].drawOutline(shape)
        }
        for (cta in crystalTapAreas) {
            drawDiamondWithBorder(
                shape,
                cta.rect.x,
                cta.rect.y,
                cta.rect.width,
                cta.rect.height,
                cta.bgColor,
                Color.WHITE,
                2f
            )
        }
        for (tta in tokenTapAreas) {
            drawCircleWithBorder(
                shape,
                tta.rect.x,
                tta.rect.y,
                tta.rect.width,
                tta.bgColor,
                Color.WHITE,
                2f,
            )
        }
        cancelButton.drawBackground(shape)
        cancelButton.drawOutline(shape)
        shape.end()

        batch.begin()
        window.drawHeaderText(batch, layout, font)
        cancelButton.drawText(batch, layout, font)
        batch.end()
    }

    override fun handleGesture(g: Gesture): BaseEvent {
        if (g !is Gesture.Tap) return Event.NoEvent

        for (i in 0 until diceTapAreas.size) {
            if (diceTapAreas[i].hitByTap(g)) {
                return GameEvent.PlayerPaysWithManaDie(manaDice[i], i)
            }
        }
        for (i in 0 until crystalTapAreas.size) {
            if (crystalTapAreas[i].hitByTap(g)) {
                return GameEvent.PlayerPaysWithManaCrystal(manaCrystals[i], i)
            }
        }
        for (i in 0 until tokenTapAreas.size) {
            if (tokenTapAreas[i].hitByTap(g)) {
                return GameEvent.PlayerPaysWithManaToken(manaTokens[i], i)
            }
        }
        if (cancelButton.hitByTap(g)) return Events.PaymentCanceled
        return Event.NoEvent
    }

    sealed class Events {
        object PaymentCanceled : UiEvent
    }
}
