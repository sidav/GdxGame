package com.sidav.gdxgame.ui.overlays.combat

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import com.sidav.gdxgame.events.BaseEvent
import com.sidav.gdxgame.events.GameEvent
import com.sidav.gdxgame.game.state.GameState
import com.sidav.gdxgame.game.state.combat.AttackToken
import com.sidav.gdxgame.game.state.combat.BlockToken
import com.sidav.gdxgame.game.state.combat.CombatPhase
import com.sidav.gdxgame.ui.drawTextLine
import com.sidav.gdxgame.ui.elements.Window
import com.sidav.gdxgame.ui.input.Gesture
import com.sidav.gdxgame.ui.overlays.OverlayBase
import com.sidav.gdxgame.ui.overlays.Vec2
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

    var selectedTokenIndex: Int? = null

    val tokenRadius = 30f
    val tokenY = bottomOffset + 95f
    val tokenSpacing = 10f

    val nextPhaseButtonX = screenWidth - 135f
    val nextPhaseButtonY = bottomOffset + 5f
    val nextPhaseButtonW = 125f
    val nextPhaseButtonH = 40f

    init {
        rebuildEnemyCircles()
    }

    private fun rebuildEnemyCircles() {
        enemiesTapCircles.clear()
        combatState.enemies.forEachIndexed { index, enemy ->
            enemiesTapCircles.add(
                EnemyTapCircle(
                    enemyCirclesCoords[index].x,
                    enemyCirclesCoords[index].y,
                    100f,
                    enemy
                )
            )
        }
    }

    override fun update() {
        if (enemiesTapCircles.any { it.enemy !in combatState.enemies } ||
            enemiesTapCircles.size != combatState.enemies.size) {
            rebuildEnemyCircles()
            selectedTokenIndex = null
        }
        val sel = selectedTokenIndex
        if (sel != null && sel >= combatState.playerTokens.size) {
            selectedTokenIndex = null
        }
    }

    private fun tokenCx(index: Int) = tokenRadius + index * (tokenRadius * 2 + tokenSpacing)

    override fun render() {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        window.fillBackground(shape)
        window.drawBorders(shape)

        enemiesTapCircles.forEach { it.draw(shape) }

        combatState.playerTokens.forEachIndexed { index, token ->
            val cx = tokenCx(index)
            val isSelected = selectedTokenIndex == index
            val bgColor = when {
                isSelected -> Color(0.7f, 0.7f, 0f, 1f)
                token is AttackToken -> Color(0.5f, 0.05f, 0.05f, 1f)
                token is BlockToken -> Color(0.05f, 0.25f, 0.5f, 1f)
                else -> Color.DARK_GRAY
            }
            val borderColor = if (isSelected) Color.YELLOW else Color.WHITE
            val borderThickness = if (isSelected) 3f else 2f
            drawCircleWithBorder(
                shape,
                cx - tokenRadius, tokenY - tokenRadius,
                tokenRadius * 2,
                bgColor, borderColor, borderThickness
            )
        }

        // Next phase button background + border
        shape.setColor(Color(0.1f, 0.08f, 0f, 1f))
        shape.rect(nextPhaseButtonX, nextPhaseButtonY, nextPhaseButtonW, nextPhaseButtonH)
        shape.setColor(Color.WHITE)
        shape.rectLine(nextPhaseButtonX, nextPhaseButtonY, nextPhaseButtonX + nextPhaseButtonW, nextPhaseButtonY, 2f)
        shape.rectLine(nextPhaseButtonX, nextPhaseButtonY + nextPhaseButtonH, nextPhaseButtonX + nextPhaseButtonW, nextPhaseButtonY + nextPhaseButtonH, 2f)
        shape.rectLine(nextPhaseButtonX, nextPhaseButtonY, nextPhaseButtonX, nextPhaseButtonY + nextPhaseButtonH, 2f)
        shape.rectLine(nextPhaseButtonX + nextPhaseButtonW, nextPhaseButtonY, nextPhaseButtonX + nextPhaseButtonW, nextPhaseButtonY + nextPhaseButtonH, 2f)

        shape.end()

        batch.begin()

        enemiesTapCircles.forEach { it.drawTexts(batch, layout, font) }

        combatState.playerTokens.forEachIndexed { index, token ->
            val cx = tokenCx(index)
            val label = when (token) {
                is AttackToken -> {
                    val prefix = if (token.properties.contains(AttackToken.AttackProperty.RANGED)) "R" else "A"
                    "$prefix:${token.attackValue}"
                }
                is BlockToken -> "B:${token.defenseValue}"
                else -> "?"
            }
            layout.setText(font, label)
            font.draw(batch, layout, cx - layout.width / 2, tokenY + layout.height / 2)
        }

        // Phase label
        val phaseLabel = when (combatState.phase) {
            CombatPhase.RANGED -> "Phase: Ranged"
            CombatPhase.DEFEND -> "Phase: Block"
            CombatPhase.ATTACK -> "Phase: Melee"
        }
        drawTextLine(phaseLabel, 10f, bottomOffset + 155f, batch, layout, font, halign = Align.left)

        // Token header
        if (combatState.playerTokens.isNotEmpty()) {
            drawTextLine("Tokens:", 10f, bottomOffset + 140f, batch, layout, font, halign = Align.left)
        } else {
            drawTextLine("No tokens", 10f, bottomOffset + 140f, batch, layout, font, color = Color.GRAY, halign = Align.left)
        }

        // Selected token hint
        if (selectedTokenIndex != null) {
            drawTextLine("Tap enemy to apply", 10f, bottomOffset + 55f, batch, layout, font, color = Color.YELLOW, halign = Align.left)
        }

        // Next phase button label
        val btnText = if (combatState.phase == CombatPhase.ATTACK) "End Combat" else "Next Phase"
        layout.setText(font, btnText)
        font.draw(
            batch, layout,
            nextPhaseButtonX + (nextPhaseButtonW - layout.width) / 2,
            nextPhaseButtonY + nextPhaseButtonH / 2 + layout.height / 2
        )

        batch.end()
    }

    override fun handleGesture(g: Gesture): BaseEvent {
        if (g is Gesture.Tap) {
            // Next phase button
            if (g.x >= nextPhaseButtonX && g.x <= nextPhaseButtonX + nextPhaseButtonW &&
                g.y >= nextPhaseButtonY && g.y <= nextPhaseButtonY + nextPhaseButtonH) {
                return GameEvent.PlayerAdvancesCombatPhase
            }

            // Token selection
            combatState.playerTokens.forEachIndexed { index, _ ->
                val cx = tokenCx(index)
                val dx = g.x - cx
                val dy = g.y - tokenY
                if (dx * dx + dy * dy <= tokenRadius * tokenRadius) {
                    selectedTokenIndex = if (selectedTokenIndex == index) null else index
                    return Event.GestureCaughtWithNoAction
                }
            }

            // Enemy tap: apply selected token
            enemiesTapCircles.forEachIndexed { enemyIndex, circle ->
                if (circle.hitByTap(g)) {
                    val selIndex = selectedTokenIndex
                    if (selIndex != null && selIndex < combatState.playerTokens.size) {
                        selectedTokenIndex = null
                        return GameEvent.PlayerAppliesCombatTokenToEnemy(selIndex, enemyIndex)
                    }
                    return Event.GestureCaughtWithNoAction
                }
            }
        }

        if (window.rect.contains(g.x, g.y)) return Event.GestureCaughtWithNoAction
        return Event.NoEvent
    }
}
