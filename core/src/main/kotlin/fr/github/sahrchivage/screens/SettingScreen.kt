package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
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
    private val skin = Skin(Gdx.files.internal("ui/uiskin.json"))
    private val labelsize = 2f
    private val logoWidth = 250f
    private val logoHeight = 250f
    private val logoX = stage.viewport.worldWidth / 2 - logoWidth / 2
    private val logoY = stage.viewport.worldHeight - logoHeight


    override fun show() {
        Gdx.input.inputProcessor = stage

        // Calcul de l'espacement
        val padding = 25f // Espacement vertical entre les éléments
        val startY = stage.viewport.worldHeight - 50f // Position de départ en haut
        var currentY = startY

        // Logo
        currentY = logoY - padding

        // Curseur Volume
        val volumeLabel = Label("Volume", skin)
        volumeLabel.setFontScale(labelsize)
        volumeLabel.setAlignment(Align.center)
        volumeLabel.setPosition(stage.viewport.worldWidth / 2 - volumeLabel.width / 2, currentY - volumeLabel.height)
        volumeLabel.color = Color.BLACK
        stage.addActor(volumeLabel)
        currentY -= (volumeLabel.height + padding)

        val volumeSlider = Slider(0f, 1f, 0.01f, false, skin)
        volumeSlider.value = volume
        volumeSlider.setSize(400f, 80f) // Agrandir le slider
        volumeSlider.setPosition(stage.viewport.worldWidth / 2 - volumeSlider.width / 2, currentY - volumeSlider.height)
        volumeSlider.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                volume = volumeSlider.value
                println("Volume réglé à $volume")
            }
        })
        stage.addActor(volumeSlider)
        currentY -= (volumeSlider.height + padding)

        // Sélecteur de difficulté
        val difficultyLabel = Label("Difficulté", skin)
        difficultyLabel.setFontScale(labelsize)
        difficultyLabel.setAlignment(Align.center)
        difficultyLabel.setPosition(stage.viewport.worldWidth / 2 - difficultyLabel.width / 2, currentY - difficultyLabel.height)
        difficultyLabel.color = Color.BLACK
        stage.addActor(difficultyLabel)
        currentY -= (difficultyLabel.height + padding)

        val difficultySelect = SelectBox<String>(skin)
        difficultySelect.setItems("Facile", "Moyen", "Difficile")
        difficultySelect.setSize(300f, 70f) // Agrandir le SelectBox
        difficultySelect.setPosition(stage.viewport.worldWidth / 2 - difficultySelect.width / 2, currentY - difficultySelect.height)
        difficultySelect.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                difficulty = difficultySelect.selected
                println("Difficulté réglée sur $difficulty")
            }
        })
        stage.addActor(difficultySelect)
        currentY -= (difficultySelect.height + padding)

        // Sélecteur de langue
        val languageLabel = Label("Langue", skin)
        languageLabel.setFontScale(labelsize)
        languageLabel.setAlignment(Align.center)
        languageLabel.color = Color.BLACK
        languageLabel.setPosition(stage.viewport.worldWidth / 2 - languageLabel.width / 2, currentY - languageLabel.height)
        stage.addActor(languageLabel)
        currentY -= (languageLabel.height + padding)

        val languageSelect = SelectBox<String>(skin)
        languageSelect.setItems("Français", "Anglais")
        languageSelect.setSize(300f, 70f) // Agrandir le SelectBox
        languageSelect.setPosition(stage.viewport.worldWidth / 2 - languageSelect.width / 2, currentY - languageSelect.height)
        languageSelect.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                language = languageSelect.selected
                println("Langue changée en $language")
            }
        })
        stage.addActor(languageSelect)
        currentY -= (languageSelect.height + padding)

        // Case à cocher pour le plein écran
        val fullscreenLabel = Label("Mode Plein Écran", skin)
        fullscreenLabel.setAlignment(Align.center)
        fullscreenLabel.color = Color.BLACK
        fullscreenLabel.setFontScale(labelsize)

        fullscreenLabel.setPosition(stage.viewport.worldWidth / 2 - fullscreenLabel.width / 2, currentY - fullscreenLabel.height)
        stage.addActor(fullscreenLabel)
        currentY -= (fullscreenLabel.height + padding)

        val fullscreenCheckbox = CheckBox("", skin)
        fullscreenCheckbox.isChecked = Gdx.graphics.isFullscreen
        fullscreenCheckbox.setSize(50f, 50f) // Agrandir la checkbox
        fullscreenCheckbox.setPosition(stage.viewport.worldWidth / 2 - fullscreenCheckbox.width / 2, currentY - fullscreenCheckbox.height)
        fullscreenCheckbox.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                if (fullscreenCheckbox.isChecked) {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.displayMode) // Activer le plein écran
                    println("Mode plein écran activé")
                } else {
                    Gdx.graphics.setWindowedMode(1280, 720) // Désactiver le plein écran
                    println("Mode fenêtré activé")
                }
            }
        })
        stage.addActor(fullscreenCheckbox)
        currentY -= (fullscreenCheckbox.height + padding)

        // Bouton Retour (toujours en dernier)
        val quitButton = TextButton("Retour", skin)
        quitButton.setSize(300f, 80f) // Agrandir le bouton Retour
        quitButton.setPosition(
            stage.viewport.worldWidth / 2 - quitButton.width / 2,
            currentY - quitButton.height
        )
        quitButton.color = Color.RED
        quitButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Main.getMain().screen = TitleScreen()
            }
        })
        stage.addActor(quitButton)
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
        spriteBatch.draw(logoTexture, logoX, logoY, logoWidth, logoHeight)

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
        skin.dispose()
    }
}


