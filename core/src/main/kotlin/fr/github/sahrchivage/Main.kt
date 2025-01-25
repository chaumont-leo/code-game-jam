package fr.github.sahrchivage

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import fr.github.sahrchivage.models.ObstaclePatternManager
import fr.github.sahrchivage.models.Player
import fr.github.sahrchivage.screens.GameScreen
import fr.github.sahrchivage.screens.TitleScreen

class Main : Game() {
    lateinit var worldManager: WorldManager
    lateinit var patternManager: ObstaclePatternManager
    var player: Player? = null

    override fun create() {
        worldManager = WorldManager()
        setScreen(TitleScreen())
    }

    fun startGame() {
        player = Player()
        patternManager = ObstaclePatternManager.getInstance()
        this.setScreen(GameScreen())
    }

    override fun dispose() {
        super.dispose()
        worldManager.dispose()
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
