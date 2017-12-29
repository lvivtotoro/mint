package org.midnightas.mint.game;

import java.util.Locale;

import org.midnightas.mint.engine.Tween;
import org.midnightas.mint.game.screens.LoadingScreen;
import org.midnightas.mint.game.screens.TransitionScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Mint extends Game {

	public static final float PPPM = 1;

	public static Mint mint;

	public AssetManager assets;

	public BitmapFont font;

	@Override
	public void create() {
		Box2D.init();
		mint = this;

		Gdx.input.setCursorCatched(true);

		// DisplayMode mode = Gdx.graphics.getDisplayMode();
		// Gdx.graphics.setFullscreenMode(mode);
		 Gdx.graphics.setWindowedMode(1280, 720);

		assets = new AssetManager();

		FileHandle fontHandle;
		switch (Locale.getDefault().getLanguage()) {
		case "en":
			fontHandle = Gdx.files.internal("GeosansLight.ttf");
			break;
		case "ja":
			fontHandle = Gdx.files.internal("Senobi-Gothic-Regular.ttf");
			break;
		default:
			fontHandle = Gdx.files.internal("Phenomena-Light.otf");
			break;
		}
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(fontHandle);
		font = fontGen.generateFont(new FreeTypeFontParameter() {
			{
				size = 32;
				color = Color.WHITE;
				gamma = 0.2f;
				characters = FreeTypeFontGenerator.DEFAULT_CHARS
						+ "йцукенгшщзхїфівапролджєячсмитьбюЙЦУКЕНГШЩЗХЇФІВАПРОЛДЖЄЯЧСМИТЬБЮҐローディング中…";
			}
		});
		fontGen.dispose();

		assets.load("Ambient_Loop_4.ogg", Music.class);
		assets.load("fly.png", Texture.class);
		assets.load("key.png", Texture.class);
		assets.load("easter.png", Texture.class);
		for (int i = 1; i <= 7; i++)
			assets.load("easter/" + i + ".ogg", Music.class);

		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		super.render();

		Tween.updateAll();
	}

	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
		font.dispose();
	}

	public void transition(Screen s) {
		if (getScreen() instanceof TransitionScreen) {
			// to allow skipping if necessary
			TransitionScreen tra = (TransitionScreen) getScreen();
			tra.one = tra.two;
			tra.two = s;
		} else {
			setScreen(new TransitionScreen(getScreen(), s));
		}
	}

	/**
	 * Pass 0, 0 to the {@code interval} and {@code times} to run only once
	 * 
	 * @param runnable
	 *            What to call
	 * @param time
	 *            Delay in seconds
	 * @param interval
	 *            Interval in seconds
	 * @param times
	 *            Repeat amount
	 * @return
	 */
	public static Task schedule(Runnable runnable, float time, float interval, int times) {
		return Timer.schedule(new Task() {
			public void run() {
				runnable.run();
			}
		}, time, interval, times);
	}

}
