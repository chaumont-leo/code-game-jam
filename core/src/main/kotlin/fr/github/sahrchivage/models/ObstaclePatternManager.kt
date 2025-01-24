package fr.github.sahrchivage.models

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.World

class ObstaclePatternManager {
    private val patterns = mutableListOf<ObstaclePattern>()
    private var currentPatternIndex = 0

    fun load(world: World) {
        // On récupère chaque pattern d'obstacles dans /assets/patterns/
        val patternFiles = Gdx.files.internal("patterns").list { _, name -> name.endsWith(".json") }

        patternFiles.forEach {
            // On charge le pattern
            val pattern = ObstaclePattern.load(it, world)
            patterns.add(pattern)
        }
    }

    fun getNextPattern(): ObstaclePattern {
        // On génère un index aléatoire
        val randomIndex = (0 until patterns.size).random()

        // Si l'index est le même que le pattern actuel, on ajoute 1
        currentPatternIndex = if (randomIndex == currentPatternIndex) {
            (currentPatternIndex + 1) % patterns.size
        } else {
            randomIndex
        }

        // On retourne le pattern
        return patterns[currentPatternIndex]
    }

    // SINGLETON
    companion object {
        private var _instance: ObstaclePatternManager? = null

        fun getInstance(): ObstaclePatternManager {
            if (_instance == null)
                _instance = ObstaclePatternManager()

            return _instance as ObstaclePatternManager
        }
    }

    private constructor()
}
