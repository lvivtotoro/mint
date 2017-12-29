package org.midnightas.mint.game.screens;

import org.midnightas.mint.engine.TextScreen;
import org.midnightas.mint.game.Mint;
import org.midnightas.mint.game.Text;

public class CreditsScreen extends TextScreen {

	public CreditsScreen() {
		super(Text.get(Text.CREDITS).toUpperCase());
	}

	@Override
	protected void handle() {
		Mint.mint.transition(null); // ala exit
	}

}
