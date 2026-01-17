package com.sidav.gdxgame.ui.overlays.combat

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.sidav.gdxgame.debugMsg
import com.sidav.gdxgame.events.BaseEvent
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.ui.elements.TapAreaCircle
import com.sidav.gdxgame.ui.elements.Window
import com.sidav.gdxgame.ui.input.Gesture
import com.sidav.gdxgame.ui.overlays.OverlayBase
import com.sidav.gdxgame.ui.overlays.Vec2
import com.sidav.gdxgame.ui.overlays.controller_requested.ManaPayOverlay.Events
import com.sidav.gdxgame.ui.overlays.drawCircleWithBorder

class CombatOverlay(val gameState: GameState) : OverlayBase() {
    val combatState = gameState.currentCombat!!
    val bottomOffset = 100f
    val ovlHeight = screenHeight - bottomOffset - 20f
    val enemyCirclesCoords = arrayOf(
        Vec2(0f, screenHeight - 120f),
        Vec2(120f, screenHeight - 120f),
        Vec2(0f, screenHeight - 240f),
        Vec2(120f, screenHeight - 240f),
    )

    val bg = Color(0.01f, 0f, 0f, 1f)

    val window = Window(0f, bottomOffset, screenWidth, ovlHeight, bgColor = bg)
    var enemiesTapCircles = mutableListOf<EnemyTapCircle>()

    init {
        combatState.enemies.forEachIndexed { index, enemy ->
            enemiesTapCircles.add(
                EnemyTapCircle(
                    enemyCirclesCoords[index].x,
                    enemyCirclesCoords[index].y,
                    100f,
                    enemy.token
                )
            )
        }
    }

    override fun render(
    ) {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        window.fillBackground(shape)
        window.drawBorders(shape)
        enemiesTapCircles.forEach { it.draw(shape) }
        shape.end()
        batch.begin()
        enemiesTapCircles.forEach { it.drawTexts(batch, layout, font) }
        batch.end()
    }

    override fun handleGesture(g: Gesture): BaseEvent {
        if (g is Gesture.Tap)
            enemiesTapCircles.forEach {
                if (it.hitByTap(g)) {
                    debugMsg("Success")
                    return Event.GestureCaughtWithNoAction
                }
            }

        if (window.rect.contains(g.x, g.y)) return Event.GestureCaughtWithNoAction

        return Event.NoEvent
    }
}
