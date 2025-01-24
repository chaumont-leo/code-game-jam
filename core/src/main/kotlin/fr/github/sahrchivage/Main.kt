package fr.github.sahrchivage

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import fr.github.sahrchivage.screens.GameScreen
import fr.github.sahrchivage.screens.TitleScreen
import fr.github.sahrchivage.utils.getInternalTexture

class Main : Game() {
    lateinit var world: World
    lateinit var camera: OrthographicCamera
    lateinit var viewport: Viewport
    lateinit var batch: SpriteBatch
    // lateinit var player: Player

    override fun create() {
        camera = OrthographicCamera(
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat(),
        ).also { it.setToOrtho(false,
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat()) }
        println(Gdx.graphics.width)
        println(Gdx.graphics.height)
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
        viewport = FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera)
        batch = SpriteBatch()
        world = World(Vector2(0f, -10f), true)

        camera.update()
        setScreen(TitleScreen())
        screen.show()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.render(Gdx.graphics.deltaTime)

        camera.update()

        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(getInternalTexture("bg.png"), 0f, 0f)
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
    }

    fun startGame() {
        this.setScreen(GameScreen())
    }

    companion object {
        private var _instance: Main? = null

        fun getMain(): Main {
            if (_instance == null)
                _instance = Main()

            return _instance as Main
        }
    }
}
