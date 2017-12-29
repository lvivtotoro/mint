package org.midnightas.mint.engine.entities;

import org.midnightas.mint.engine.MintW;
import org.midnightas.mint.engine.UserData;
import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;

public class Entity implements Disposable {

	protected MintW mintw;
	
	public Body body;

	public Entity(MintW mintw, float w, float h, float x, float y, boolean collidable, String userdata) {
		this.mintw = mintw;
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(x / Mint.PPPM, y / Mint.PPPM);
		bdef.type = BodyType.DynamicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w / Mint.PPPM / 2, h / Mint.PPPM / 2);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = !collidable;

		body = mintw.box2d.createBody(bdef);
		body.createFixture(fdef).setUserData(new UserData(userdata, this));
	}

	public void update() {
	}

	public void onCollide(Fixture zis, Fixture zat) {
	}
	
	@Override
	public void dispose() {
	}

}
