package fr.github.sahrchivage.models

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Json

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

    constructor() : this(emptyList(), 0f)

    companion object {
        fun load(file: FileHandle, world: World): ObstaclePattern {
            val json = Json()
            val pattern = json.fromJson(ObstaclePattern::class.java, file)
            pattern.obstacles.forEach { it.world = world }
            return pattern
        }
    }
}
