package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
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
    private val stage = Stage(FitViewport(800f, 600f))
    private val backgroundTexture = Texture(Gdx.files.internal("ui/background_1.png")) // Remplace par le chemin de ton image
    private val spriteBatch = SpriteBatch()

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
        playButton.setColor(Color.GREEN)
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
        quitButton.setColor(Color.RED)
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

        // Dessin du fond d'écran
        spriteBatch.projectionMatrix = stage.camera.combined
        spriteBatch.begin()
        spriteBatch.draw(backgroundTexture, 0f, 0f, stage.viewport.worldWidth, stage.viewport.worldHeight)
        spriteBatch.end()

        // Dessin des éléments de l'interface
        stage.act(delta)
        stage.draw()
    }

    override fun hide() {
        stage.dispose()
        backgroundTexture.dispose()
        spriteBatch.dispose()
    }
}


