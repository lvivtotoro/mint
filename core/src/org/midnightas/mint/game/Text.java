package org.midnightas.mint.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

public class Text {

	public static final String LOADING = "loading";
	public static final String CREDITS = "credits";

	private static I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("langs/translation"));

	public static String get(String key) {
		return bundle.get(key);
	}

	public static String format(String key, Object... args) {
		return bundle.format(key, args);
	}

}
