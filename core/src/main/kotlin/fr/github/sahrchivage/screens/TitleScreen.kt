package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import java.util.Random
import fr.github.sahrchivage.Main


class TitleScreen : ScreenAdapter() {
    private val stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
    private val backgroundTexture = Texture(Gdx.files.internal("ui/background1.png"))
    private val cloud = Texture(Gdx.files.internal("ui/clouds_2.png"))
    private val logoTexture = Texture(Gdx.files.internal("ui/logo.png")) // Charger le logo
    private val background = TextureRegion(backgroundTexture)
    private val spriteBatch = SpriteBatch()
    private var cloudX = 0f
    private var cloudY = 600f
    private val cloudSpeed = 400f
    private val cloudSpeedY = 10f


    override fun show() {
        Gdx.input.inputProcessor = stage

        // Logo au lieu du titre
        val logoWidth = 500f
        val logoHeight = 500f
        val logoX = stage.viewport.worldWidth / 2 - logoWidth / 2
        val logoY = stage.viewport.worldHeight - logoHeight - 50f

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
        settingsButton.color = Color.BLUE
        settingsButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Main.getMain().screen = SettingScreen();
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
        
        button.setScale(5f)
        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Main.getMain().startGame();
            }
        })
        stage.addActor(button)
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f)

        cloudX += cloudSpeed * delta
        if (cloudX > stage.viewport.worldWidth) {
            cloudX = -cloud.width.toFloat()
            val random = Random()
            var valeurYcloud = random.nextInt(400, 600).toFloat()
            cloudY = valeurYcloud.toFloat()
        }
        cloudY -= cloudSpeedY * delta


        // Dessiner le fond d'écran
        spriteBatch.projectionMatrix = stage.camera.combined
        spriteBatch.begin()
        spriteBatch.draw(background, 0f, 0f, stage.viewport.worldWidth, stage.viewport.worldHeight)

        // Dessiner le logo au lieu du titre
        spriteBatch.draw(logoTexture, stage.viewport.worldWidth / 2 - 200f, stage.viewport.worldHeight - 250f, 400f, 200f)

        // Dessiner les nuages en mouvement
        spriteBatch.draw(cloud, cloudX, cloudY) // Placer les nuages à la position (cloudX, cloudY)

        spriteBatch.end()

        // Dessiner les éléments de l'interface
        stage.act(delta)
        stage.draw()
    }

    override fun hide() {
        stage.dispose()
        backgroundTexture.dispose()
        cloud.dispose()
        logoTexture.dispose()
        spriteBatch.dispose()
    }
}
