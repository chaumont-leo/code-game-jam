package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport

class TitleScreen: ScreenAdapter() {
    private val stage = Stage(FitViewport(800f, 600f))

    override fun show() {
        Gdx.input.inputProcessor = stage
        val button = TextButton("Hello World", Skin(Gdx.files.internal("ui/uiskin.json")))
        button.setSize(200f, 50f)
        button.setPosition(
            stage.viewport.worldWidth / 2 - button.width / 2,
            stage.viewport.worldHeight / 2 - button.height / 2
        )
        button.setScale(5f)
        stage.addActor(button)
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true);
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
    }

    override fun hide() {
        stage.dispose()
    }
}
