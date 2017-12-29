package org.midnightas.mint.engine;

import com.badlogic.gdx.math.Interpolation;

public class IncreasingInterpolation extends Interpolation {

	private float multiplier;
	private boolean accelerating;
	
	private float b;
	
	public IncreasingInterpolation(float multiplier, boolean accelerating) {
		this.multiplier = multiplier;
		this.accelerating = accelerating;
	}

	@Override
	public float apply(float a) {
		b += a * multiplier;
		
		if(accelerating)
			multiplier *= 1.0005f;
		
		return b;
	}

}
