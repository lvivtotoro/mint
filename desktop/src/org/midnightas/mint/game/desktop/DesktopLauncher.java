package org.midnightas.mint.game.desktop;

import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mint";
		config.initialBackgroundColor = Color.WHITE;
		config.samples = 8;
		new LwjglApplication(new Mint(), config);
	}
}
