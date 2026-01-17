package com.sidav.gdxgame.game

import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectEvent
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest
import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.ui.UiStack
import com.sidav.gdxgame.ui.overlays.ThisTurnDataOverlay
import com.sidav.gdxgame.ui.overlays.HandOverlay

class GameController {
    val gameState = GameState()
    val uiStack = UiStack(gameState)

    var controllerMode: Mode = Mode.AwaitingInput

    sealed interface Mode {
        object AwaitingInput : Mode
        class PlayingEffect(val currentCardPlayed: CardBase, val currentCardEffect: CardEffect) :
            Mode
    }

    init {
        uiStack.push(
            HandOverlay(
                gameState.playerHand.contents,
            )
        )
        uiStack.push(
            ThisTurnDataOverlay(gameState)
        )
    }


    private fun switchMode(newMode: Mode) {
        controllerMode = newMode
    }

    fun handleTap() {

        val event = uiStack.handleTap()
        if (event is GameEvent.NoEvent) return

        debugMsg("-> NEW CONTROLLER EVENT. Current mode is $controllerMode")
        debugMsg(" Game event received: ${event::class.simpleName}")

        when (controllerMode) {
            is Mode.AwaitingInput -> handleEventForAwaitingInputMode(event)
            is Mode.PlayingEffect -> handleEventForPlayingEffect(event)
        }

    }

    private fun handleEventForAwaitingInputMode(ev: GameEvent) {
        when (ev) {
            is GameEvent.EffectTriesToBePlayed -> {
                debugMsg("  Switching mode to \"PLAYING_EFFECT\"... Playing ${ev.effect.effectText}")
                switchMode(Mode.PlayingEffect(ev.effectSourceCard, ev.effect))
                advanceCurrentPlayedEffectAndHandleResult()
                uiStack.removeLastEventSource()
            }
        }
    }

    private fun handleEventForPlayingEffect(ev: GameEvent) {
        val mode = (controllerMode as Mode.PlayingEffect)
        debugMsg("  Got GameEvent: ${ev::class.simpleName}")
        if (ev is GameEvent.PaymentCanceled) {
            mode.currentCardEffect.resetState()
            switchMode(Mode.AwaitingInput)
            uiStack.removeLastEventSource()
        }
        if (ev is GameEvent.PlayerPaysWithManaDie) {
            mode.currentCardEffect.onEvent(CardEffectEvent.ManaPaid(ev.die))
            advanceCurrentPlayedEffectAndHandleResult()
        }
    }

    fun advanceCurrentPlayedEffectAndHandleResult() {
        val mode = (controllerMode as Mode.PlayingEffect)
        val result = advanceCardEffect(mode.currentCardEffect)
        if (result == EffectAdvanceResult.FINISHED) {
            gameState.playerHand.remove(mode.currentCardPlayed)
            switchMode(Mode.AwaitingInput)
        }
    }

    private enum class EffectAdvanceResult {
        CONTINUES,
        FINISHED
    }

    private fun advanceCardEffect(eff: CardEffect): EffectAdvanceResult {
        while (true) {
            val req = eff.currentRequest()
            debugMsg("  Current request for the effect is ${req::class.simpleName}")
            when (req) {
                CardEffectRequest.NoRequest -> {
                    eff.onEvent(CardEffectEvent.NoEvent)
                }

                is CardEffectRequest.RequestPayment -> {
                    debugMsg("Creating a new payment overlay")
                    uiStack.showManaPayOverlay()
                    return EffectAdvanceResult.CONTINUES
                }

                is CardEffectRequest.ConsumePayment -> {
                    uiStack.removeLastEventSource()
                    eff.onEvent(CardEffectEvent.PaymentConsumed)
                }

                is CardEffectRequest.ApplyStatChange -> {
                    gameState.applyStatChange(req.statChange)
                    eff.onEvent(CardEffectEvent.Applied)
                }

                is CardEffectRequest.ApplyUnimplemented -> {
                    eff.onEvent(CardEffectEvent.Applied)
                }

                CardEffectRequest.Finish -> {
                    eff.resetState()
                    return EffectAdvanceResult.FINISHED
                }
            }
        }
    }
}
