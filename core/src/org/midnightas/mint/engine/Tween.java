package org.midnightas.mint.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;

public class Tween {

	private static final List<Tween> tweens = new ArrayList<Tween>();

	private Consumer<Float> onUpdate;

	private float from, to;
	private float length, life;
	private Interpolation interp;

	private float val;

	public Tween(float from, float to, float length) {
		this(from, to, length, Interpolation.linear);
	}

	public Tween(float from, float to, float length, Interpolation interp) {
		this.from = from;
		this.to = to;
		this.length = length;
		this.interp = interp;
	}

	public Tween onUpdate(Consumer<Float> onUpdate) {
		this.onUpdate = onUpdate;

		return this;
	}

	public Tween start() {
		tweens.add(this);
		return this;
	}

	public float getValue() {
		return this.val;
	}

	private void update() {
		life += Gdx.graphics.getDeltaTime();
		if (life >= length) {
			tweens.remove(this);
		}

		val = interp.apply(from, to, life / length);

		onUpdate.accept(val);
	}

	public static void updateAll() {
		for (int i = 0; i < tweens.size(); i++)
			tweens.get(i).update();
	}

}
