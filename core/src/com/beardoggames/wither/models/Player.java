package com.beardoggames.wither.models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.beardoggames.wither.GameMain;

public class Player extends Sprite{
  private World world;
  public Body body;

  private int health;

  public Player(World world, String name, float x , float y){
    super(new Texture(name));
    super.setPosition(x, y);
    this.world = world;
    this.health = 3;
    createBody();
  }

  private void createBody(){
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.position.set(getX(), getY());
    bodyDef.fixedRotation = true;
    body = world.createBody(bodyDef);

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(getWidth() / 2, getHeight() / 2);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.density = 1;
    body.createFixture(fixtureDef);
    body.setUserData("player");

    shape.dispose();
  }

  public void updatePlayer(){
    setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
  }

  public int getHealth() {return health;}

  public void setHealth(int health) {this.health = health;}

}
