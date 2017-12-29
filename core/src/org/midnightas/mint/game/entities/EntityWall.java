package org.midnightas.mint.game.entities;

import org.midnightas.mint.engine.MintW;
import org.midnightas.mint.engine.TerrainMagic;
import org.midnightas.mint.engine.TerrainPart;
import org.midnightas.mint.engine.entities.ModelEntity;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class EntityWall extends ModelEntity {

	public EntityWall(MintW mintw, float x, float y, float w, float h, int color, BodyType type) {
		super(mintw, x, y, w, h,
				new ModelBuilder().createBox(w, 1, h, TerrainPart.mats[color], Usage.Normal | Usage.Position), "wall");
		body.setType(type);
	}

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
		super.getRenderables(renderables, pool);

		for (Renderable r : renderables) {
			r.worldTransform.scale(1, TerrainMagic.instance.get(), 1);
		}
	}

}
