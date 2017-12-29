package org.midnightas.mint.engine.entities;

import org.midnightas.mint.engine.MintW;

import com.badlogic.gdx.graphics.g3d.decals.Decal;

public class DecalEntity extends Entity {

	public Decal decal;

	public DecalEntity(MintW mintw, float w, float h, float x, float y, boolean collidable, String userdata,
			Decal decal) {
		super(mintw, w, h, x, y, collidable, userdata);

		this.decal = decal;
		this.decal.setPosition(x, 0f, y);
	}
	
	@Override
	public void update() {
		decal.rotateY(0.5f);
	}

}
