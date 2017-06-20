package com.beardoggames.wither.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beardoggames.wither.GameMain;
import com.beardoggames.wither.models.Ground;
import com.beardoggames.wither.models.Player;

import static com.badlogic.gdx.Input.*;

public class GameScreen implements Screen{
  private final GameMain game;
  private Player player;
  private Ground ground;
  private World world;
  private OrthographicCamera camera;
  private Viewport viewport;
  private Box2DDebugRenderer debugRenderer;

  public GameScreen(final GameMain game){
    this.game = game;

    // Create Textures

    // Create the camera
    camera = new OrthographicCamera();
    viewport = new FitViewport(GameMain.WIDTH / GameMain.PPM, GameMain.HEIGHT / GameMain.PPM, camera);
    camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

    debugRenderer = new Box2DDebugRenderer();

    // Create the world
    world = new World(new Vector2(0, -9.8f), true);

    // Create a rectangle to represent the player
    player = new Player(world, "sprites/playerSprite.png", GameMain.WIDTH / 2, GameMain.HEIGHT / 2);
    ground = new Ground(world, "sprites/ground.png");
  }

  private void parseInputs(){
    // Handle inputs
    if(Gdx.input.isKeyPressed(Keys.LEFT)){
      player.getBody().applyLinearImpulse(new Vector2(-0.1f, 0), player.getBody().getWorldCenter(), true);
    } else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
      player.getBody().applyLinearImpulse(new Vector2(0.1f, 0), player.getBody().getWorldCenter(), true);
    }
    if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
      player.getBody().setLinearVelocity(new Vector2(player.getBody().getLinearVelocity().x, 5f));
    }
  }

  private void update(float dt){
    parseInputs();
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    update(delta);

    // Update Player
    player.updatePlayer();

    // Clear the screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update the camera
    camera.update();

    // Tell the SpriteBatch to render in the coord system used by the camera
    game.getBatch().setProjectionMatrix(camera.combined);

    // Begin a new batch
    game.getBatch().begin();
    game.getBatch().draw(player, (player.getX() - player.getWidth() / 2) / GameMain.PPM, (player.getY() - player.getHeight() / 2) / GameMain.PPM, player.getWidth() / GameMain.PPM, player.getHeight() / GameMain.PPM);
    game.getBatch().draw(ground, (ground.getX() - ground.getWidth() / 2) / GameMain.PPM, (ground.getY() - ground.getHeight() / 2) / GameMain.PPM, ground.getWidth() / GameMain.PPM, ground.getHeight() / GameMain.PPM);
    game.getBatch().end();

    debugRenderer.render(world, camera.combined);

    // Set world physics
    world.step(Gdx.graphics.getDeltaTime(), 6, 2);
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    player.getTexture().dispose();
    ground.getTexture().dispose();
  }
}
