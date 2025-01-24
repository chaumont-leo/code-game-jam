package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import fr.github.sahrchivage.Main

class GameScreen : ScreenAdapter() {
    private val stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
    private val main = Main.getMain()
    private lateinit var groundBody: Body
    private lateinit var groundTexture: Texture
    private var groundPositionX = 0f
    private val scrollSpeed = 100f // Vitesse de défilement du sol en pixels par seconde
    private val batch = SpriteBatch()



    override fun show() {
        groundTexture = Texture(Gdx.files.internal("ui/floor/terrainherbe.png"))

        Gdx.input.inputProcessor = stage
        createGround()

    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
        main.worldManager.doPhysicalStep(delta)
        groundPositionX -= scrollSpeed * delta
        if (groundPositionX <= -groundTexture.width) {
            groundPositionX = 0f
        }

        batch.begin()

        // Dessiner le sol sur toute la largeur
        val groundHeight = 50f
        val groundWidth = groundTexture.width.toFloat()
        for (x in 0 until (stage.viewport.worldWidth.toInt() + groundWidth.toInt()) step groundWidth.toInt()) {
            batch.draw(groundTexture, groundPositionX + x.toFloat(), 0f, groundWidth, groundHeight)
        }

        batch.end()
    }

    override fun hide() {
        stage.dispose()
    }
    private fun createGround() {
        val groundDef = BodyDef()
        groundDef.type = BodyDef.BodyType.StaticBody // Le sol est statique
        groundDef.position.set(0f, 0f) // Position de départ (0, 0)

        groundBody = main.worldManager.world.createBody(groundDef)

        val groundShape = PolygonShape()
        groundShape.setAsBox(stage.viewport.worldWidth / 2, 25f) // Largeur totale du sol

        val fixtureDef = FixtureDef()
        fixtureDef.shape = groundShape
        fixtureDef.friction = 0.5f

        groundBody.createFixture(fixtureDef) // Ajouter la fixture au sol
        groundShape.dispose() // Libérer la mémoire de la forme
    }
}
