package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.WoundCard
import com.sidav.gdxgame.ui.elements.TapArea
import com.sidav.gdxgame.ui.input.Gesture

class HandOverlay(
    private val cards: List<CardBase>,
) : OverlayBase() {

    val cardColor = Color(0x010101FF)
    val cardWidth = 80f
    val cardHeight = 100f
    val spacing = 6f
    var cardTapAreas = arrayOf<TapArea>()
    private var lastKnownCards: List<CardBase> = emptyList()

    override fun update() {
        if (cards == lastKnownCards) return
        lastKnownCards = cards.toList()

        cardTapAreas = Array(cards.size) {
            val xIncrement = if (cards.size <= 1)
                                0f
                            else
                                (screenWidth - cardWidth) / (cards.size - 1).toFloat()
            TapArea(
                it * xIncrement, 0f, cardWidth, cardHeight, cards[it].cardName, fgColor = when {
                    cards[it] is WoundCard -> Color.SCARLET
                    else -> getColorForMana(cards[it].getManaCostColor())
                }
            )
        }
    }

    override fun render() {
        setDrawObjects(shape, batch)
//        if (cardTapAreas.size != cards.size) createCardTapAreas()

        for (i in cardTapAreas.indices) {
            // CARD RECTANGLES
            shape.begin(ShapeRenderer.ShapeType.Filled)
            cardTapAreas[i].drawBackground(shape)
            cardTapAreas[i].drawOutline(shape)
            if (cards[i] is WoundCard) {
                val rect = cardTapAreas[i].rect
                drawDropletWithBorder(
                    shape,
                    rect.x + rect.width * 0.32f,
                    rect.y + rect.height * 0.2f,
                    rect.width * 0.36f,
                    rect.height * 0.6f,
                    Color.SCARLET,
                    Color.GRAY,
                    2f
                )
            }
            shape.end()

            // TEXT
            batch.begin()
            cardTapAreas[i].drawText(batch, layout, font)
            batch.end()

        }
    }

    override fun handleGesture(g: Gesture): UiEvent {
        if (g !is Gesture.Tap) return OverlayBase.Event.NoEvent

        if (g.y <= cardHeight) // Not tapped 100%, higher than cards' row
            for (i in cardTapAreas.indices.reversed()) {
                if (cardTapAreas[i].hit(g.x, g.y)) {
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
