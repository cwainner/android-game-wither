package com.beardoggames.wither.models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends Sprite{

  private World world;
  private Body body;

  private int health;

  public Player(World world, String name, float x , float y){
    super(new Texture(name));
    this.world = world;
    super.setPosition(x - getWidth() / 2f, y - getHeight() / 2f);
    this.health = 3;
    createBody();
  }

  void createBody(){
    // Create bodydef and assign body physics type
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;

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

  public void updatePlayer(){
    this.setPosition(body.getPosition().x, body.getPosition().y);
  }

  public int getHealth() {return health;}

  public void setHealth(int health) {this.health = health;}
}
