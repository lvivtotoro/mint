package org.midnightas.mint.engine.le;

public class ETerrainPart {

	public int color;
	public float w, h, x, y;

	public ETerrainPart(float x, float y, float w, float h, int color) {
		this.color = color;

		this.w = w;
		this.h = h;
		this.x = x;
		this.y = y;
	}

}
