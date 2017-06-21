package com.beardoggames.wither.tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;

/**
 * Created by chris on 21/06/17.
 */

public class OrthogonalTiledMapRendererWithSprites extends OrthogonalTiledMapRenderer {
  private ArrayList<Sprite> sprites;

  public OrthogonalTiledMapRendererWithSprites(TiledMap map){
    super(map);
    sprites = new ArrayList<Sprite>();
  }

  public void addSprite(Sprite sprite){
    sprites.add(sprite);
  }

  @Override
  public void render(){
    beginRender();
    int currentLayer = 0;
    for(MapLayer layer : map.getLayers()){
      if(layer instanceof TiledMapTileLayer){
        renderTileLayer((TiledMapTileLayer) layer);
        currentLayer++;
//        if(currentLayer == draw)
      }
    }
    endRender();
  }
}
