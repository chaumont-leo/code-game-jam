package fr.github.sahrchivage.models

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import fr.github.sahrchivage.Main
import fr.github.sahrchivage.utils.getInternalTextureAtlas

data class ObstacleData(
    val x: Int,
    val y: Int,
    val width: Float,
    val height: Float,
    val textureIndex: String
)

class ObstaclePattern(
    val obstacles: List<Obstacle>,
    var speed: Float = 10f,
) {
    fun render(batch: SpriteBatch) {
        val scale = 3f
        obstacles.forEach {
            it.update(speed)
            batch.draw(
                it.region,
                it.x+500f, it.y,
                it.region.regionWidth / 2f, it.region.regionHeight / 2f,
                it.region.regionWidth.toFloat(), it.region.regionHeight.toFloat(),
                scale, scale, 0f)
        }
    }

    companion object {
        fun fromData(data: List<ObstacleData>): ObstaclePattern {
            val world = Main.getMain().worldManager.world
            val atlas = getInternalTextureAtlas("obstacles/tiles.atlas")
            val obstacles = data.map {
                Obstacle(
                    world,
                    it.x * it.width,
                    it.y * it.height,
                    it.width,
                    it.height,
                    it.textureIndex,
                    atlas,
                )
            }
            return ObstaclePattern(obstacles, 0f)
        }
    }
}
