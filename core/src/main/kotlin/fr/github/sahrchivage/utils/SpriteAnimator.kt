package fr.github.sahrchivage.utils

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

class SpriteAnimator(
    private val cols: Int,
    private val rows: Int,
    private val region: TextureRegion,
    private val animationSpeed: Float,
    private val animationPlayMode: Animation.PlayMode = Animation.PlayMode.LOOP
) : ApplicationAdapter() {
    private var stateTime = 0f
    var pos = Vector2()
    var size = 1f
    var isReversed = false
    var offsetX = 0f

    val width: Int = region.regionWidth / cols
    val height: Int = region.regionHeight / rows

    private var frames: Array<TextureRegion>? = null
    private var animation: Animation<TextureRegion>? = null
    private var spriteBatch: SpriteBatch? = null

    override fun create() {
        val tmp = region.split(
            width,
            height,
        )

        frames = Array(cols * rows)
        for (i in 0 until rows)
            for (j in 0 until cols)
                frames!!.add(tmp[i][j])

        animation = Animation(animationSpeed, frames, animationPlayMode)

        spriteBatch = SpriteBatch()
        stateTime = 0f
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stateTime += Gdx.graphics.deltaTime

        val currentFrame: TextureRegion = animation?.getKeyFrame(stateTime, true) ?: return
        if (isReversed && !currentFrame.isFlipX)
            currentFrame.flip(true, false)
        else if (!isReversed && currentFrame.isFlipX)
            currentFrame.flip(true, false)
        spriteBatch?.begin()
        spriteBatch?.draw(currentFrame, pos.x+offsetX, pos.y, width*size, height*size)
        spriteBatch?.end()
    }

    override fun dispose() {
        spriteBatch?.dispose()
    }
}
