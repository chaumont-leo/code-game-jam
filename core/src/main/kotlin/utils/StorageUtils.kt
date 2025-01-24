package utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

fun getInternalTexture(path: String): Texture {
    val raw = Gdx.files.internal(path)
    return Texture(raw)
}

fun getInternalTextureAtlas(path: String): TextureAtlas {
    val raw = Gdx.files.internal(path)
    return TextureAtlas(raw)
}
