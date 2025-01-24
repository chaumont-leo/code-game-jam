package fr.github.sahrchivage.models

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

    override fun dispose() {
        this.shape.dispose()
    }
}
