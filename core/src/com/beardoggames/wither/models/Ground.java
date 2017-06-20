package com.beardoggames.wither.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class Ground extends Sprite{

  private World world;
  private Body body;
  private Fixture fixture;

  public Ground(World world, String name, float x, float y){
    super(new Texture(name));
    this.world = world;
    super.setPosition(x - getWidth() / 2f, y - getHeight() / 2f);
  }

  private void createBody(){
    // Create bodydef and assign body physics type
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.StaticBody;

    // Set bodydef position
    bodyDef.position.set(getX(), getY());

    // Create body based on bodydef
    body = world.createBody(bodyDef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(getWidth() / 2, getHeight() / 2);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 1;

    Fixture fixture = body.createFixture(fixtureDef);

    shape.dispose();
  }
}
