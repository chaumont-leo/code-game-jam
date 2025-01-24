package fr.github.sahrchivage.models

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.github.sahrchivage.Main

data class ObstacleData(
    val x: Int,
    val y: Int,
    val width: Float,
    val height: Float,
    val texturePath: String
)

class ObstaclePattern(
    val obstacles: List<Obstacle>,
    var speed: Float = 0f
) {
    fun render(batch: SpriteBatch) {
        obstacles.forEach {
            it.update(speed)
            batch.draw(it.texture, it.x, it.y)
        }
    }

    companion object {
        fun fromData(data: List<ObstacleData>): ObstaclePattern {
            val world = Main.getMain().worldManager.world
            val obstacles = data.map {
                Obstacle(
                    world,
                    it.x * it.width,
                    it.y * it.height,
                    it.width,
                    it.height,
                    it.texturePath
                )
            }
            return ObstaclePattern(obstacles, 0f)
        }
    }
}
