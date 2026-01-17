package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.DeedCard
import com.sidav.gdxgame.game.mana.Mana
import com.sidav.gdxgame.game.mana.ManaCost
import com.sidav.gdxgame.ui.elements.TapArea
import com.sidav.gdxgame.ui.elements.WindowWithTitle

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

    override fun render(
        shape: ShapeRenderer,
        batch: SpriteBatch
    ) {
        setDrawObjects(shape, batch)
        when (val card = cards[currentCardIndex]) {
            is DeedCard -> {
                drawDeedCard(card)
            }

            else -> TODO()
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

    private fun drawDeedCard(card: DeedCard) {
        val cardTextHeight = (cardHeight - cardHeaderHeight) / 2

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
        shape.end()

        shape.begin(ShapeRenderer.ShapeType.Line)
        for (ta in effectTapAreas) {
            ta.drawOutline(shape)
        }
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
        if (cost is ManaCost.OneMana) {
            val color = getColorForMana(cost.costManaColor)
            val text = when (cost.costManaColor) {
                Mana.WHITE -> "[Pay White mana]"
                Mana.BLUE -> "[Pay Blue mana]"
                Mana.GREEN -> "[Pay Green mana]"
                Mana.RED -> "[Pay Red mana]"
                Mana.GOLD -> "[Pay Gold mana]"
                Mana.BLACK -> "[Pay Black mana]"
            }
            val costRectW = cardWidth - 60
            drawWrappedText(text, midX - costRectW / 2, y, costRectW, color)
        }
    }

    override fun handleTap(x: Float, y: Float): UiEvent {
        if (prevCardButton.hit(x, y)) {
            currentCardIndex = (currentCardIndex - 1).coerceIn(0, cards.lastIndex)
            currentSelectedEffectIndex = -1
            cardWindow.titleText = cards[currentCardIndex].cardName
            createTapAreas()
            return OverlayBase.Event.NoEvent
        }
        if (nextCardButton.hit(x, y)) {
            currentCardIndex = (currentCardIndex + 1).coerceIn(0, cards.lastIndex)
            currentSelectedEffectIndex = -1
            cardWindow.titleText = cards[currentCardIndex].cardName
            createTapAreas()
            return OverlayBase.Event.NoEvent
        }
        if (currentSelectedEffectIndex != -1 && playEffectButton.hit(x, y)) {
            val playedEffect = cards[currentCardIndex].effects[currentSelectedEffectIndex]
            debugMsg("Play effect: ${playedEffect.effectText}")
            return Event.EffectPlayTapped(cards[currentCardIndex], playedEffect)
        }
        if (cardWindow.rect.contains(x, y)) {
            for (i in effectTapAreas.indices) {
                val ta = effectTapAreas[i]
                if (ta.hit(x, y)) currentSelectedEffectIndex = i
            }
        } else {
            debugMsg("Card NOT tapped")
            return OverlayBase.Event.CloseThisOverlay
        }
        return OverlayBase.Event.NoEvent
    }

    sealed class Event {
        data class EffectPlayTapped(val effectSourceCard: CardBase, val effect: CardEffect) : UiEvent
    }
}
