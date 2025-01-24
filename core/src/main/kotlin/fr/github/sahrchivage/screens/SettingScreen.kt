package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import fr.github.sahrchivage.Main

class SettingScreen : ScreenAdapter() {
    private val stage = Stage(FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()))
    private val backgroundTexture = Texture(Gdx.files.internal("ui/background1.png"))
    private val logoTexture = Texture(Gdx.files.internal("ui/logo.png")) // Charger le logo
    private val background = TextureRegion(backgroundTexture)
    private val spriteBatch = SpriteBatch()

    private var volume: Float = 0.5f // Valeur initiale du volume
    private var difficulty: String = "Moyenne" // Valeur initiale de la difficulté
    private var language: String = "Français" // Valeur initiale de la langue

    override fun show() {
        Gdx.input.inputProcessor = stage

        // Logo au lieu du titre
        val logoWidth = 500f
        val logoHeight = 500f
        val logoX = stage.viewport.worldWidth / 2 - logoWidth / 2
        val logoY = stage.viewport.worldHeight - logoHeight - 50f

        // Bouton Retour
        val quitButton = TextButton("Retour", Skin(Gdx.files.internal("ui/uiskin.json")))
        quitButton.setSize(200f, 50f)
        quitButton.setPosition(
            stage.viewport.worldWidth / 2 - quitButton.width / 2,
            50f // Placer le bouton au bas de la page
        )
        quitButton.color = Color.RED
        quitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Main.getMain().screen = TitleScreen()
            }
        })
        stage.addActor(quitButton)

        // Curseur Volume
        val volumeLabel = Label("Volume", Skin(Gdx.files.internal("ui/uiskin.json")))
        volumeLabel.setPosition(stage.viewport.worldWidth / 2 - volumeLabel.width / 2, stage.viewport.worldHeight - 200f)
        stage.addActor(volumeLabel)

        val volumeSlider = Slider(0f, 1f, 0.01f, false, Skin(Gdx.files.internal("ui/uiskin.json")))
        volumeSlider.value = volume
        volumeSlider.setSize(200f, 50f)
        volumeSlider.setPosition(stage.viewport.worldWidth / 2 - volumeSlider.width / 2, stage.viewport.worldHeight - 250f)
        volumeSlider.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                volume = volumeSlider.value
                println("Volume réglé à $volume")
            }
        })
        stage.addActor(volumeSlider)

        // Sélecteur de difficulté
        val difficultyLabel = Label("Difficulté", Skin(Gdx.files.internal("ui/uiskin.json")))
        difficultyLabel.setPosition(stage.viewport.worldWidth / 2 - difficultyLabel.width / 2, stage.viewport.worldHeight - 350f)
        stage.addActor(difficultyLabel)

        val difficultySelect = SelectBox<String>(Skin(Gdx.files.internal("ui/uiskin.json")))
        difficultySelect.setItems("Facile", "Moyen", "Difficile")
        difficultySelect.setSize(200f, 50f)
        difficultySelect.setPosition(stage.viewport.worldWidth / 2 - difficultySelect.width / 2, stage.viewport.worldHeight - 400f)
        difficultySelect.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                difficulty = difficultySelect.selected
                println("Difficulté réglée sur $difficulty")
            }
        })
        stage.addActor(difficultySelect)

        // Sélecteur de langue
        val languageLabel = Label("Langue", Skin(Gdx.files.internal("ui/uiskin.json")))
        languageLabel.setPosition(stage.viewport.worldWidth / 2 - languageLabel.width / 2, stage.viewport.worldHeight - 500f)
        stage.addActor(languageLabel)

        val languageSelect = SelectBox<String>(Skin(Gdx.files.internal("ui/uiskin.json")))
        languageSelect.setItems("Français", "Anglais")
        languageSelect.setSize(200f, 50f)
        languageSelect.setPosition(stage.viewport.worldWidth / 2 - languageSelect.width / 2, stage.viewport.worldHeight - 550f)
        languageSelect.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                language = languageSelect.selected
                println("Langue changée en $language")
            }
        })
        stage.addActor(languageSelect)
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f)

        // Dessiner le fond d'écran
        spriteBatch.projectionMatrix = stage.camera.combined
        spriteBatch.begin()
        spriteBatch.draw(background, 0f, 0f, stage.viewport.worldWidth, stage.viewport.worldHeight)

        // Dessiner le logo au lieu du titre
        spriteBatch.draw(logoTexture, stage.viewport.worldWidth / 2 - 200f, stage.viewport.worldHeight - 250f, 400f, 200f)

        spriteBatch.end()

        // Dessiner les éléments de l'interface
        stage.act(delta)
        stage.draw()
    }

    override fun hide() {
        stage.dispose()
        backgroundTexture.dispose()
        logoTexture.dispose()
        spriteBatch.dispose()
    }
}


