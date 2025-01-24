package fr.github.sahrchivage.models

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import fr.github.sahrchivage.utils.getInternalTexture

open class Obstacle(
    private val world: World,
    var x: Float,
    var y: Float,
    val width: Float,
    val height: Float,
    private val texturePath: String,
    private var speed: Float = 0f
) {
    lateinit var body: Body
    val texture = getInternalTexture(texturePath)

    init {
        createBody()
    }

    private fun createBody() {
        // Configurer le body Box2D
        val bodyDef = BodyDef().apply {
            type = BodyDef.BodyType.KinematicBody
            position.set(x, y)
        }

        // Créer la forme de collision
        val shape = PolygonShape().apply {
            setAsBox(width / 2, height / 2)
        }

        // Définir les propriétés physiques
        val fixtureDef = FixtureDef().apply {
            this.shape = shape
            density = 0f
            friction = 0.5f
            restitution = 0f
        }

        // Créer le body
        body = world.createBody(bodyDef)
        body.createFixture(fixtureDef)
    }

    fun update() {
        body.setLinearVelocity(-speed, 0f)
        x = body.position.x
        y = body.position.y
    }

    fun setSpeed(newSpeed: Float) {
        speed = newSpeed
    }

    fun dispose() {
        body.fixtureList.forEach { world.destroyBody(body) }
        texture.dispose()
    }
}
