package fr.github.sahrchivage

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import fr.github.sahrchivage.screens.GameScreen
import fr.github.sahrchivage.screens.TitleScreen
import fr.github.sahrchivage.utils.getInternalTexture

class Main : Game() {
    lateinit var world: World

    override fun create() {
        setScreen(TitleScreen())
        screen.show()
    }

    fun startGame() {
        this.setScreen(GameScreen())
    }

    companion object {
        private var _instance: Main? = null

        fun getMain(): Main {
            if (_instance == null)
                _instance = Main()

            return _instance as Main
        }
    }
}
