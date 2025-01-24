package fr.github.sahrchivage.models

val PATTERNS = arrayListOf<ObstaclePattern>(
    ObstaclePattern.fromData(
        listOf(
            ObstacleData(0, 0, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(1, 0, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(1, 1, 40f, 40f, "obstacles/terrain.png"),

            ObstacleData(2, 0, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(3, 0, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(3, 1, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(3, 2, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(4, 0, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(5, 0, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(5, 1, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(5, 2, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(5, 3, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(6, 0, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(7, 0, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(7, 3, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(7, 4, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(8, 0, 40f, 40f, "obstacles/terrain.png"),
            ObstacleData(9, 0, 40f, 40f, "obstacles/terrain.png"),
        )
    )
)

class ObstaclePatternManager {
    private val patterns = PATTERNS
    private var currentPatternIndex = 0

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
