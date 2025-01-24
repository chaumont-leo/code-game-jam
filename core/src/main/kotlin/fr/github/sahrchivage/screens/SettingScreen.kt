package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import fr.github.sahrchivage.MED_FONT_SCALE_X
import fr.github.sahrchivage.MED_FONT_SCALE_Y
import fr.github.sahrchivage.Main

class SettingScreen : AbstractScreen() {
    private val backgroundTexture = Texture(Gdx.files.internal("ui/background1.png"))
    private val logoTexture = Texture(Gdx.files.internal("ui/logo.png")) // Charger le logo
    private val background = TextureRegion(backgroundTexture)
    private val spriteBatch = SpriteBatch()

    private var volume: Float = 0.5f // Valeur initiale du volume
    private var difficulty: String = "Moyenne" // Valeur initiale de la difficulté
    private var language: String = "Français" // Valeur initiale de la langue
    private val skin = Skin(Gdx.files.internal("ui/uiskin.json"))
    private val labelSize = 2f
    private val logoWidth = 250f
    private val logoHeight = 250f
    private val logoX = stage.viewport.worldWidth / 2 - logoWidth / 2
    private val logoY = stage.viewport.worldHeight - logoHeight
    private val labelStyle = Label.LabelStyle().also { it.font = font }


    private fun createLabel(name: String): Label {
        return Label(name, skin)
            .also {
                it.style = labelStyle
                it.setAlignment(Align.center)
                it.color = Color.BLACK
            }
    }

    override fun show() {
        super.show()

        // Calcul de l'espacement
        val padding = 25f // Espacement vertical entre les éléments

        // Logo
        var currentY: Float = logoY - padding

        // Curseur Volume
        val volumeLabel = createLabel("Volume")
            .also { it.setPosition(stage.viewport.worldWidth / 2 - it.width / 2, currentY - it.height) }
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
        val difficultyLabel = createLabel("Difficulté")
            .also { it.setPosition(stage.viewport.worldWidth / 2 - it.width / 2, currentY - it.height) }
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
        val languageLabel = createLabel("Langue")
            .also { it.setPosition(stage.viewport.worldWidth / 2 - it.width / 2, currentY - it.height) }
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
        val fullscreenLabel = createLabel("Mode Plein Écran")
            .also { it.setPosition(stage.viewport.worldWidth / 2 - it.width / 2, currentY - it.height) }
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
        super.render(delta)

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


