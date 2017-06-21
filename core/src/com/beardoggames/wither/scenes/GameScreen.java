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
import com.beardoggames.wither.tools.OrthogonalTiledMapRendererWithSprites;

import static com.badlogic.gdx.Input.*;

public class GameScreen implements Screen, InputProcessor {
  private final GameMain game;
  private Player player;
  private OrthographicCamera camera;
  private Viewport viewport;
  private TiledMap tiledMap;
  private OrthogonalTiledMapRendererWithSprites mapRenderer;

  public GameScreen(final GameMain game){
    this.game = game;

    // Create the camera
    camera = new OrthographicCamera();
    viewport = new FitViewport(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, camera);

    tiledMap = new TmxMapLoader().load("maps/map.tmx");
    mapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);

    camera.position.set(0, 0, 0);
    Gdx.input.setInputProcessor(this);

    // Create the player
    player = new Player("sprites/playerSprite.png", 100, (tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class)) / 2);
    mapRenderer.addSprite(player);
  }

  private void parseInputs(){
    if(Gdx.input.isKeyPressed(Keys.LEFT) && (player.getX() > 0)){
      float x = player.getX() - 10;
      player.setPosition(x, player.getY());
    } else if(Gdx.input.isKeyPressed(Keys.RIGHT) && (player.getX() < (tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class)))){
      float x = player.getX() + 10;
      player.setPosition(x, player.getY());
    }
    if(Gdx.input.isKeyPressed(Keys.UP) && (player.getY() < (tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class)))){
      float y = player.getY() + 10;
      player.setPosition(player.getX(), y);
    } else if(Gdx.input.isKeyPressed(Keys.DOWN) && (player.getY() > 0)){
      float y = player.getY() - 10;
      player.setPosition(player.getX(), y);
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

    // Clear the screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update the camera
    camera.position.x = player.getX();
    camera.position.y = player.getY();
    camera.update();

    // Update the tiled map
    mapRenderer.setView(camera);
    mapRenderer.render();

    // Tell the SpriteBatch to render in the coord system used by the camera
    game.getBatch().setProjectionMatrix(camera.combined);

    // Begin a new batch
    game.getBatch().begin();
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
