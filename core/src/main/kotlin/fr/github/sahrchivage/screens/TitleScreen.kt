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
    private var playerAnim = SpriteAnimator(2, 1, getInternalTextureAtlas("player/PlayerSprites.atlas").findRegion("Idle"), 0.3f)

    override fun show() {
        super.show()

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
                Main.getMain().startGame()
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

        playerAnim.create()
        playerAnim.pos = Vector2(
            stage.viewport.worldWidth / 4,
            stage.viewport.worldHeight / 2)
        playerAnim.size = 0.5f
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
        spriteBatch.draw(logoTexture, stage.viewport.worldWidth / 2 - 200f, stage.viewport.worldHeight - 250f, 400f, 200f)

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
