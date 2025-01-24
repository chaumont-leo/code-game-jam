package fr.github.sahrchivage.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import fr.github.sahrchivage.Main
import fr.github.sahrchivage.enums.AnimationEnum

class Player : BaseModel(), IAnimable
{
    init {
        val bodyDef = BodyDef().apply { type = BodyDef.BodyType.DynamicBody }
        this.body = Main.getMain().worldManager.world.createBody(bodyDef)
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
            this.body.applyForce(Vector2(0f, 10f), body.position, true)
        }
    }

    override fun startAnimate(animation: AnimationEnum) {
        when(animation) {
            AnimationEnum.PLAYER_IDLE_ANIMATION -> {}
            AnimationEnum.PLAYER_WALK_ANIMATION -> {}
        }
    }

    override fun dispose() {
        this.shape.dispose()
    }
}
