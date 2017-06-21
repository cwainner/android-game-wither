package com.beardoggames.wither.tools;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.beardoggames.wither.GameMain;


public class MapBodyBuilder {

  // The pixels per tile. If your tiles are 16x16, this is set to 16f

  public static Array<Body> buildShapes(Map map, World world) {
    MapObjects objects = map.getLayers().get(2).getObjects();

    Array<Body> bodies = new Array<Body>();

    for(MapObject object : objects) {

      if (object instanceof TextureMapObject) {
        continue;
      }

      Shape shape = getRectangle((RectangleMapObject)object);

      BodyDef bd = new BodyDef();
      bd.type = BodyDef.BodyType.StaticBody;
      Body body = world.createBody(bd);
      body.createFixture(shape, 1);

      bodies.add(body);

      shape.dispose();
    }
    return bodies;
  }

  private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
    Rectangle rectangle = rectangleObject.getRectangle();
    PolygonShape polygon = new PolygonShape();
    Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f),
        (rectangle.y + rectangle.height * 0.5f));
    polygon.setAsBox(rectangle.width * 0.5f,
        rectangle.height * 0.5f,
        size,
        0.0f);
    return polygon;
  }
}
