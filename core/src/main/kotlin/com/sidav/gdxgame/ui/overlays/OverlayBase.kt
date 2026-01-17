package com.sidav.gdxgame.ui.overlays

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Align
import com.sidav.gdxgame.events.BaseEvent
import com.sidav.gdxgame.events.UiEvent
import com.sidav.gdxgame.ui.input.Gesture

abstract class OverlayBase {


    val font by lazy { BitmapFont() }
    val layout by lazy { GlyphLayout() }

    open val modal: Boolean = false

    lateinit var shape: ShapeRenderer
    lateinit var batch: SpriteBatch

    open fun update() {}
    abstract fun render()
    protected fun setDrawObjects(shp: ShapeRenderer, btc: SpriteBatch) {
        shape = shp
        batch = btc
    }

    // Returns anything else than NoEvent if gesture is "consumed" (did something)
    open fun handleGesture(g: Gesture): BaseEvent = Event.NoEvent

    open fun dispose() {
        font.dispose()
    }

    protected fun drawWrappedText(
        text: String,
        x: Float,
        y: Float,
        width: Float,
        color: Color = Color.WHITE,
        halign: Int = Align.center
    ) {
        com.sidav.gdxgame.ui.drawWrappedText(text, x, y, width, batch, layout, font, color, halign)
    }

    val screenWidth by lazy { Gdx.graphics.width.toFloat() }
    val screenHeight by lazy { Gdx.graphics.height.toFloat() }
    val screenCenter by lazy { centerOfScreen() }
    private fun centerOfScreen(): Vector2 = Vector2(
        (Gdx.graphics.width).toFloat() / 2f,
        (Gdx.graphics.height).toFloat() / 2f
    )

    sealed class Event {
        object NoEvent : UiEvent
        object GestureCaughtWithNoAction : UiEvent
        object CloseThisOverlay : UiEvent
    }
}
