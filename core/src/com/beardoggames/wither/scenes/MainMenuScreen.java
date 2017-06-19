package com.beardoggames.wither.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.beardoggames.wither.GameMain;
import com.beardoggames.wither.helpers.GameInfo;

public class MainMenuScreen implements Screen{
  private final GameMain game;
  private OrthographicCamera camera;

  public MainMenuScreen(final GameMain game){
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, GameInfo.WIDTH, GameInfo.HEIGHT);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    // Clear the screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update camera and set game batch to use its coordinate system
    camera.update();
    game.getBatch().setProjectionMatrix(camera.combined);

    // Begin a new batch to draw
    game.getBatch().begin();
    game.getFont().draw(game.getBatch(), "Welcome to GameMain!", GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2);
    game.getFont().draw(game.getBatch(), "Tap anywhere to begin", GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2 - 50);
    game.getBatch().end();

    // Process user input
    if(Gdx.input.isTouched()){
      game.setScreen(new GameScreen(game));
      dispose();
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

  }
}
