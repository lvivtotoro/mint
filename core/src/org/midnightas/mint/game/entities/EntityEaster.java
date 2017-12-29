package org.midnightas.mint.game.entities;

import org.midnightas.mint.engine.MintW;
import org.midnightas.mint.engine.entities.FloatyEntity;
import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class EntityEaster extends FloatyEntity {

	private boolean playing = false;

	private int nextLine = 1;

	public EntityEaster(MintW mintw, float x, float y) {
		super(mintw, x, y, Mint.mint.assets.get("easter.png", Texture.class));
	}

	@Override
	public void onCollide(Fixture zis, Fixture zat) {
		super.onCollide(zis, zat);
		if (zat.getBody().getType() != BodyType.DynamicBody)
			return;

		flop();

		if (!playing) {
			playing = true;

			try {
				Music music = Mint.mint.assets.get("easter/" + nextLine++ + ".ogg", Music.class);
				music.play();

				music.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(Music music) {
						playing = false;
					}
				});
			} catch (GdxRuntimeException assetNotLoaded) {
				playing = false;
			}
		}
	}

}
