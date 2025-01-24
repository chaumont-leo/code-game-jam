package fr.github.sahrchivage

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import fr.github.sahrchivage.models.Player
import fr.github.sahrchivage.screens.GameScreen

class Main : Game() {
    lateinit var world: World
    lateinit var renderer: ShapeRenderer
    lateinit var player: Player
    var accumulator: Float = 0f

    override fun create() {
        world = World(Vector2(0F, 0F), true)
        renderer = ShapeRenderer()
        player = Player()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        player.handleInput()


        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        val frameTime = Gdx.graphics.deltaTime.coerceAtMost(0.25f)
        accumulator += frameTime
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
            accumulator -= TIME_STEP
        }

        renderer.begin(ShapeRenderer.ShapeType.Filled)
        renderer.color = Color.GREEN
        renderer.rect(player.body.position.x, player.body.position.y, 50f, 50f)
        renderer.end()
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
