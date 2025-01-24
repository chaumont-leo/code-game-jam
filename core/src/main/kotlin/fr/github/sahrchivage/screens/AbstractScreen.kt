package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import fr.github.sahrchivage.FONT_SIZE
import fr.github.sahrchivage.Main

abstract class AbstractScreen: ScreenAdapter() {
    protected val main = Main.getMain()
    protected val camera = main.worldManager.camera
    protected val viewport = ScreenViewport(camera)
    protected val stage = Stage(viewport)
    protected val font: BitmapFont

    init {
        stage.viewport = viewport

        val fontFile = Gdx.files.internal("ui/font/font.ttf")
        val generator = FreeTypeFontGenerator(fontFile)
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()

        parameter.size = FONT_SIZE
        parameter.color = Color.WHITE

        font = generator.generateFont(parameter)

        generator.dispose()
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
    }
}
