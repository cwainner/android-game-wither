package com.beardoggames.wither;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.beardoggames.wither.scenes.MainMenuScreen;

public class GameMain extends Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	private SpriteBatch batch;
	private BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
    this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}

	public SpriteBatch getBatch(){
		return this.batch;
	}

	public BitmapFont getFont(){
		return this.font;
	}
}
