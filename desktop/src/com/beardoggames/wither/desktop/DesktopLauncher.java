package com.beardoggames.wither.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.beardoggames.wither.GameMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameMain.WIDTH;
		config.height = GameMain.HEIGHT;
		new LwjglApplication(new GameMain(), config);
	}
}
