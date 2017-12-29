package org.midnightas.mint.engine;

import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;

public class PlayerController {

	public static final float PLAYER_SPEED = 0.02f;

	public Body body;

	public float ascendingSpeed = 0;

	public PlayerController(MintW mint) {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		bdef.bullet = true;
		bdef.linearDamping = 5f / Mint.PPPM;

		CircleShape shape = new CircleShape();
		shape.setRadius(0.2f / Mint.PPPM);

		body = mint.box2d.createBody(bdef);
		body.createFixture(shape, 1).setUserData(new UserData("plyare", this));

		shape.dispose();
	}

	public void ascend(float time, float speed) {
		if (ascendingSpeed != 0)
			return;

		ascendingSpeed += speed;

		// what the fuuuuuckkkk
		Mint.schedule(() -> {
			body.getFixtureList().get(0).setSensor(true);
			Mint.schedule(() -> {
				ascendingSpeed -= speed * 3;
				Mint.schedule(() -> {
					body.getFixtureList().get(0).setSensor(false);
					Mint.schedule(() -> {
						ascendingSpeed += speed * 2;
					}, time / 4, 0, 0);
				}, time / 4, 0, 0);
			}, time / 2, 0, 0);
		}, time / 2, 0, 0);
	}

}
