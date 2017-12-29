package org.midnightas.mint.game.screens;

import org.midnightas.mint.engine.TextScreen;
import org.midnightas.mint.game.Mint;
import org.midnightas.mint.game.Text;

public class LoadingScreen extends TextScreen {

	public Mint mint;

	public boolean trai = false, transitioning = false;

	public LoadingScreen(Mint mint) {
		super(Text.get(Text.LOADING).toUpperCase());

		this.mint = mint;

		Mint.schedule(() -> trai = true, 0.1f, 0, 0);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (trai) {
			if (this.mint.assets.update()) {
				if (!transitioning) {
					transitioning = true;

					mint.transition(new MenuScreen());
				}
			}
		}
	}

}
