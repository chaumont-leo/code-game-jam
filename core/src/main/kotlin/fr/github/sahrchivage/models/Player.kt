package fr.github.sahrchivage.models

import com.badlogic.gdx.physics.box2d.*
import fr.github.sahrchivage.Main

class Player (body: Body, shape: Shape, fixtureDef: FixtureDef)
    : BaseModel(body, shape, fixtureDef)
{

//    companion object {
//        fun getPlayer() : Player {
//
//            val body: Body = Main.getMain().world.createBody()
//
//            val shape = PolygonShape()
//            shape.setAsBox(1f, 2f);
//
//            val fixtureDef = FixtureDef();
//            fixtureDef.shape = shape;
//            fixtureDef.density = 0.5f;
//            fixtureDef.friction = 0.4f;
//            fixtureDef.restitution = 0.6f;
//
//            val fixture = body.createFixture(fixtureDef);
//
//            return Player(BodyType.DynamicBody, shape, )
//        }
//    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}
