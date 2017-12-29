package org.midnightas.mint.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImageScreen implements Screen, InputProcessor {

	public Texture tex = new Texture(Gdx.files.internal("mint.png"));
	public float size;

	public SpriteBatch batch = new SpriteBatch();

	public ImageScreen(FileHandle file, float size) {
		this(new Texture(file), size);
	}

	public ImageScreen(Texture tex, float size) {
		this.tex = tex;
		this.size = size;
	}

	@Override
	public void show() {
		Gdx.input.setCursorCatched(true);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		float width = tex.getWidth() * size;
		float height = tex.getHeight() * size;

		batch.begin();
		batch.draw(tex, Gdx.graphics.getWidth() / 2 - width / 2, Gdx.graphics.getHeight() / 2 - height / 2, width,
				height);
		batch.end();
	}

	/**
	 * Called when a key is pressed
	 */
	protected void handleKeycode(int keycode) {
	}

	/**
	 * Called when a touch down event occours
	 */
	protected void handleTouch(int button) {
	}
	
	protected void handle() {
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
		tex.dispose();
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		handleKeycode(keycode);
		handle();
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
		handleTouch(button);
		handle();
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
