package com.beardoggames.wither.models;


import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.beardoggames.wither.GameMain;
import com.beardoggames.wither.scenes.GameScreen;

public class Player extends Sprite{
  private World world;
  private State currentState, previousState;
  private int health;
  private TextureRegion still;
  private Animation<TextureRegion> walkLeft;
  private Animation<TextureRegion> walkRight;
  private Animation<TextureRegion> walkUp;
  private Animation<TextureRegion> walkDown;
  private float stateTimer;

  public Body body;
  public enum State {WALKLEFT, WALKRIGHT, WALKUP, WALKDOWN, STILL};

  public Player(World world, GameScreen screen, float x , float y){
    super(screen.getAtlas().findRegion("e_still"));
    super.setPosition(x, y);
    this.world = world;
    this.health = 3;
    currentState = State.STILL;
    previousState = State.STILL;
    stateTimer = 0;

    // Create animations
    TextureRegion frames[][] = new TextureRegion[4][3];
    for(int i = 0; i < 4; i++){
      for(int j = 0; j < 3; j++){
        frames[i][j] = new TextureRegion(getTexture(), j * 24, i * 32, 24, 32);
      }
    }
    walkUp = new Animation(0.3f, frames[0]);
    walkRight = new Animation(0.3f, frames[1]);
    walkDown = new Animation(0.3f, frames[2]);
    walkLeft = new Animation(0.3f, frames[3]);
    still = new TextureRegion(getTexture(), 0, 64, 24, 32);

    createBody();
    setRegion(still);
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

  public void updatePlayer(float dt){
    setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    setRegion(getFrame(dt));
  }

  private TextureRegion getFrame(float dt){
    currentState = getState();

    TextureRegion region;
    switch(currentState){
      case WALKLEFT:
        region = walkLeft.getKeyFrame(stateTimer, true);
        break;
      case WALKRIGHT:
        region = walkRight.getKeyFrame(stateTimer, true);
        break;
      case WALKUP:
        region = walkUp.getKeyFrame(stateTimer, true);
        break;
      case WALKDOWN:
        region = walkDown.getKeyFrame(stateTimer, true);
        break;
      case STILL:
      default:
        region = still;
    }

    stateTimer = currentState == previousState ? stateTimer + dt : 0;
    previousState = currentState;

    return region;
  }

  private State getState(){
    if(body.getLinearVelocity().y > 0){
      return State.WALKUP;
    } else if(body.getLinearVelocity().y < 0){
      return State.WALKDOWN;
    } else if(body.getLinearVelocity().x > 0){
      return State.WALKRIGHT;
    } else if(body.getLinearVelocity().x < 0){
      return  State.WALKLEFT;
    } else {
      return State.STILL;
    }
  }

  public int getHealth() {return health;}

  public void setHealth(int health) {this.health = health;}

}
