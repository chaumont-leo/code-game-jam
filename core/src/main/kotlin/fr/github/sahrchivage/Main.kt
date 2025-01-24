package fr.github.sahrchivage

import com.badlogic.gdx.Game
import fr.github.sahrchivage.screens.TitleScreen

class Main : Game() {
    override fun create() {
        setScreen(TitleScreen())
        screen.show()
    }

    override fun dispose() {
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
