package org.midnightas.mint.engine.entities;

import org.midnightas.mint.engine.MintW;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class ModelEntity extends Entity implements RenderableProvider {

	public ModelInstance modelInstance;

	public ModelEntity(MintW mintw, float x, float y, float w, float h, Model model, String userdata) {
		super(mintw, w, h, x, y, true, userdata);

		modelInstance = new ModelInstance(model);
	}

	@Override
	public void update() {
		super.update();

		Vector2 pos = body.getPosition();

		// M13 is the y coord
		modelInstance.transform.setTranslation(pos.x, modelInstance.transform.val[Matrix4.M13], pos.y);
	}

	public void dispose() {
		super.dispose();
		modelInstance.model.dispose();
	}

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
		modelInstance.getRenderables(renderables, pool);
	}

}
