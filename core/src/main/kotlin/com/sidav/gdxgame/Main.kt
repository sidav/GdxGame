package com.sidav.gdxgame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.sidav.gdxgame.game.GameController

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms. */
class Main : ApplicationAdapter() {
    private val batch by lazy { SpriteBatch() }
    private val shape by lazy { ShapeRenderer() }
    val camera = OrthographicCamera()
    val viewport = ScreenViewport(camera)

    val gameController by lazy { GameController() }

    override fun create() {

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render() {
        val delta = Gdx.graphics.deltaTime // Time since last frame
        // LOGIC
        gameController.handleTap()

        // GL preparations
        viewport.apply()
        camera.update()
        shape.projectionMatrix = camera.combined
        batch.projectionMatrix = camera.combined
        // DRAW
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f)
        gameController.uiStack.render(shape, batch)
    }

    override fun dispose() {
        batch.dispose()
        shape.dispose()
    }
}
