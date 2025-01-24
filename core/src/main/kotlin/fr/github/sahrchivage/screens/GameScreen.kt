package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import fr.github.sahrchivage.Main
import fr.github.sahrchivage.PLAYER_HEIGHT
import fr.github.sahrchivage.PLAYER_WIDTH
import fr.github.sahrchivage.enums.AnimationEnum

class GameScreen : ScreenAdapter() {
    private val stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
    private val main = Main.getMain()
    private var renderer: ShapeRenderer? = null

    override fun show() {
        Gdx.input.inputProcessor = stage
        renderer = ShapeRenderer()
        main.player?.startAnimate(AnimationEnum.PLAYER_IDLE_ANIMATION)
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        main.player?.update()

        // ShapeRenderer to render player's position
        if (renderer != null) {
            Gdx.gl.glEnable(GL20.GL_BLEND)
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

            renderer!!.begin(ShapeRenderer.ShapeType.Filled)
            renderer!!.color = Color(Color.rgba8888(0f, 1f, 1f, 0.5f))
            if (main.player != null)
                renderer?.rect(main.player!!.body.position.x, main.player!!.body.position.y, PLAYER_WIDTH, PLAYER_HEIGHT)
            renderer!!.end()
        }

        // Stage
        stage.act(delta)
        stage.draw()
        main.worldManager.doPhysicalStep(delta)
    }

    override fun hide() {
        stage.dispose()
    }
}
