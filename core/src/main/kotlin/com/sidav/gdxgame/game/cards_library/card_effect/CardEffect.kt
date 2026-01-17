package com.sidav.gdxgame.game.cards_library.card_effect

import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.game.cards_library.card_effect.CardEffectRequest.*
import com.sidav.gdxgame.game.mana.ManaCost

open class CardEffect(
    val effectText: String,
    val cost: ManaCost = ManaCost.Free
) {
    enum class States {
        INITIAL_STATE,
        WAITING_FOR_PAYMENT,
        PAYMENT_ACCEPTED,
        APPLYING,
        DONE
    }

    private var state = States.INITIAL_STATE
    open fun resetState() {
        switchState(States.INITIAL_STATE)
    }

    fun switchState(newState: States) {
        debugMsg("CARD_EFFECT", "EV: State of effect is switched: $state->$newState")
        state = newState
    }

    /** Returns a request (what's needed to happen in order for the effect to proceed */
    fun currentRequest(): CardEffectRequest {
        val req = when (state) {
            States.INITIAL_STATE -> NoRequest
            States.WAITING_FOR_PAYMENT -> RequestPayment(cost)
            States.PAYMENT_ACCEPTED -> ConsumePayment(cost)
            States.APPLYING -> requestOnApplying()
            States.DONE -> Finish
        }
        debugMsg("CARD_EFFECT","Current effect is ${this::class.simpleName}, in state ${state}, requesting ${req::class.simpleName}")
        return req
    }


    open fun requestOnApplying(): CardEffectRequest {
        debugMsg("CARD_EFFECT", "(not an error): Nothing really requested, stub method called")
        return ApplyUnimplemented
    }

    fun onEvent(event: CardEffectEvent) {
        debugMsg(
            "CARD_EFFECT",
            "Effect ${this::class.simpleName} (state $state) received an event: ${event::class.simpleName}"
        )
        // Thread.sleep(1000)
        when (state) {
            States.INITIAL_STATE -> {
                if (event is CardEffectEvent.NoEvent) {
                    if (cost is ManaCost.Free)
                        switchState(States.APPLYING)
                    else
                        switchState(States.WAITING_FOR_PAYMENT)
                }
            }

            States.WAITING_FOR_PAYMENT -> onWaitingForPayment(event)

            States.PAYMENT_ACCEPTED -> {
                if (event is CardEffectEvent.PaymentConsumed) {
                    switchState(States.APPLYING)
                }
            }

            States.APPLYING -> onApplying(event)
            States.DONE -> error("This code should never be reached")
        }
    }

    open fun onWaitingForPayment(ev: CardEffectEvent) {
        if (ev is CardEffectEvent.ManaPaid && cost.acceptsAsPayment(ev.manaSource)) {
            switchState(States.PAYMENT_ACCEPTED)
        }
    }

    open fun onApplying(ev: CardEffectEvent) {
        if (ev is CardEffectEvent.Applied)
            debugMsg("CARD_EFFECT", "(not an error): Nothing really applied, stub method called")
            switchState(States.DONE)
    }
}
