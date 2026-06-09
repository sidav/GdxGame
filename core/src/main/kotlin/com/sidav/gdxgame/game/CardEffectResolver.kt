package com.sidav.gdxgame.game

import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.game.cards_library.CardBase
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffect
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectEvent
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest
import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.ui.UiStack

class CardEffectResolver(
    private val state: GameState,
    private val uiStack: UiStack
) {
    sealed interface Result {
        object Continues : Result
        object Finished : Result
    }

    private var currentCard: CardBase? = null
    private var currentEffect: CardEffect? = null

    fun start(card: CardBase, effect: CardEffect): Result {
        currentCard = card
        currentEffect = effect
        return advance()
    }

    fun handleEvent(ev: GameEvent): Result {
        val effect = currentEffect ?: error("No effect in progress")
        when (ev) {
            is GameEvent.PaymentCanceled -> {
                effect.resetState()
                cleanup()
                uiStack.removeLastEventSource()
                return Result.Finished
            }
            is GameEvent.PlayerPaysWithManaDie ->
                effect.onEvent(CardEffectEvent.ManaPaid(ev.die))
            is GameEvent.PlayerPaysWithManaCrystal ->
                effect.onEvent(CardEffectEvent.ManaPaid(ev.crystal))
            is GameEvent.PlayerPaysWithManaToken ->
                effect.onEvent(CardEffectEvent.ManaPaid(ev.token))
            is GameEvent.PlayerSelectsManaColor -> {
                uiStack.removeLastEventSource()
                effect.onEvent(CardEffectEvent.ManaColorSelected(ev.color))
            }
            else -> error("Unhandled event in CardEffectResolver: ${ev::class.simpleName}")
        }
        return advance()
    }

    private fun advance(): Result {
        val effect = currentEffect ?: error("No effect in progress")
        while (true) {
            val req = effect.currentRequest()
            debugMsg("CARD_EFFECT", "Current request: ${req::class.simpleName}")
            when (req) {
                CardEffectRequest.NoRequest ->
                    effect.onEvent(CardEffectEvent.NoEvent)
                is CardEffectRequest.RequestPayment -> {
                    uiStack.showManaPayOverlay()
                    return Result.Continues
                }
                is CardEffectRequest.RequestManaColorSelection -> {
                    uiStack.showSelectManaColorOverlay(req.manaType)
                    return Result.Continues
                }
                is CardEffectRequest.ConsumePayment -> {
                    uiStack.removeLastEventSource()
                    state.manaStock.consume(req.paid)
                    effect.onEvent(CardEffectEvent.PaymentConsumed)
                }
                is CardEffectRequest.ApplyStatChange -> {
                    state.player.stats.applyStatChange(req.statChange)
                    effect.onEvent(CardEffectEvent.Applied(req))
                }
                is CardEffectRequest.GivePlayerAManaCrystal -> {
                    state.manaStock.addCrystal(req.color)
                    effect.onEvent(CardEffectEvent.Applied(req))
                }
                is CardEffectRequest.GivePlayerAManaToken -> {
                    state.manaStock.addToken(req.color)
                    effect.onEvent(CardEffectEvent.Applied(req))
                }
                is CardEffectRequest.GivePlayerCombatToken -> {
                    val cmb = state.currentCombat
                    cmb?.playerTokens?.add(req.combatToken)
                    effect.onEvent(CardEffectEvent.Applied(req))
                }
                is CardEffectRequest.DrawCards -> {
                    state.player.drawCards(req.howMany)
                    effect.onEvent(CardEffectEvent.Applied(req))
                }
                is CardEffectRequest.HealWounds -> {
                    state.player.healWounds(req.howMany)
                    effect.onEvent(CardEffectEvent.Applied(req))
                }
                is CardEffectRequest.ApplyUnimplemented ->
                    effect.onEvent(CardEffectEvent.Applied(req))
                CardEffectRequest.Finish -> {
                    state.player.discardCard(currentCard!!)
                    cleanup()
                    return Result.Finished
                }
            }
        }
    }

    private fun cleanup() {
        currentCard = null
        currentEffect = null
    }
}
