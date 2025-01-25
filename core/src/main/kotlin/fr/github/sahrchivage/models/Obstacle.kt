package fr.github.sahrchivage.models

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import fr.github.sahrchivage.Main
import fr.github.sahrchivage.utils.getInternalTexture

open class Obstacle(
    internal var world: World = Main.getMain().worldManager.world,
    var x: Float = 0f,
    var y: Float = 0f,
    val width: Float = 0f,
    val height: Float = 0f,
    private val textureIndex: String = "",
    private val atlas: TextureAtlas
) {
    lateinit var body: Body
    val region: TextureRegion = atlas.findRegion(textureIndex)

    init {
        createBody()
    }

    private fun createBody() {
        // Configurer le body Box2D
        val bodyDef = BodyDef().apply {
            type = BodyDef.BodyType.KinematicBody
            position.set(x+500f, y)
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

    fun update(speed: Float) {
        body.setLinearVelocity(-speed, 0f)
        x = body.position.x
        y = body.position.y
    }

    fun dispose() {
        body.fixtureList.forEach { _ -> world.destroyBody(body) }
    }
}
