package org.midnightas.mint.game.entities;

import org.midnightas.mint.engine.MintW;
import org.midnightas.mint.engine.Tween;
import org.midnightas.mint.engine.entities.Entity;
import org.midnightas.mint.engine.entities.FloatyEntity;
import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;

public class EntityWallKey extends FloatyEntity {

	public EntityWallKey(MintW mintw, float x, float y) {
		super(mintw, x, y, Mint.mint.assets.get("key.png", Texture.class));
	}

	@Override
	public void onCollide(Fixture a, Fixture b) {
		super.onCollide(a, b);
		if (b.getBody().getType() != BodyType.DynamicBody)
			return;

		flop();

		for (Entity e : mintw.entities) {
			if (e instanceof EntityWall) {
				EntityWall wall = (EntityWall) e;

				Matrix4 wallTransform = wall.modelInstance.transform;
				Vector3 itsPos = new Vector3();

				new Tween(0, -1, 1).onUpdate(f -> {
					wallTransform.getTranslation(itsPos);
					wallTransform.setTranslation(itsPos.x, f, itsPos.z);
				}).start();
				Mint.schedule(() -> wall.body.getFixtureList().get(0).setSensor(true), 0.5f, 0, 0);
				Mint.schedule(() -> wall.body.getFixtureList().get(0).setSensor(false), 9.5f, 0, 0);
				Mint.schedule(() -> {
					new Tween(-1, 0, 1).onUpdate(f -> {
						wallTransform.getTranslation(itsPos);
						wallTransform.setTranslation(itsPos.x, f, itsPos.z);
					}).start();
				}, 9, 0, 0);
			}
		}
	}

}
