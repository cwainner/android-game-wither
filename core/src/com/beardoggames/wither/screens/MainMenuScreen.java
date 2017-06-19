package com.beardoggames.wither.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.beardoggames.wither.Wither;

public class MainMenuScreen implements Screen{
  private final Wither game;

  private OrthographicCamera camera;

  public MainMenuScreen(final Wither game){
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);

    game.batch.begin();
    game.font.draw(game.batch, "Welcome to Wither!", 800 / 2, 480 / 2);
    game.font.draw(game.batch, "Tap anywhere to begin", 800 / 2, 480 / 2 - 50);
    game.batch.end();
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