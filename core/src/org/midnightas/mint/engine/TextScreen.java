package org.midnightas.mint.engine;

import org.midnightas.mint.game.Mint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextScreen implements Screen, InputProcessor {

	public GlyphLayout glyph;
	public String text;

	public SpriteBatch batch = new SpriteBatch();

	public TextScreen(String text) {
		this.text = text;
		this.glyph = new GlyphLayout(Mint.mint.font, text);
	}

	@Override
	public void show() {
		Gdx.input.setCursorCatched(true);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		float x = Gdx.graphics.getWidth() / 2 - glyph.width / 2;
		float y = Gdx.graphics.getHeight() / 2 + glyph.height / 2;

		batch.begin();
		Mint.mint.font.setColor(TerrainPart.colors[3]);
		Mint.mint.font.draw(batch, text, x, y);
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
