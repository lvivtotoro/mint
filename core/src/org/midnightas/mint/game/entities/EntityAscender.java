package org.midnightas.mint.game.entities;

import org.midnightas.mint.engine.MintW;
import org.midnightas.mint.engine.PlayerController;
import org.midnightas.mint.engine.entities.FloatyEntity;
import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.graphics.Texture;

public class EntityAscender extends FloatyEntity {

	public EntityAscender(MintW mintw, float x, float y) {
		super(mintw, x, y, Mint.mint.assets.get("fly.png", Texture.class));
	}

	@Override
	protected void onCollideWithPlayer(PlayerController player) {
		super.onCollideWithPlayer(player);

		player.ascend(6, 0.005f);
	}

}
