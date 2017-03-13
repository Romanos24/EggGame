package com.egg.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.egg.game.GameScreen;
import com.egg.game.drop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Falling eggs";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new drop(), config);
	}
}
