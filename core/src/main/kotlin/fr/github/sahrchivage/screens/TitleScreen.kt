package fr.github.sahrchivage.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import java.util.Random
import fr.github.sahrchivage.Main
import fr.github.sahrchivage.utils.SpriteAnimator
import fr.github.sahrchivage.utils.getInternalTextureAtlas

class TitleScreen : AbstractScreen() {
    private val backgroundTexture = Texture(Gdx.files.internal("ui/background1.png"))
    private val cloud = Texture(Gdx.files.internal("ui/clouds_2.png"))
    private val cloud2 = Texture(Gdx.files.internal("ui/clouds_4.png"))
    private val logoTexture = Texture(Gdx.files.internal("ui/logo.png")) // Charger le logo
    private val background = TextureRegion(backgroundTexture)
    private val spriteBatch = SpriteBatch()
    private var cloudX = 0f
    private var cloudY = stage.viewport.worldHeight - cloud.height
    private var cloudX2 = 0f - 354
    private var cloudY2 = stage.viewport.worldHeight - cloud2.height
    private val cloudSpeed = 100f
    private val cloudSpeedY = 10f

    private val logoWidth = 250f
    private val logoHeight = 250f
    private val logoX = stage.viewport.worldWidth / 2 - logoWidth / 2
    private val logoY = stage.viewport.worldHeight - logoHeight - 50f

    private var playerAnim = SpriteAnimator(2, 1, getInternalTextureAtlas("player/PlayerSprites.atlas").findRegion("Idle"), 0.3f)

    override fun show() {
        super.show()

        // Dimensions et espacement
        val buttonWidth = 400f
        val buttonHeight = 100f
        val buttonSpacing = 50f
        val buttonsStartY = logoY - 200f // Position initiale sous le logo, avec un espacement plus grand

        // Chargement du Skin et ajustement du style de texte
        val skin = Skin(Gdx.files.internal("ui/uiskin.json"))
        val textButtonStyle = skin.get("default", TextButton.TextButtonStyle::class.java)
        textButtonStyle.font = font
        textButtonStyle.font.data.setScale(2f) // Augmente la taille du texte dans les boutons

        // Bouton Jouer
        val playButton = TextButton("Jouer", skin)
        playButton.setSize(buttonWidth, buttonHeight)
        playButton.style = textButtonStyle
        playButton.setPosition(
            stage.viewport.screenWidth / 2 - playButton.width / 2,
            buttonsStartY
        )
        playButton.color = Color.GREEN
        playButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Main.getMain().startGame()
            }
        })
        stage.addActor(playButton)

        // Bouton Paramètres
        val settingsButton = TextButton("Paramètres", skin)
        settingsButton.setSize(buttonWidth, buttonHeight)
        settingsButton.style = textButtonStyle
        settingsButton.setPosition(
            stage.viewport.screenWidth / 2 - settingsButton.width / 2,
            buttonsStartY - (buttonHeight + buttonSpacing)
        )
        settingsButton.color = Color.BLUE
        settingsButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                Main.getMain().screen = SettingScreen()
            }
        })
        stage.addActor(settingsButton)

        // Bouton Quitter
        val quitButton = TextButton("Quitter", skin)
        quitButton.setSize(buttonWidth, buttonHeight)
        quitButton.style = textButtonStyle
        quitButton.setPosition(
            stage.viewport.screenWidth / 2 - quitButton.width / 2,
            buttonsStartY - 2 * (buttonHeight + buttonSpacing)
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
        playerAnim.create()
        playerAnim.pos = Vector2(
            stage.viewport.screenWidth.toFloat()  / 4 - playerAnim.width/2,
            stage.viewport.screenHeight.toFloat() / 2 - playerAnim.height/2)
        playerAnim.size = 1f
    }

    override fun render(delta: Float) {
        super.render(delta)

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
        // Dessiner le logo au lieu du titre
        spriteBatch.draw(logoTexture, logoX, logoY, logoWidth, logoHeight)

        // Dessiner les nuages en mouvement
        spriteBatch.draw(cloud, cloudX, cloudY) // Placer les nuages à la position (cloudX, cloudY)

        val currentAnimFrame = playerAnim.getCurrentFrame(delta)
        if (currentAnimFrame != null)
            playerAnim.drawInBatch(spriteBatch, currentAnimFrame)

        spriteBatch.end()

        // Draw stage UI elements
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
