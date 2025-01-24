package fr.github.sahrchivage.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import fr.github.sahrchivage.Main

class Player : BaseModel()
{
    init {
        val bodyDef = BodyDef().apply { type = BodyDef.BodyType.DynamicBody }
        this.body = Main.getMain().world.createBody(bodyDef)
        this.shape = PolygonShape().apply { setAsBox(1f, 2f) }
        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        this.fixture = this.body.createFixture(fixtureDef)
    }

    fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.body.applyForce(Vector2(0f, -9.81f), body.position, true)
        }
    }

    override fun dispose() {
        this.shape.dispose()
    }
}
