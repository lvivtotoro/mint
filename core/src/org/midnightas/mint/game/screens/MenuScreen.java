package org.midnightas.mint.game.screens;

import org.midnightas.mint.engine.GameScreen;
import org.midnightas.mint.engine.ImageScreen;
import org.midnightas.mint.game.Mint;
import org.midnightas.mint.game.OfficialLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MenuScreen extends ImageScreen {

	public MenuScreen() {
		super(Gdx.files.internal("mint.png"), 0.5f);
	}

	@Override
	public void show() {
		super.show();

		Music bgm = Mint.mint.assets.get("Ambient_Loop_4.ogg", Music.class);
		bgm.setLooping(true);
		bgm.setVolume(0.05f);
		bgm.play();
	}

	@Override
	protected void handle() {
		Gdx.input.setInputProcessor(null);
		Mint.mint.transition(new GameScreen(new OfficialLevel(6)));
	}

}
