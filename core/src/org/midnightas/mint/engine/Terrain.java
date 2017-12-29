package org.midnightas.mint.engine;

import java.util.List;

import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Terrain extends ModelCache {

	public Body body;

	public Terrain(MintW world, List<TerrainPart> parts) {
		body = world.box2d.createBody(new BodyDef() {
			{
				type = BodyType.StaticBody;
			}
		});

		begin();
		for (TerrainPart part : parts) {
			// model part
			add(part);
			// physics part
			Fixture f = body.createFixture(part.fixture);
			f.setUserData(new UserData(part.userData, f));
		}
		end();

		TerrainMagic.instance.set(world.data.interpId);
	}

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
		super.getRenderables(renderables, pool);

		for (Renderable r : renderables) {
			r.worldTransform.setToScaling(1, TerrainMagic.instance.get(), 1);
			r.worldTransform.rotate(Vector3.Y, body.getAngle() * MathUtils.radiansToDegrees);
		}
	}

}
