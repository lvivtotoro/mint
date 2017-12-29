package org.midnightas.mint.engine.entities;

import org.midnightas.mint.engine.MintW;
import org.midnightas.mint.engine.PlayerController;
import org.midnightas.mint.engine.Tween;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.physics.box2d.Fixture;

public class FloatyEntity extends DecalEntity {

	public FloatyEntity(MintW mintw, float x, float y, Texture texture) {
		super(mintw, 0.3f, 0.3f, x, y, false, "ascender", Decal.newDecal(0.5f, 0.5f, new TextureRegion(texture), true));
	}

	@Override
	public void onCollide(Fixture zis, Fixture zat) {
		super.onCollide(zis, zat);

		Object o = MintW.getDataThing(zat.getUserData());
		if (o instanceof PlayerController) {
			onCollideWithPlayer((PlayerController) o);
		}
	}

	protected void onCollideWithPlayer(PlayerController player) {
		flop();
	}

	protected void flop() {
		new Tween(0, 10, 1, Interpolation.pow4In).onUpdate(f -> decal.rotateY(f)).start();
	}

}
