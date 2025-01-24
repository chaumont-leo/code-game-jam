package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import fr.github.sahrchivage.PLAYER_HEIGHT
import fr.github.sahrchivage.PLAYER_WIDTH
import fr.github.sahrchivage.enums.AnimationEnum

class GameScreen : AbstractScreen() {
    private var renderer: ShapeRenderer? = null
    private lateinit var groundBody: Body
    private lateinit var groundTexture: Texture
    private var groundPositionX = 0f
    private val scrollSpeed = 100f // Vitesse de défilement du sol en pixels par seconde
    private val batch = SpriteBatch()

    override fun show() {
        groundTexture = Texture(Gdx.files.internal("ui/floor/terrainherbe.png"))
        Gdx.input.inputProcessor = stage
        renderer = ShapeRenderer()
        main.player?.startAnimate(AnimationEnum.PLAYER_IDLE_ANIMATION)
        createGround()
    }

    override fun render(delta: Float) {
        super.render(delta)

        // Mise à jour du monde et du joueur
        main.worldManager.doPhysicalStep(delta)
        main.player?.update()

        // Rendu du sol
        batch.begin()
        batch.projectionMatrix = camera.combined

        val groundHeight = 50f
        val groundWidth = groundTexture.width.toFloat()

        // Déplacement du sol
        groundPositionX -= scrollSpeed * delta
        if (groundPositionX <= -groundTexture.width) {
            groundPositionX = 0f
        }

        // Couvrir toute la largeur visible avec le sol
        for (x in -groundWidth.toInt() until (viewport.worldWidth.toInt() + groundWidth.toInt()) step groundWidth.toInt()) {
            batch.draw(groundTexture, groundPositionX + x.toFloat(), 0f, groundWidth, groundHeight)
        }

        batch.end()

        // Rendu de la position du joueur
        renderer?.let {
            it.begin(ShapeRenderer.ShapeType.Filled)
            it.color = Color(0f, 1f, 1f, 0.5f)
            main.player?.let { player ->
                it.rect(player.body.position.x, player.body.position.y, PLAYER_WIDTH, PLAYER_HEIGHT)
            }
            it.end()
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        batch.projectionMatrix = camera.combined
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
