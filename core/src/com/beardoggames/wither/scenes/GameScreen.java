package com.beardoggames.wither.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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
  private Box2DDebugRenderer debugRenderer;

  public GameScreen(final GameMain game){
    this.game = game;

    // Create Textures

    // Create the camera
    camera = new OrthographicCamera();
    camera.setToOrtho(false, GameMain.WIDTH, GameMain.HEIGHT);
    camera.position.set(GameMain.WIDTH / 2f, GameMain.HEIGHT / 2f, 0);

    debugRenderer = new Box2DDebugRenderer();

    // Create the world
    world = new World(new Vector2(0, -980f), true);

    // Create a rectangle to represent the player
    player = new Player(world, "sprites/playerSprite.png", GameMain.WIDTH / 2, 150);
    ground = new Ground(world, "sprites/ground.png");
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    // Clear the screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update Player
    player.updatePlayer();

    // Update the camera
    camera.update();

    // Tell the SpriteBatch to render in the coord system used by the camera
    game.getBatch().setProjectionMatrix(camera.combined);

    // Begin a new batch
    game.getBatch().begin();
    game.getBatch().draw(player, player.getX() - player.getWidth() / 2f, player.getY() - player.getHeight() / 2f);
    game.getBatch().end();

    debugRenderer.render(world, camera.combined);

    // Set world physics
    world.step(Gdx.graphics.getDeltaTime(), 6, 2);

    // Process user input
    if(Gdx.input.isKeyPressed(Keys.LEFT) && (player.getX() > 0)){
      float x = player.getX();
      x -= 200 * Gdx.graphics.getDeltaTime();
      player.setX(x);
    }
    if(Gdx.input.isKeyPressed(Keys.RIGHT) && (player.getX() < (GameMain.WIDTH - 64))){
      float x = player.getX();
      x += 200 * Gdx.graphics.getDeltaTime();
      player.setX(x);
    }
  }

  @Override
  public void resize(int width, int height) {

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
  }
}
