package org.midnightas.mint.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.midnightas.mint.engine.entities.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class MintW implements Disposable, ContactListener {

	public World box2d;

	public Terrain terrain;

	public PlayerController playerCont;

	public Data data;

	public List<Entity> entities = new ArrayList<Entity>();

	public MintW(List<TerrainPart> terrainParts, Data data) {
		this.data = data;

		this.box2d = new World(new Vector2(), true);
		this.box2d.setContactListener(this);

		this.terrain = new Terrain(this, terrainParts);

		this.playerCont = new PlayerController(this);

		if (data.onStart != null)
			data.onStart.accept(this);
	}

	public void finish() {
		if (data.finish != null) {
			data.finish.accept(this);
		}
	}

	public void restart() {
		if (data.restart != null) {
			data.restart.accept(this);
		}
	}

	public void dispose() {
		box2d.dispose();
		terrain.dispose();

		for (Entity e : entities) {
			e.dispose();
		}
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();

		if ((entityTypeEquals(a.getUserData(), "goal") && entityTypeEquals(b.getUserData(), "plyare"))
				|| (entityTypeEquals(a.getUserData(), "plyare") && entityTypeEquals(b.getUserData(), "goal"))) {
			Gdx.app.postRunnable(this::finish);
		} else if ((entityTypeEquals(a.getUserData(), "restart") && entityTypeEquals(b.getUserData(), "plyare"))
				|| (entityTypeEquals(a.getUserData(), "plyare") && entityTypeEquals(b.getUserData(), "restart"))) {
			Gdx.app.postRunnable(this::restart);
		} else {
			Object dataA = getDataThing(a.getUserData());
			if (dataA instanceof Entity) {
				((Entity) dataA).onCollide(a, b);
			}

			Object dataB = getDataThing(b.getUserData());
			if (dataB instanceof Entity) {
				((Entity) dataB).onCollide(b, a);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

	public static boolean compareEntityTypes(Object a, Object b) {
		return compareEntityTypes((UserData) a, (UserData) b);
	}

	public static boolean compareEntityTypes(UserData a, UserData b) {
		if (a == null || b == null)
			return false;

		return a.type.equals(b.type);
	}

	public static boolean entityTypeEquals(Object data, String type) {
		if (data == null)
			return false;
		// if(((UserData) data).thing instanceof Fixture)
		// System.out.println(((Fixture) ((UserData)
		// data).thing).getBody().getType());
		return ((UserData) data).type.equals(type);
	}

	public static Object getDataThing(Object data) {
		if (data == null)
			return null;
		if (!(data instanceof UserData))
			return null;

		return ((UserData) data).thing;
	}

	public static class Data {
		public Consumer<MintW> onStart;
		public Consumer<MintW> finish;
		public Consumer<MintW> restart;

		public int interpId = -1;

		public boolean flyPower = false;
	}

	public void update() {
		box2d.step(1.0f / 60.0f, 20, 20);

		for (Entity e : entities) {
			e.update();
		}
	}

}
