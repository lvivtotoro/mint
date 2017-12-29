package org.midnightas.mint.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.midnightas.mint.engine.MintW.Data;
import org.midnightas.mint.game.entities.EntityAscender;
import org.midnightas.mint.game.entities.EntityEaster;
import org.midnightas.mint.game.entities.EntitySphere;
import org.midnightas.mint.game.entities.EntityWall;
import org.midnightas.mint.game.entities.EntityWallKey;
import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

@SuppressWarnings("unchecked")
public class MITFile {

	protected List<TerrainPart> parts = new ArrayList<TerrainPart>();

	protected Yaml yamlFile;
	protected Map<String, Object> yaml;

	protected Data data;

	public MITFile(FileHandle file) {
		this(file.readString("UTF-8"));
	}

	public MITFile(String markup) {
		yamlFile = new Yaml();
		yaml = yamlFile.load(markup);

		data = new Data();

		Map<String, Object> yamlData = (Map<String, Object>) yaml.get("data");
		if (yamlData != null)
			data.interpId = (Integer) yamlData.get("interpId");

		for (Map<String, Object> part : (List<Map<String, Object>>) yaml.get("parts")) {
			float x = ((Number) part.get("x")).floatValue();
			float y = ((Number) part.get("y")).floatValue();
			float w = ((Number) part.get("w")).floatValue();
			float h = ((Number) part.get("h")).floatValue();
			boolean goal = part.containsKey("g") ? (Boolean) part.get("g") : false;
			boolean restart = part.containsKey("r") ? (Boolean) part.get("r") : false;
			int c = goal || restart ? 4 : (Integer) part.get("c");

			parts.add(new TerrainPart(w, h, x, y, c, goal ? "goal" : restart ? "restart" : "wall"));
		}
	}

	public MintW load() {
		MintW mintw = new MintW(parts, data);

		if (yaml.get("entities") != null)
			for (Map<String, Object> ent : (List<Map<String, Object>>) yaml.get("entities")) {
				float x = ((Number) ent.get("x")).floatValue();
				float y = ((Number) ent.get("y")).floatValue();
				switch ((String) ent.get("type")) {
				case "ascender": {
					mintw.entities.add(new EntityAscender(mintw, x, y));
					break;
				}
				case "wallkey": {
					mintw.entities.add(new EntityWallKey(mintw, x, y));
					break;
				}
				case "wall": {
					float w = ((Number) ent.get("w")).floatValue();
					float h = ((Number) ent.get("h")).floatValue();
					int c = (Integer) ent.get("c");
					String t = (String) ent.get("t");
					mintw.entities.add(new EntityWall(mintw, x, y, w, h, c, BodyType.valueOf(t)));
					break;
				}
				case "sphere": {
					float d = ((Number) ent.get("d")).floatValue();
					float h = ((Number) ent.get("h")).floatValue();
					int c = (Integer) ent.get("c");
					mintw.entities.add(new EntitySphere(mintw, x, y, d, c) {
						{
							modelInstance.transform.val[Matrix4.M13] = h;
						}
					});
					break;
				}
				case "easter": {
					mintw.entities.add(new EntityEaster(mintw, x, y));
					break;
				}
				}
			}

		return mintw;
	}

}
