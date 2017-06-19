package com.beardoggames.wither.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.beardoggames.wither.GameMain;
import com.beardoggames.wither.helpers.GameInfo;
import com.beardoggames.wither.models.Player;

import static com.badlogic.gdx.Input.*;

public class GameScreen implements Screen{
  private final GameMain game;
  private OrthographicCamera camera;
  private Player player;
  private Texture playerSprite;

  public GameScreen(final GameMain game){
    this.game = game;

    // Create Textures
    playerSprite = new Texture(Gdx.files.internal("sprites/playerSprite.png"));

    // Create the camera
    camera = new OrthographicCamera();
    camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);

    // Create a rectangle to represent the player
    player = new Player(GameInfo.WIDTH / 2 - 64 / 2, 20, 64, 64);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    // Clear the screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update the camera
    camera.update();

    // Tell the SpriteBatch to render in the coord system used by the camera
    game.getBatch().setProjectionMatrix(camera.combined);

    // Begin a new batch
    game.getBatch().begin();
    game.getBatch().draw(playerSprite, player.getX(), player.getY(), player.getWidth(), player.getHeight());
    game.getBatch().end();

    // Process user input
    if(Gdx.input.isKeyPressed(Keys.LEFT) && (player.getX() > 0)){
      int x = player.getX();
      x -= 200 * Gdx.graphics.getDeltaTime();
      player.setX(x);
    }
    if(Gdx.input.isKeyPressed(Keys.RIGHT) && (player.getX() < (GameInfo.WIDTH - 64))){
      int x = player.getX();
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
    playerSprite.dispose();
  }
}
