package com.sidav.gdxgame.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.ui.overlays.OverlayBase
import com.sidav.gdxgame.ui.overlays.CardActionSelectOverlay
import com.sidav.gdxgame.ui.overlays.HandOverlay
import com.sidav.gdxgame.ui.overlays.controller_requested.ManaPayOverlay

class UiStack(val gameState: GameState) {
    private val overlays = mutableListOf<OverlayBase>()
    private var lastEventSourceOverlay: OverlayBase? = null

    // Debug vars for tap position output
    private var touchX: Float = 0f
    private var touchY: Float = 0f

    fun push(ovl: OverlayBase) {
        overlays.add(ovl)
    }

    fun pop(): OverlayBase? =
        if (overlays.isNotEmpty()) overlays.removeAt(overlays.lastIndex) else null

    fun top(): OverlayBase? =
        overlays.lastOrNull()

    fun forEach(block: (OverlayBase) -> Unit) {
        overlays.forEach(block)
    }

    fun removeLastEventSource() {
        overlays.remove(lastEventSourceOverlay)
    }

    fun render(shape: ShapeRenderer, batch: SpriteBatch) {
        for (ovl in overlays) {
            ovl.update()
            ovl.render(shape, batch)
        }

        // Draw last touch position
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.color = Color(0.5f, 0f, 0.5f, 0.5f)
        shape.circle(touchX, touchY, 5f)
        shape.end()
    }

    fun handleTap(): GameEvent {
        if (Gdx.input.justTouched()) {
            touchX = Gdx.input.x.toFloat()
            touchY = Gdx.graphics.height - Gdx.input.y.toFloat()
            debugMsg("x=$touchX y=$touchY")

            for (i in overlays.indices.reversed()) {
                val ovl = overlays[i]
                ovl.handleTap(touchX, touchY).let {
                    if (it !is OverlayBase.Event.NoEvent) {
                        lastEventSourceOverlay = overlays[i]
                        return handleOverlayUiEvent(ovl, it)
                    }
                }
                if (ovl.modal) break
            }
        }
        return GameEvent.NoEvent
    }

    fun handleOverlayUiEvent(overlay: OverlayBase, event: UiEvent): GameEvent {
        debugMsg("Received an event ${event::class.simpleName} from ${overlay::class.simpleName}")
        when (event) {
            is OverlayBase.Event.CloseThisOverlay -> {
                overlays.remove(overlay)
            }

            is HandOverlay.Event.CardTapped -> {
                push(CardActionSelectOverlay(gameState.playerHand.contents, event.cardIndex))
            }

            is CardActionSelectOverlay.Event.EffectPlayTapped -> {
                return GameEvent.EffectTriesToBePlayed(event.effectSourceCard, event.effect)
            }

            is ManaPayOverlay.Events.PaymentCanceled -> return GameEvent.PaymentCanceled
            is ManaPayOverlay.Events.PayWithDieTapped -> {
                return GameEvent.PlayerPaysWithManaDie(event.die, event.dieIndex)
            }

            else -> {
                debugMsg("Event was not handled.")
            }
        }
        return GameEvent.NoEvent
    }

    fun showManaPayOverlay() {
        if (top() !is ManaPayOverlay)
            push(ManaPayOverlay(gameState.ambientManaDice))
    }
}
