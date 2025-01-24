package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import fr.github.sahrchivage.Main

class GameScreen : ScreenAdapter() {
    private val stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
    private val main = Main.getMain()

    override fun show() {
        Gdx.input.inputProcessor = stage

    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
        main.worldManager.doPhysicalStep(delta)
    }

    override fun hide() {
        stage.dispose()
    }
}
