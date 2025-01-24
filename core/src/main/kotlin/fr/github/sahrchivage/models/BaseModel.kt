package fr.github.sahrchivage.models

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.Shape
import com.badlogic.gdx.utils.Disposable

abstract class BaseModel : Disposable
{
    lateinit var body : Body
    protected lateinit var fixture : Fixture
    protected lateinit var shape : Shape

}
