package com.sidav.gdxgame.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.game.mana.ManaType
import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.ui.input.Gesture
import com.sidav.gdxgame.ui.input.GestureTracker
import com.sidav.gdxgame.ui.overlays.OverlayBase
import com.sidav.gdxgame.ui.overlays.CardActionSelectOverlay
import com.sidav.gdxgame.ui.overlays.CurrentHighlightedTileOverlay
import com.sidav.gdxgame.ui.overlays.GameMapOverlay
import com.sidav.gdxgame.ui.overlays.HandOverlay
import com.sidav.gdxgame.ui.overlays.ThisTurnDataOverlay
import com.sidav.gdxgame.ui.overlays.combat.CombatOverlay
import com.sidav.gdxgame.ui.overlays.controller_requested.ManaPayOverlay
import com.sidav.gdxgame.ui.overlays.controller_requested.SelectManaColorOverlay

class UiStack(val gameState: GameState) {
    private val overlays = mutableListOf<OverlayBase>()
    private var lastEventSourceOverlay: OverlayBase? = null
    private var mapOverlay: GameMapOverlay? = null

    private val gestureTracker = GestureTracker()
    private var lastGesture: Gesture? = null

    fun initGameView(gameState: GameState) {
        val mo = GameMapOverlay(gameState)
        mapOverlay = mo
        push(mo)
        push(HandOverlay(gameState.player.playerHand.contents))
        push(ThisTurnDataOverlay(gameState))
        push(CurrentHighlightedTileOverlay(mo))
    }

    fun push(ovl: OverlayBase) {
        overlays.add(ovl)
    }

    fun pop(): OverlayBase? =
        if (overlays.isNotEmpty()) overlays.removeAt(overlays.lastIndex) else null

    val top: OverlayBase?
        get() = overlays.lastOrNull()

    fun forEach(block: (OverlayBase) -> Unit) {
        overlays.forEach(block)
    }

    fun removeLastEventSource() {
        overlays.remove(lastEventSourceOverlay)
    }

    fun render(shape: ShapeRenderer, batch: SpriteBatch) {
        for (ovl in overlays) {
            ovl.shape = shape
            ovl.batch = batch
            ovl.update()
            ovl.render()
        }

        // Draw last gesture position
        drawLastGesture(shape)
    }

    fun drawLastGesture(shape: ShapeRenderer) {
        val g = lastGesture ?: return
        shape.begin(ShapeRenderer.ShapeType.Filled)
        if (g is Gesture.SwipeMove) {
            shape.line(g.x, g.y, g.x + g.dx, g.y + g.dy)
        }
        shape.color = Color(0.5f, 0f, 0.5f, 0.5f)
        shape.circle(g.x, g.y, if (g is Gesture.LongTap) 15f else 5f)
        shape.end()
    }

    fun handleInput(): GameEvent {
        val gesture = gestureTracker.poll() ?: return GameEvent.NoEvent
        lastGesture = gesture
        for (i in overlays.indices.reversed()) {
            val ovl = overlays[i]
            ovl.handleGesture(gesture).let {
                if (it is GameEvent.NoEvent) error("UI overlays aren't allowed to send NoEvent as GameEvent!")
                when {
                    it is GameEvent -> {
                        lastEventSourceOverlay = overlays[i]
                        return it
                    }
                    // This means the overlay caught the gesture, did no action, but gesture should not be passed further down the stack
                    it is OverlayBase.Event.GestureCaughtWithNoAction -> break

                    it is UiEvent && it !is OverlayBase.Event.NoEvent -> {
                        lastEventSourceOverlay = overlays[i]
                        return handleOverlayUiEvent(ovl, it)
                    }
                }
            }
            if (ovl.modal) break
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
                push(CardActionSelectOverlay(gameState.player.playerHand.contents, event.cardIndex))
            }

            is CardActionSelectOverlay.Event.EffectPlayTapped -> {
                return GameEvent.EffectTriesToBePlayed(event.effectSourceCard, event.effect)
            }

            is ManaPayOverlay.Events.PaymentCanceled -> return GameEvent.PaymentCanceled

            else -> {
                debugMsg("Event was not handled.")
            }
        }
        return GameEvent.NoEvent
    }

    fun showManaPayOverlay() {
        if (top !is ManaPayOverlay) push(ManaPayOverlay(gameState.manaStock))
    }

    fun showSelectManaColorOverlay(manaType: ManaType) {
        if (top !is SelectManaColorOverlay) push(SelectManaColorOverlay(manaType))
    }

    fun showCombatOverlay() {
        if (top !is CombatOverlay) push(CombatOverlay(gameState))
    }
}
