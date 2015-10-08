package de.vernideas.rpg.kingscourier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;

import de.vernideas.libgdx.BaseGame;
import de.vernideas.rpg.kingscourier.data.Assets;
import de.vernideas.rpg.kingscourier.data.Save;
import de.vernideas.rpg.kingscourier.gamestate.MainMenu;
import de.vernideas.rpg.kingscourier.graphics.FlatObliqueCamera;

public class MainGame extends BaseGame {
	private OrthographicCamera mainCamera;
	private Assets assets;
	private Save saveData;

	public Environment environment;

	public ModelBatch modelBatch;
	public Model model;
	public ModelInstance instance;

	@Override public void create() {
		assets = new Assets();
		assets.queueLoading();

		saveData = Save.empty();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		mainCamera = new FlatObliqueCamera(w, h);
		mainCamera.position.set(0, 0, 1000f);
		mainCamera.far = 2000f;
		mainCamera.zoom = 1 / 32.0f;
		mainCamera.update();

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.35f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.9f, 0.85f, 0.8f, 1f, 2f, -2f));

		modelBatch = new ModelBatch();

		//ModelLoader<ObjLoader.ObjLoaderParameters> loader = new ObjLoader();
		//model = loader.loadModel(Gdx.files.internal("data/house1.obj"));
		//instance = new ModelInstance(model);

		setScreen(MainMenu.instance(this));
	}

	@Override public void dispose() {
		super.dispose();
		modelBatch.dispose();
		model.dispose();
	}

	private float time;

	private float cameraX = 0.0f;
	private float cameraY = 0.0f;

	@Override public void render() {
		super.render();
	}

	@Override public void resize(int width, int height) {
		super.resize(width, height);
		// mainCamera.viewportWidth = width;
		// mainCamera.viewportHeight = height;
		// mainCamera.update();
	}

	@Override public void pause() {
		super.pause();
		// TODO Autosave before quit
	}

	@Override public Assets assets() {
		return assets;
	}

	@Override public Save saveData() {
		return saveData;
	}

	@Override public void saveData(Save saveData) {
		this.saveData = saveData;
	}
}
