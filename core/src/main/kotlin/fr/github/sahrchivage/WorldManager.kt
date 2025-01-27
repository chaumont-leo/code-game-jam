package fr.github.sahrchivage

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

class WorldManager {
    val world = World(WORLD_VECTOR, true)
    val camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private var accumulator = 0f

    fun doPhysicalStep(delta: Float) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        val frameTime = delta.coerceAtMost(0.25f)
        accumulator += frameTime
        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
            accumulator -= TIME_STEP
        }
    }

    fun dispose() {
        world.dispose()
    }
}
