package fr.github.sahrchivage.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import fr.github.sahrchivage.*
import fr.github.sahrchivage.enums.AnimationEnum
import fr.github.sahrchivage.utils.SpriteAnimator
import fr.github.sahrchivage.utils.getInternalTextureAtlas

class Player : BaseModel(), IAnimable
{
    private val idleAnimation = SpriteAnimator.createPlayerAnimation(2, 1, getInternalTextureAtlas("player/PlayerSprites.atlas").findRegion("Idle"), 0.3f)
    private val walkAnimation = SpriteAnimator.createPlayerAnimation(4, 1, getInternalTextureAtlas("player/PlayerSprites.atlas").findRegion("Run"), 0.2f)
    private val jumpAnimation = SpriteAnimator.createPlayerAnimation(11, 1, getInternalTextureAtlas("player/PlayerSprites.atlas").findRegion("Jump"), 0.1f, Animation.PlayMode.NORMAL)

    private var currentAnimation: SpriteAnimator? = null

    init {
        val bodyDef = BodyDef().apply { type = BodyDef.BodyType.DynamicBody }
        bodyDef.position.set(Vector2(10f, 50f))

        body = Main.getMain().worldManager.world.createBody(bodyDef)
        shape = PolygonShape().apply { setAsBox(1f, 1f) }

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f
        fixtureDef.friction = 1f
        fixtureDef.restitution = 0f

        fixture = this.body.createFixture(fixtureDef)
        body.resetMassData()

        // Init animations
        idleAnimation.create()
        walkAnimation.create()
        jumpAnimation.create()

        body.linearVelocity = Vector2(0f, 0f)
    }

    fun update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            body.applyForce(Vector2(0f, 50f), body.position, true)
            startAnimate(AnimationEnum.PLAYER_JUMP_ANIMATION)
        }

        if (currentAnimation != null) {
            val tmp = body.position
            tmp.y = 0f
            currentAnimation!!.pos = tmp
            currentAnimation!!.render()
        }
    }

    override fun startAnimate(animation: AnimationEnum) {
        currentAnimation = when(animation) {
            AnimationEnum.PLAYER_IDLE_ANIMATION -> idleAnimation
            AnimationEnum.PLAYER_WALK_ANIMATION -> walkAnimation
            AnimationEnum.PLAYER_JUMP_ANIMATION -> jumpAnimation
        }
    }

    override fun dispose() {
        shape.dispose()
        idleAnimation.dispose()
        walkAnimation.dispose()
        jumpAnimation.dispose()
    }
}
