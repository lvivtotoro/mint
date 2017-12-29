package org.midnightas.mint.game.entities;

import org.midnightas.mint.engine.MintW;
import org.midnightas.mint.engine.TerrainPart;
import org.midnightas.mint.engine.entities.ModelEntity;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class EntitySphere extends ModelEntity {

	public EntitySphere(MintW mintw, float x, float y, float d, int color) {
		super(mintw, x, y, d, d,
				new ModelBuilder().createSphere(d, d, d, 5, 5, TerrainPart.mats[color], Usage.Normal | Usage.Position),
				"sphere");
	}

	@Override
	public void update() {
		super.update();

		float x = body.getLinearVelocity().x;
		float y = body.getLinearVelocity().y;
		
		modelInstance.transform.rotate(Vector3.Z, x * MathUtils.radiansToDegrees / 2);
		modelInstance.transform.rotate(Vector3.X, y * MathUtils.radiansToDegrees / 2);
	}

}
