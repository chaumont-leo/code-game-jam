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

class TitleScreen : ScreenAdapter() {
    private val stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
    private val backgroundTexture = Texture(Gdx.files.internal("ui/background1.png"))
    private val cloud = Texture(Gdx.files.internal("ui/clouds_2.png"))
    private val background = TextureRegion(backgroundTexture)
    private val spriteBatch = SpriteBatch()

    private var cloudX = 50f
    private var cloudY = 500f // Position verticale initiale des nuages
    private val cloudSpeed = 100f // Vitesse de déplacement horizontal
    private val cloudSpeedY = 30f // Vitesse de descente des nuages

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

        // Déplacer les nuages horizontalement
        cloudX += cloudSpeed * delta // Déplacement horizontal fluide
        if (cloudX > stage.viewport.worldWidth) {
            cloudX = -cloud.width.toFloat() // Remettre les nuages à gauche une fois qu'ils ont dépassé l'écran
        }

        // Descendre les nuages verticalement
        cloudY -= cloudSpeedY * delta // Descente des nuages
        if (cloudY + cloud.height < 0) {
            cloudY = stage.viewport.worldHeight.toFloat() // Remonter les nuages une fois qu'ils sont sortis par le bas
        }

        // Dessiner le fond d'écran
        spriteBatch.projectionMatrix = stage.camera.combined
        spriteBatch.begin()
        spriteBatch.draw(background, 0f, 0f, stage.viewport.worldWidth, stage.viewport.worldHeight)

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
        spriteBatch.dispose()
    }
}
