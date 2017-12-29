package org.midnightas.mint.engine;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

/**
 * Deals with the wibbly wobbly terrain
 * @author Midnightas
 */
public class TerrainMagic {

	public static TerrainMagic instance = new TerrainMagic();

	private boolean backwards = false;
	private float alpha = 1;

	private Interpolation interp;
	private boolean interpClamp = false;

	private float val;

	public void set(int interpId) {
		if (interpId != -2) {
			if (interpId == -1)
				interpId = MathUtils.random(0, 4);
			switch (interpId) {
			case 0:
				interp = Interpolation.bounce;
				interpClamp = true;
				break;
			case 1:
				interp = Interpolation.elastic;
				interpClamp = true;
				break;
			case 2:
				interp = Interpolation.sine;
				interpClamp = true;
				break;
			case 3:
				interp = Interpolation.swing;
				interpClamp = false;
				break;
			case 4:
				interp = Interpolation.smooth;
				interpClamp = true;
				break;
			case 5:
				interp = new IncreasingInterpolation(0.03f, true);
				break;
			}
		} else {
			// -2 means no magic
			interp = null;
		}
	}
	
	public float get() {
		return this.val;
	}

	public void update() {
		if (!backwards) {
			alpha += 0.01f;
			if (alpha >= 1)
				backwards = true;
		} else {
			alpha -= 0.01f;
			if (alpha <= 0)
				backwards = false;
		}

		val = interp != null ? interp.apply(alpha) : 1;
		if (interpClamp)
			val = Math.max(val, 0.001f);
	}

}
