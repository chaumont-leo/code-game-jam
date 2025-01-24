package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.github.czyzby.lml.parser.impl.attribute.building.RangeMaxValueLmlAttribute
import java.sql.DriverManager.println
import java.util.*
import java.util.Random

class TitleScreen : ScreenAdapter() {
    private val stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
    private val backgroundTexture = Texture(Gdx.files.internal("ui/background1.png"))
    private val cloud = Texture(Gdx.files.internal("ui/clouds_2.png"))
    private val cloud2 = Texture(Gdx.files.internal("ui/clouds_4.png"))
    private val background = TextureRegion(backgroundTexture)
    private val spriteBatch = SpriteBatch()
    private var cloudX = 0f - 1882
    private var cloudY = stage.viewport.worldHeight - cloud.height
    private var cloudX2 = 0f - 354
    private var cloudY2 = stage.viewport.worldHeight - cloud2.height
    private val cloudSpeed = 100f
    private val cloudSpeedY = 10f

    override fun show() {
        Gdx.input.inputProcessor = stage

        // Titre
        val titleLabel = Label("Bienvenue dans le Jeu !", Skin(Gdx.files.internal("ui/uiskin.json")))
        titleLabel.setFontScale(2f)
        titleLabel.setPosition(
            stage.viewport.worldWidth / 2 - titleLabel.prefWidth / 2,
            stage.viewport.worldHeight - 100f
        )
        stage.addActor(titleLabel)

        // Bouton Jouer
        val playButton = TextButton("Jouer", Skin(Gdx.files.internal("ui/uiskin.json")))
        playButton.setSize(200f, 50f)
        playButton.setPosition(
            stage.viewport.worldWidth / 2 - playButton.width / 2,
            stage.viewport.worldHeight / 2
        )
        playButton.color = Color.GREEN
        playButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("Lancement du jeu !")
            }
        })
        stage.addActor(playButton)

        // Bouton Paramètres
        val settingsButton = TextButton("Paramètres", Skin(Gdx.files.internal("ui/uiskin.json")))
        settingsButton.setSize(200f, 50f)
        settingsButton.setPosition(
            stage.viewport.worldWidth / 2 - settingsButton.width / 2,
            stage.viewport.worldHeight / 2 - 80f
        )
        settingsButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("Ouverture des paramètres !")
            }
        })
        stage.addActor(settingsButton)

        // Bouton Quitter
        val quitButton = TextButton("Quitter", Skin(Gdx.files.internal("ui/uiskin.json")))
        quitButton.setSize(200f, 50f)
        quitButton.setPosition(
            stage.viewport.worldWidth / 2 - quitButton.width / 2,
            stage.viewport.worldHeight / 2 - 160f
        )
        quitButton.color = Color.RED
        quitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Gdx.app.exit()
            }
        })
        stage.addActor(quitButton)
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f)

        // Move cloud1
        cloudX += cloudSpeed * delta
        if (cloudX > stage.viewport.worldWidth) {
            cloudX = -cloud.width.toFloat()
            cloudY = stage.viewport.worldHeight - cloud.height
        }
        cloudY -= cloudSpeedY * delta
        if (cloudX > stage.viewport.worldWidth / 3 || cloudX2 > -cloud2.width) {
            cloudX2 += cloudSpeed * delta
            if (cloudX2 > stage.viewport.worldWidth) {
                cloudX2 = -cloud2.width.toFloat()
                cloudY2 = stage.viewport.worldHeight - cloud2.height
            }
            cloudY2 -= cloudSpeedY * delta
        }

        // Draw background
        spriteBatch.projectionMatrix = stage.camera.combined
        spriteBatch.begin()
        spriteBatch.draw(background, 0f, 0f, stage.viewport.worldWidth, stage.viewport.worldHeight)

        // Draw clouds
        spriteBatch.draw(cloud, cloudX, cloudY)
        spriteBatch.draw(cloud2, cloudX2, cloudY2)

        spriteBatch.end()

        // Draw stage UI elements
        stage.act(delta)
        stage.draw()
    }





    override fun hide() {
        stage.dispose()
        backgroundTexture.dispose()
        cloud.dispose()
        spriteBatch.dispose()
    }
}
