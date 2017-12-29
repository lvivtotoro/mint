package org.midnightas.mint.game;

import org.midnightas.mint.engine.GameScreen;
import org.midnightas.mint.engine.MITFile;
import org.midnightas.mint.game.screens.CreditsScreen;

import com.badlogic.gdx.Gdx;

/**
 * Because I am lazy, I put all level-specific important stuff here, instead of
 * using some scripting language like how stuff should be done.
 * 
 * @author Midnightas
 */
public class OfficialLevel extends MITFile {

	public OfficialLevel(final int levelId) {
		super(Gdx.files.internal("official/").child(levelId + ".yml"));

		boolean lastOfficialLevel = !Gdx.files.internal("official/").child((levelId + 1) + ".yml").exists();

		if (lastOfficialLevel) {
			data.finish = w -> Mint.mint.transition(new CreditsScreen());
		} else {
			data.finish = w -> Mint.mint.transition(new GameScreen(new OfficialLevel(levelId + 1)));
		}
	}

}
