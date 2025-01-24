package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
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
import fr.github.sahrchivage.PLAYER_HEIGHT
import fr.github.sahrchivage.PLAYER_WIDTH
import fr.github.sahrchivage.enums.AnimationEnum

class GameScreen : ScreenAdapter() {
    private val stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
    private val main = Main.getMain()
    private var renderer: ShapeRenderer? = null
    private lateinit var groundBody: Body
    private lateinit var groundTexture: Texture
    private var groundPositionX = 0f
    private val scrollSpeed = 100f // Vitesse de défilement du sol en pixels par seconde
    private val batch = SpriteBatch()



    override fun show() {
        groundTexture = Texture(Gdx.files.internal("ui/floor/ground.png"))
        Gdx.input.inputProcessor = stage
        renderer = ShapeRenderer()
        main.player?.startAnimate(AnimationEnum.PLAYER_IDLE_ANIMATION)
        createGround()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        // Nettoyage de l'écran
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // Mise à jour du joueur
        main.player?.update()

        // Mise à jour du monde physique
        main.worldManager.doPhysicalStep(delta)

        // Mise à jour et rendu de la scène
        stage.act(delta)
        stage.draw()

        // Rendu du sol
        batch.begin()
        val groundHeight = 50f
        val groundWidth = groundTexture.width.toFloat()

        // Faire défiler le sol
        groundPositionX -= scrollSpeed * delta
        if (groundPositionX <= -groundTexture.width) {
            groundPositionX = 0f
        }

        // Dessiner le sol sur toute la largeur
        for (x in 0 until (stage.viewport.worldWidth.toInt() + groundWidth.toInt()) step groundWidth.toInt()) {
            batch.draw(groundTexture, groundPositionX + x.toFloat(), 0f, groundWidth, groundHeight)
        }
        batch.end()

        // Rendu de la position du joueur avec ShapeRenderer
        if (renderer != null) {
            Gdx.gl.glEnable(GL20.GL_BLEND)
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

            renderer!!.begin(ShapeRenderer.ShapeType.Filled)
            renderer!!.color = Color(0f, 1f, 1f, 0.5f) // Couleur avec transparence
            if (main.player != null) {
                renderer!!.rect(
                    main.player!!.body.position.x,
                    main.player!!.body.position.y,
                    PLAYER_WIDTH,
                    PLAYER_HEIGHT
                )
            }
            renderer!!.end()

            Gdx.gl.glDisable(GL20.GL_BLEND) // Désactiver le blending
        }
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
