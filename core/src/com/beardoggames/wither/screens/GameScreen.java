package com.beardoggames.wither.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.beardoggames.wither.Wither;

import static com.badlogic.gdx.Input.*;

public class GameScreen implements Screen{
  private final Wither game;
  private OrthographicCamera camera;
  private Rectangle player;
  private Texture playerSprite;

  public GameScreen(final Wither game){
    this.game = game;

    // Create Textures
    playerSprite = new Texture(Gdx.files.internal("badlogic.jpg"));

    // Create the camera
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);

    // Create a rectangle to represent the player
    player = new Rectangle();
    player.x = 800 / 2 - 64 / 2;
    player.y = 20;
    player.width = 64;
    player.height = 64;
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
    game.batch.setProjectionMatrix(camera.combined);

    // Begin a new batch
    game.batch.begin();
    game.batch.draw(playerSprite, player.x, player.y, player.width, player.height);
    game.batch.end();

    // Process user input
    if(Gdx.input.isKeyPressed(Keys.LEFT) && (player.x > 0)){
      player.x -= 200 * Gdx.graphics.getDeltaTime();
    }
    if(Gdx.input.isKeyPressed(Keys.RIGHT) && (player.x < (800 - 64))){
      player.x += 200 * Gdx.graphics.getDeltaTime();
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
