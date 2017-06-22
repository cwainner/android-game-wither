package com.beardoggames.wither.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beardoggames.wither.GameMain;
import com.beardoggames.wither.models.Player;
import com.beardoggames.wither.tools.MapBodyBuilder;
import com.beardoggames.wither.tools.OrthogonalTiledMapRendererWithSprites;

import static com.badlogic.gdx.Input.*;

public class GameScreen implements Screen, InputProcessor {
  private final GameMain game;
  private TextureAtlas textureAtlas;
  private Player player;
  private OrthographicCamera camera;
  private Viewport viewport;
  private TiledMap tiledMap;
  private OrthogonalTiledMapRendererWithSprites mapRenderer;
  private World world;
  private Box2DDebugRenderer debugRenderer;
  private Array<Body> bodies;

  public GameScreen(final GameMain game){
    this.game = game;

    // Create atlas
    textureAtlas = new TextureAtlas("textures/walk_animations.atlas");

    // Create world
    world = new World(new Vector2(0, 0), true);

    // Create debug renderer for body shapes
    debugRenderer = new Box2DDebugRenderer();

    // Create the map
    tiledMap = new TmxMapLoader().load("maps/map.tmx");
    mapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);

    // Create the camera
    camera = new OrthographicCamera();
    viewport = new FitViewport(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, camera);

    Gdx.input.setInputProcessor(this);

    // Create the player
    player = new Player(world, this, 100, (tiledMap.getProperties().get("height", Integer.class) * tiledMap.getProperties().get("tileheight", Integer.class)) / 2);
    bodies = MapBodyBuilder.buildShapes(tiledMap, world);
    bodies.add(player.body);
    mapRenderer.addSprite(player);
    Gdx.input.setInputProcessor(this);
  }

  public TextureAtlas getAtlas(){
    return this.textureAtlas;
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {

    player.updatePlayer(delta);

    // Clear the screen
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update the camera
    camera.position.x = player.getX();
    camera.position.y = player.getY();
    camera.update();

    world.step(Gdx.graphics.getDeltaTime(), 6, 2);

    // Update the tiled map and render all Sprites
    mapRenderer.setView(camera);
    mapRenderer.render();

    // Render the debug renderer
    debugRenderer.render(world, camera.combined);

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
    mapRenderer.dispose();
    debugRenderer.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    if(keycode == Keys.LEFT){
      player.body.setLinearVelocity(new Vector2(-2 * GameMain.PPM, 0));
    } else if(keycode == Keys.RIGHT){
      player.body.setLinearVelocity(new Vector2(2 * GameMain.PPM, 0));
    }
    if(keycode == Keys.UP){
      player.body.setLinearVelocity(new Vector2(0, 2 * GameMain.PPM));
    } else if(keycode == Keys.DOWN){
      player.body.setLinearVelocity(new Vector2(0, -2 * GameMain.PPM));
    }
    return true;
  }

  @Override
  public boolean keyUp(int keycode) {
    if(keycode == Keys.LEFT || keycode == Keys.RIGHT || keycode == Keys.DOWN || keycode == Keys.UP){
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
