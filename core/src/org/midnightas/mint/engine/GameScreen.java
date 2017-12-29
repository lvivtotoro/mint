package org.midnightas.mint.engine;

import static org.midnightas.mint.engine.PlayerController.PLAYER_SPEED;

import org.midnightas.mint.engine.entities.DecalEntity;
import org.midnightas.mint.engine.entities.Entity;
import org.midnightas.mint.engine.entities.ModelEntity;
import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.MintCameraGroupStrategy;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen, InputProcessor {

	public MintW w;

	public PerspectiveCamera cam = new PerspectiveCamera(67, 1280, 1280);
	public ScreenViewport port = new ScreenViewport(cam);

	public ModelBatch modelBatch = new ModelBatch();
	public DecalBatch decalBatch = new DecalBatch(new MintCameraGroupStrategy(cam));

	public Box2DDebugRenderer debug = new Box2DDebugRenderer(true, true, true, true, true, true);

	public SpriteBatch spriteBatch = new SpriteBatch();

	public Texture powerTextureFly = Mint.mint.assets.get("fly.png", Texture.class);

	public GameScreen(MITFile level) {
		cam.far = 1000;
		cam.near = 0.1f;
		cam.position.y = 0.001f;
		cam.update();

		w = level.load();

		// walls upon walls of spaghetti code begins
		w.data.restart = w -> {
			Mint.mint.transition(new GameScreen(level));
		};
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void render(float delta) {
		Vector2 playerPos = w.playerCont.body.getPosition();
		cam.position.y += w.playerCont.ascendingSpeed;
		cam.position.lerp(new Vector3(playerPos.x, cam.position.y, playerPos.y), 0.1f);
		cam.update();

		TerrainMagic.instance.update();

		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		modelBatch.begin(cam);
		modelBatch.render(w.terrain);
		for (Entity e : w.entities) {
			if (e instanceof DecalEntity)
				decalBatch.add(((DecalEntity) e).decal);
			else if (e instanceof ModelEntity)
				modelBatch.render((ModelEntity) e);
		}
		modelBatch.end();
		decalBatch.flush();
		Gdx.gl20.glDisable(GL20.GL_BLEND);

		if (Gdx.input.isKeyPressed(Keys.F1)) {
			Matrix4 matrix = cam.combined.cpy();
			matrix.rotate(Vector3.X, -90);
			matrix.scale(1, -1, 1);
			debug.render(w.box2d, matrix);
		}
		if (Gdx.input.isKeyPressed(Keys.F2)) {
			cam.position.y += 0.01f;
		}
		if (Gdx.input.isKeyPressed(Keys.F3)) {
			cam.position.y -= 0.01f;
		}

		spriteBatch.begin();
		Mint.mint.font.setColor(1, 1, 1, 1);
		if (w.data.flyPower) {
			spriteBatch.draw(powerTextureFly, 16, 16, 128, 128);

			GlyphLayout bounds = new GlyphLayout(Mint.mint.font, "1");
			Mint.mint.font.draw(spriteBatch, "1", 16 + 64 - bounds.width / 2, 16 + 64 + bounds.height / 2);
		}
		spriteBatch.end();

		update();
	}

	public void update() {
		w.update();

		if (Gdx.input.isKeyJustPressed(Keys.F4)) {
			Fixture playerFixture = w.playerCont.body.getFixtureList().get(0);
			playerFixture.setSensor(!playerFixture.isSensor());
		}

		if (Gdx.input.isKeyPressed(Keys.W)) {
			Vector3 vec = cam.direction.cpy().crs(Vector3.Y);

			float angle = (float) Math.atan2(vec.z, vec.x) - 90 * MathUtils.degRad;
			float velX = MathUtils.cos(angle) * PLAYER_SPEED / Mint.PPPM;
			float velY = MathUtils.sin(angle) * PLAYER_SPEED / Mint.PPPM;
			w.playerCont.body.applyLinearImpulse(new Vector2(velX, velY), w.playerCont.body.getWorldCenter(), true);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			Vector3 vec = cam.direction.cpy().crs(Vector3.Y);

			float angle = (float) Math.atan2(vec.z, vec.x) - 90 * MathUtils.degRad;
			float velX = MathUtils.cos(angle) * PLAYER_SPEED / Mint.PPPM;
			float velY = MathUtils.sin(angle) * PLAYER_SPEED / Mint.PPPM;
			w.playerCont.body.applyLinearImpulse(new Vector2(-velX, -velY), w.playerCont.body.getWorldCenter(), true);
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			Vector3 vec = cam.direction.cpy().crs(Vector3.Y);

			float angle = (float) Math.atan2(vec.z, vec.x);
			float velX = MathUtils.cos(angle) * PLAYER_SPEED / Mint.PPPM;
			float velY = MathUtils.sin(angle) * PLAYER_SPEED / Mint.PPPM;
			w.playerCont.body.applyLinearImpulse(new Vector2(-velX, -velY), w.playerCont.body.getWorldCenter(), true);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			Vector3 vec = cam.direction.cpy().crs(Vector3.Y);

			float angle = (float) Math.atan2(vec.z, vec.x) + 180 * MathUtils.degRad;
			float velX = MathUtils.cos(angle) * PLAYER_SPEED / Mint.PPPM;
			float velY = MathUtils.sin(angle) * PLAYER_SPEED / Mint.PPPM;
			w.playerCont.body.applyLinearImpulse(new Vector2(-velX, -velY), w.playerCont.body.getWorldCenter(), true);
		}

		if (Gdx.input.isKeyJustPressed(Keys.NUM_1) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_1)) {
			if (w.data.flyPower) {
				w.playerCont.ascend(6, 0.005f);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		port.update(width, height);

		try {
			// libgdx plz fix
			Field field = ClassReflection.getDeclaredField(Box2DDebugRenderer.class, "renderer");
			field.setAccessible(true);

			ShapeRenderer renderer = (ShapeRenderer) field.get(debug);
			renderer.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, width, height));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		w.dispose();
		debug.dispose();
		modelBatch.dispose();
		decalBatch.dispose();
		spriteBatch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		float deltaX = -Gdx.input.getDeltaX() * 0.5f;
		float deltaY = -Gdx.input.getDeltaY() * 0.5f;
		cam.direction.rotate(cam.up, deltaX);
		cam.direction.rotate(new Vector3(cam.direction).crs(cam.up).nor(), deltaY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
