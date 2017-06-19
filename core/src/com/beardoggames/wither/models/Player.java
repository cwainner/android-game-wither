package com.beardoggames.wither.models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite{

  private int health;

  public Player(String name, float x , float y){
    super(new Texture(name));
    super.setPosition(x, y);
    this.health = 3;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }
}
