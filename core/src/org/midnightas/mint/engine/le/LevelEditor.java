package org.midnightas.mint.engine.le;

import java.util.ArrayList;
import java.util.List;

import org.midnightas.mint.engine.TerrainPart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LevelEditor implements Screen {

	public final List<ETerrainPart> parts = new ArrayList<ETerrainPart>();

	public ShapeRenderer renderer = new ShapeRenderer();

	public ScreenViewport port = new ScreenViewport();

	public LevelEditor() {
	}

	@Override
	public void show() {
		Gdx.input.setCursorCatched(false);
		Gdx.input.setInputProcessor(new GestureDetector(new LEIP()));
	}

	@Override
	public void render(float delta) {
		renderer.begin(ShapeType.Filled);
		renderer.setProjectionMatrix(port.getCamera().combined);
		for (ETerrainPart part : parts) {
			renderer.setColor(TerrainPart.colors[part.color]);
			renderer.rect(part.x, part.y, part.w, part.h);
		}
		renderer.end();
	}

	@Override
	public void resize(int width, int height) {
		port.update(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	public class LEIP extends GestureAdapter {
		public ETerrainPart part;

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			Vector2 pos = port.unproject(new Vector2(x, y));
			System.out.println(pos);

			if (part == null) {
				part = new ETerrainPart(pos.x, pos.y, 0, 0, 0);
				parts.add(part);
			}

			part.w += deltaX;
			part.y -= deltaY;

			return true;
		}

		@Override
		public boolean panStop(float x, float y, int pointer, int button) {
			if (part == null)
				return false;

			part = null;
			return true;
		}

	}

}
