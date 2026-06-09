package com.sidav.gdxgame.game

import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.ui.UiStack

class GameController {
    val gameState = GameState()
    val uiStack = UiStack(gameState)

    val cardEffectResolver = CardEffectResolver(gameState, uiStack)
    val combatResolver = CombatResolver(gameState, uiStack)

    var controllerMode: Mode = Mode.AwaitingInput

    sealed interface Mode {
        object AwaitingInput : Mode
        object PlayingEffect : Mode
    }

    init {
        uiStack.initGameView(gameState)
    }

    fun handleInput() {
        val event = uiStack.handleInput()
        if (event is GameEvent.NoEvent) return

        debugMsg("-> CTRL","NEW CONTROLLER EVENT. Current mode is $controllerMode")
        debugMsg("CTRL"," Game event received: ${event::class.simpleName}")

        when (controllerMode) {
            is Mode.AwaitingInput -> handleEventForAwaitingInputMode(event)
            is Mode.PlayingEffect -> handleEventForPlayingEffect(event)
        }
    }

    private fun handleEventForAwaitingInputMode(ev: GameEvent) {
        when (ev) {
            is GameEvent.EffectTriesToBePlayed -> {

                val isApplicable = CardEffectApplicabilityResolver(gameState).canCardEffectBePlayedNow(ev.effect)
                if (!isApplicable) return

                debugMsg("Switching mode to PLAYING_EFFECT... Playing ${ev.effect.effectText}")
                controllerMode = Mode.PlayingEffect
                val resolverResult = cardEffectResolver.start(ev.effectSourceCard, ev.effect)
                if (resolverResult == CardEffectResolver.Result.Finished) {
                    debugMsg("Switching mode to AWAITING INPUT: effect resolved instantly")
                    controllerMode = Mode.AwaitingInput
                }
                uiStack.removeLastEventSource()
            }
            is GameEvent.PlayerTriesToMove -> {
                val moved = PlayerMoveResolver(gameState).execute(ev.newCoords)
                if (moved) {
                    if (combatResolver.combatShouldStart()) {
                        combatResolver.initCombat()
                    }
                }
            }
        }
    }

    private fun handleEventForPlayingEffect(ev: GameEvent) {
        val result = cardEffectResolver.handleEvent(ev)
        if (result == CardEffectResolver.Result.Finished)
            controllerMode = Mode.AwaitingInput
    }
}
