package de.vernideas.rpg.kingscourier.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.ObjectMap;

public class Save {
	private static final String DEFAULT_SAVE = "bin/save.json";

	public static Save load() {
		FileHandle fileHandle = Gdx.files.local(DEFAULT_SAVE);
		if( fileHandle.exists() ) {
			Json json = new Json();
			return json.fromJson(Save.class, fileHandle.readString("UTF-8"));
		}
		// Empty save
		return empty();
	}

	public static Save empty() {
		return new Save();
	}

	private final ObjectMap<String, Object> data = new ObjectMap<String, Object>();

	public void save() {
		FileHandle fileHandle = Gdx.files.local(DEFAULT_SAVE);
		Json json = new Json();
		json.setOutputType(OutputType.minimal);
		fileHandle.writeString(json.prettyPrint(this), false, "UTF-8");
	}

	public void clear() {
		data.clear();
	}

	public <T> T get(String key, Class<T> clazz, T defaultValue) {
		Object result = data.get(key, defaultValue);
		if( result.getClass().equals(clazz) ) {
			return clazz.cast(result);
		}
		return defaultValue;
	}

	public <T> T get(String key, Class<T> clazz) {
		return get(key, clazz, (T) null);
	}

	public void set(String key, Object value) {
		data.put(key, value);
	}

	public String getString(String key) {
		return get(key, String.class, (String) null);
	}

	public String getString(String key, String defaultValue) {
		return get(key, String.class, defaultValue);
	}
}
