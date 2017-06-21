package com.beardoggames.wither.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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
  private World world;
  private Box2DDebugRenderer debugRenderer;

  public GameScreen(final GameMain game){
    this.game = game;

    // Create world
    world = new World(new Vector2(0, 0), true);

    // Create debug renderer for body shapes
    debugRenderer = new Box2DDebugRenderer();

    // Create the camera
    camera = new OrthographicCamera();
    viewport = new FitViewport(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, camera);

    tiledMap = new TmxMapLoader().load("maps/map.tmx");
    mapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);

    camera.position.set(0, 0, 0);
    Gdx.input.setInputProcessor(this);

    // Create the player
    player = new Player(world, "sprites/playerSprite.png", 100, (tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class)) / 2);
    mapRenderer.addSprite(player);
  }

  private void parseInputs(){
//    if(Gdx.input.isKeyPressed(Keys.LEFT) && (player.getX() > 24)){
//      player.body.setLinearVelocity(new Vector2(-2 * GameMain.PPM, 0));
//    } else if(Gdx.input.isKeyPressed(Keys.RIGHT) && (player.getX() < (tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class)) - 32)){
//      player.body.setLinearVelocity(new Vector2(2 * GameMain.PPM, 0));
//    }
//    if(Gdx.input.isKeyPressed(Keys.UP) && (player.getY() < (tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class)) - 32)){
//      float y = player.getY() + 2;
//      player.setPosition(player.getX(), y);
//    } else if(Gdx.input.isKeyPressed(Keys.DOWN) && (player.getY() > 24)){
//      float y = player.getY() - 2;
//      player.setPosition(player.getX(), y);
//    }
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

    player.updatePlayer();

    // Clear the screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update the camera
    camera.position.x = player.getX();
    camera.position.y = player.getY();
    camera.update();

    //
    world.step(Gdx.graphics.getDeltaTime(), 6, 2);
//    player.setPosition(player.body.getPosition().x, player.body.getPosition().y);

    // Render the debug renderer
    debugRenderer.render(world, camera.combined);

    // Update the tiled map and render all Sprites
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
    if(keycode == Keys.LEFT){
      player.body.setLinearVelocity(new Vector2(-2 * GameMain.PPM, 0));
    } else if(keycode == Keys.RIGHT){

    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    if(keycode == Keys.LEFT){
      player.body.setLinearVelocity(new Vector2(0, 0));
    }
    return true;
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
