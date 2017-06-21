package com.beardoggames.wither.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beardoggames.wither.GameMain;
import com.beardoggames.wither.models.Player;

import static com.badlogic.gdx.Input.*;

public class GameScreen implements Screen, InputProcessor {
  private final GameMain game;
  private Player player;
  private OrthographicCamera camera;
  private Viewport viewport;
  private TiledMap tiledMap;
  private TiledMapRenderer mapRenderer;

  public GameScreen(final GameMain game){
    this.game = game;

    // Create the camera
    camera = new OrthographicCamera();
    viewport = new FitViewport(GameMain.WIDTH / 3, GameMain.HEIGHT / 3, camera);
    camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
    Gdx.input.setInputProcessor(this);

    // Create the player
    player = new Player("sprites/playerSprite.png", 25, 25);
  }

  private void parseInputs(){
    if(Gdx.input.isKeyPressed(Keys.LEFT) && (player.getX() > 0)){
      float x = player.getX() - 10;
      player.setPosition(x, player.getY());
    } else if(Gdx.input.isKeyPressed(Keys.RIGHT) && (player.getX() < GameMain.WIDTH)){
      float x = player.getX() + 10;
      player.setPosition(x, player.getY());
    }
    if(Gdx.input.isKeyPressed(Keys.UP)){
      float y = player.getY() + 10;
      player.setPosition(player.getX(), y);
    } else if(Gdx.input.isKeyPressed(Keys.DOWN)){
      float y = player.getY() - 10;
      player.setPosition(player.getX(), y);
    }
  }

  private void update(float dt){
    parseInputs();
  }

  @Override
  public void show() {
    tiledMap = new TmxMapLoader().load("maps/map.tmx");
    mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
  }

  @Override
  public void render(float delta) {
    update(delta);

    // Clear the screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update the camera
    camera.update();

    // Update the tiled map
    mapRenderer.setView(camera);
    mapRenderer.render();

    // Tell the SpriteBatch to render in the coord system used by the camera
    game.getBatch().setProjectionMatrix(camera.combined);

    // Begin a new batch
    game.getBatch().begin();
    game.getBatch().draw(player, (player.getX() - player.getWidth() / 2), (player.getY() - player.getHeight() / 2), player.getWidth(), player.getHeight());
    game.getBatch().end();
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
    tiledMap.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    return false;
  }
}
