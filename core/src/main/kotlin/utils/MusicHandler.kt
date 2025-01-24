package utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound

class MusicHandler {
    // Music tracks
    private var menuMusic: Music? = null
    private val gameMusicTracks: MutableMap<String, Music> = mutableMapOf()
    private var currentGameMusic: Music? = null

    // Sound effects
    private val soundEffects: MutableMap<String, Sound> = mutableMapOf()

    // Current music volume and state
    private var musicVolume: Float = 0.5f
    private var isMusicMuted: Boolean = false

    // Singleton
    private constructor() {
        load()
    }

    companion object {
        private var instance: MusicHandler? = null

        fun getInstance(): MusicHandler {
            if (instance == null) {
                instance = MusicHandler()
            }
            return instance!!
        }
    }

    private fun load() {
        // Load menu background music


        // Load multiple in-game music tracks


        // Load sound effects

    }

    private fun loadMusic(key: String, path: String) {
        try {
            val music = Gdx.audio.newMusic(Gdx.files.internal(path))
            music.isLooping = true
            music.volume = musicVolume
            gameMusicTracks[key] = music
        } catch (e: Exception) {
            Gdx.app.error("MusicHandler", "Failed to load music: $path", e)
        }
    }

    private fun loadSound(key: String, path: String) {
        try {
            val sound = Gdx.audio.newSound(Gdx.files.internal(path))
            soundEffects[key] = sound
        } catch (e: Exception) {
            Gdx.app.error("MusicHandler", "Failed to load sound: $path", e)
        }
    }

    fun playMenuMusic() {
        stopCurrentGameMusic()
        gameMusicTracks["menu_music"]?.play()
        currentGameMusic = gameMusicTracks["menu_music"]
    }

    fun playGameMusic(musicKey: String) {
        stopCurrentGameMusic()
        gameMusicTracks[musicKey]?.play()
        currentGameMusic = gameMusicTracks[musicKey]
    }

    private fun stopCurrentGameMusic() {
        currentGameMusic?.stop()
        currentGameMusic = null
    }

    fun pauseMusic() {
        currentGameMusic?.pause()
    }

    fun resumeMusic() {
        currentGameMusic?.play()
    }

    fun setVolume(volume: Float) {
        musicVolume = volume.coerceIn(0f, 1f)
        gameMusicTracks.values.forEach { it.volume = musicVolume }
    }

    fun toggleMute() {
        isMusicMuted = !isMusicMuted
        gameMusicTracks.values.forEach {
            it.volume = if (isMusicMuted) 0f else musicVolume
        }
    }

    fun playSound(key: String) {
        soundEffects[key]?.play()
    }

    fun dispose() {
        gameMusicTracks.values.forEach { it.dispose() }
        soundEffects.values.forEach { it.dispose() }
    }
}
