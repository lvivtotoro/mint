package org.midnightas.mint.engine;

import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

public class TerrainPart implements Disposable, RenderableProvider {

	public static final Color[] colors = new Color[5];
	static {
		colors[0] = new Color(208f / 255f, 1, 208f / 255f, 1);
		colors[1] = new Color(174f / 255f, 1, 174f / 255f, 1);
		colors[2] = new Color(139f / 255f, 1, 139f / 255f, 1);
		colors[3] = new Color(217f / 255f, 217f / 255f, 217f / 255f, 1);
		colors[4] = new Color(1, 1, 1, 0);
	}

	public static final Material[] mats = new Material[colors.length];
	static {
		for (int i = 0; i < mats.length; i++) {
			mats[i] = new Material(ColorAttribute.createDiffuse(colors[i]));

			boolean isTransparent = colors[i].a < 1;
			if (isTransparent) {
				mats[i].set(new BlendingAttribute(true, 0));
			}
		}
	}

	public ModelInstance modelInstance;

	public FixtureDef fixture;
	public String userData;

	public TerrainPart(float w, float h, float x, float y, int c, String typeOfPart) {
		this.userData = typeOfPart;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w / 2f / Mint.PPPM, h / 2f / Mint.PPPM, new Vector2(x / Mint.PPPM, y / Mint.PPPM), 0);

		modelInstance = new ModelInstance(
				new ModelBuilder().createBox(w, 1, h, mats[c], Usage.Normal | Usage.Position));
		modelInstance.transform.setToTranslation(x, 0, y);

		fixture = new FixtureDef();
		fixture.isSensor = !userData.equals("wall");
		fixture.shape = shape;
	}

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
		modelInstance.getRenderables(renderables, pool);
	}

	public void dispose() {
		modelInstance.model.dispose();
	}

}
