package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.cards_library.WoundCard
import com.sidav.gdxgame.game.mana.ManaColor
import com.sidav.gdxgame.game.mana.ManaCost
import com.sidav.gdxgame.ui.elements.TapArea
import com.sidav.gdxgame.ui.elements.WindowWithTitle
import com.sidav.gdxgame.ui.input.Gesture

/**
 * Implements an overlay of full card overview, allows selecting the card and effect from it.
 */
class CardActionSelectOverlay(
    val cards: List<CardBase>,
    var currentCardIndex: Int
) : OverlayBase() {
    override val modal: Boolean = true

    val cardBgColor = Color(0.1f, 0.1f, 0.1f, 1f)
    val cardWidth = 240f
    val cardHeight = 400f
    val cardHeaderHeight = 30f
    val cardNoHeaderHeight = cardHeight - cardHeaderHeight

    val cardWindow by lazy {
        WindowWithTitle(
            screenCenter.x - cardWidth / 2,
            screenCenter.y - cardHeight / 2,
            cardWidth,
            cardHeight,
            cards[currentCardIndex].cardName,
            titlebarHeight = cardHeaderHeight
        )
    }
    val prevCardButton = TapArea(0f, screenCenter.y - 20f, 40f, 40f, "<", bgColor = cardBgColor)
    val nextCardButton =
        TapArea(screenWidth - 40, screenCenter.y - 20f, 40f, 40f, ">", bgColor = cardBgColor)
    val playEffectButton by lazy { cardWindow.createButtonUnder(40f, "Play this effect") }

    /** Tappable parts of a card containing an effect */
    var effectTapAreas = listOf<TapArea>()
    var currentSelectedEffectIndex: Int = -1
    private fun createTapAreas() {
        val card = cards[currentCardIndex]
        val effectsQty = card.effects.size
        val singleEffectAreaHeight = cardNoHeaderHeight / effectsQty
        effectTapAreas = card.effects.mapIndexed { index, effect ->
            TapArea(
                cardWindow.rect.x,
                cardWindow.rect.y + cardNoHeaderHeight - singleEffectAreaHeight * (index + 1),
                cardWidth, singleEffectAreaHeight, effect.effectText
            )
        }
    }

    init {
        createTapAreas()
    }

    override fun render() {
        setDrawObjects(shape, batch)

        when (val card = cards[currentCardIndex]) {
            else -> drawCard(card)
        }

        shape.begin(ShapeRenderer.ShapeType.Filled)
        if (currentCardIndex > 0) {
            prevCardButton.drawBackground(shape)
            prevCardButton.drawOutline(shape)
        }
        if (currentCardIndex < cards.lastIndex) {
            nextCardButton.drawBackground(shape)
            nextCardButton.drawOutline(shape)
        }
        if (currentSelectedEffectIndex != -1) {
            playEffectButton.drawBackground(shape)
            playEffectButton.drawOutline(shape)
        }
        shape.end()

        batch.begin()
        if (currentCardIndex > 0) prevCardButton.drawText(batch, layout, font)
        if (currentCardIndex < cards.lastIndex) nextCardButton.drawText(batch, layout, font)
        if (currentSelectedEffectIndex != -1) playEffectButton.drawText(batch, layout, font)
        batch.end()
    }

    private fun drawCard(card: CardBase) {
        // CARD RECTANGLE
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.color = cardBgColor
        cardWindow.fillBackground(shape)
        // Selected action rect
        if (currentSelectedEffectIndex >= 0) {
            shape.color = Color.OLIVE
            val rect = effectTapAreas[currentSelectedEffectIndex].rect
            shape.rect(rect.x, rect.y, rect.width, rect.height)
        }

        // CARD BORDERS
        shape.color = Color.WHITE
        cardWindow.drawBorders(shape)

        for (ta in effectTapAreas) {
            ta.drawOutline(shape)
        }

        // DROPLET FOR WOUND CARD
        if (card is WoundCard)
            drawDropletWithBorder(
                shape,
                cardWindow.rect.x + cardWindow.rect.width * 0.25f,
                cardWindow.rect.y + cardWindow.heightWithoutTitlebar * 0.15f,
                cardWindow.rect.width * 0.5f,
                cardWindow.heightWithoutTitlebar * 0.7f,
                Color.SCARLET,
                Color.GRAY,
                2f
            )

        shape.end()

        // TEXT
        batch.begin()

        // Header
        cardWindow.drawHeaderText(batch, layout, font)
        // Effects
        for (i in effectTapAreas.indices) {
            val ta = effectTapAreas[i]
            drawCostText(
                card.effects[i].cost,
                ta.midX(),
                ta.topY()
            )
            ta.drawText(batch, layout, font, font.lineHeight * 1.5f)
        }

        batch.end()
    }

    private fun drawCostText(cost: ManaCost, midX: Float, y: Float) {
        if (cost is ManaCost.SingleManaOfColor) {
            val color = getColorForMana(cost.costManaColor)
            val text = when (cost.costManaColor) {
                ManaColor.WHITE -> "[Pay White mana]"
                ManaColor.BLUE -> "[Pay Blue mana]"
                ManaColor.GREEN -> "[Pay Green mana]"
                ManaColor.RED -> "[Pay Red mana]"
                ManaColor.GOLD -> "[Pay Gold mana]"
                ManaColor.BLACK -> "[Pay Black mana]"
            }
            val costRectW = cardWidth - 60
            drawWrappedText(text, midX - costRectW / 2, y, costRectW, color)
        }
    }

    override fun handleGesture(g: Gesture): UiEvent {
        if (g !is Gesture.Tap) return OverlayBase.Event.NoEvent
        if (prevCardButton.hit(g.x, g.y)) {
            currentCardIndex = (currentCardIndex - 1).coerceIn(0, cards.lastIndex)
            currentSelectedEffectIndex = -1
            cardWindow.titleText = cards[currentCardIndex].cardName
            createTapAreas()
            return OverlayBase.Event.NoEvent
        }
        if (nextCardButton.hit(g.x, g.y)) {
            currentCardIndex = (currentCardIndex + 1).coerceIn(0, cards.lastIndex)
            currentSelectedEffectIndex = -1
            cardWindow.titleText = cards[currentCardIndex].cardName
            createTapAreas()
            return OverlayBase.Event.NoEvent
        }
        if (currentSelectedEffectIndex != -1 && playEffectButton.hit(g.x, g.y)) {
            val playedEffect = cards[currentCardIndex].effects[currentSelectedEffectIndex]
            debugMsg("Play effect: ${playedEffect.effectText}")
            return Event.EffectPlayTapped(cards[currentCardIndex], playedEffect)
        }
        if (cardWindow.rect.contains(g.x, g.y)) {
            for (i in effectTapAreas.indices) {
                val ta = effectTapAreas[i]
                if (ta.hit(g.x, g.y)) currentSelectedEffectIndex = i
            }
        } else {
            debugMsg("Card NOT tapped")
            return OverlayBase.Event.CloseThisOverlay
        }
        return OverlayBase.Event.NoEvent
    }

    sealed class Event {
        data class EffectPlayTapped(val effectSourceCard: CardBase, val effect: CardEffect) :
            UiEvent
    }
}
