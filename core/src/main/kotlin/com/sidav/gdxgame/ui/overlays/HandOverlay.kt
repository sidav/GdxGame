package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.ui.elements.TapArea

class HandOverlay(
    private val cards: List<CardBase>,
) : OverlayBase() {

    val cardColor = Color(0x010101FF)
    val cardWidth = 80f
    val cardHeight = 140f
    val spacing = 6f
    var cardTapAreas = arrayOf<TapArea>()

    override fun update() {
        if (cardTapAreas.size == cards.size) return
        cardTapAreas = Array(cards.size) {
            val xIncrement = (screenWidth - cardWidth) / (cards.size - 1).toFloat()
            TapArea(
                it * xIncrement,
                0f,
                cardWidth,
                cardHeight,
                cards[it].cardName,
                fgColor = getColorForMana(cards[it].getManaCostColor())
            )
        }
    }

    override fun render(shape: ShapeRenderer, batch: SpriteBatch) {
        setDrawObjects(shape, batch)
//        if (cardTapAreas.size != cards.size) createCardTapAreas()

        for (i in cardTapAreas.indices) {
            // CARD RECTANGLES
            shape.begin(ShapeRenderer.ShapeType.Filled)
            cardTapAreas[i].drawBackground(shape)
            cardTapAreas[i].drawOutline(shape)
            shape.end()

            // TEXT
            batch.begin()
            cardTapAreas[i].drawText(batch, layout, font)
            batch.end()
        }
    }

    override fun handleTap(x: Float, y: Float): UiEvent {
        if (y <= cardHeight) // Not tapped 100%, higher than cards' row
            for (i in cardTapAreas.indices.reversed()) {
                if (cardTapAreas[i].hit(x, y)) {
                    debugMsg("${cards[i].cardName} tapped.")
                    return Event.CardTapped(cards[i], i)
                }
            }
        return OverlayBase.Event.NoEvent;
    }

    sealed class Event {
        data class CardTapped(val card: CardBase, val cardIndex: Int) : UiEvent
    }
}
