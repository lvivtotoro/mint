package com.badlogic.gdx.graphics.g3d.decals;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Array;

public class MintCameraGroupStrategy extends CameraGroupStrategy {

	public MintCameraGroupStrategy(Camera camera) {
		super(camera);
	}

	@Override
	public void beforeGroup(int group, Array<Decal> contents) {
		super.beforeGroup(group, contents);
	}

}
