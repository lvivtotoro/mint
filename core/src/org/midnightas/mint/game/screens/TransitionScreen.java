package org.midnightas.mint.game.screens;

import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;

public class TransitionScreen implements Screen, InputProcessor {

	public ShapeRenderer whiteShape = new ShapeRenderer();

	public Screen one, two;

	public float alpha = 0;

	public TransitionScreen(Screen one, Screen two) {
		this.one = one;
		this.two = two;

		if (one == null)
			alpha = 0.5f;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		alpha += delta * 0.25f;

		if (Interpolation.fade.apply(alpha) < 0.5f) {
			one.render(delta);
		} else {
			if (two != null) {
				two.render(delta);
			} else {
				Gdx.app.exit();
			}
		}

		if (whiteShape != null) {
			Gdx.gl20.glEnable(GL20.GL_BLEND);
			Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			whiteShape.begin(ShapeType.Filled);
			whiteShape.setColor(1, 1, 1, alpha < 0.5f ? alpha * 2 : (1 - alpha) * 2);
			whiteShape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			whiteShape.end();
			Gdx.gl20.glDisable(GL20.GL_BLEND);
		}

		if (alpha >= 1) {
			finish();
		}
	}

	private boolean finishing = false;

	public void finish() {
		if (finishing)
			return;
		finishing = true;

		dispose();
		if (two != null) {
			Mint.mint.setScreen(two);
		} else {
			Gdx.app.exit();
		}
	}

	@Override
	public void resize(int width, int height) {
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
		if (one != null)
			one.dispose();

		whiteShape.dispose();
		whiteShape = null;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE) {
			finish();
		}
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
		finish();
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
