package de.vernideas.rpg.kingscourier.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.vernideas.libgdx.BaseGame;
import de.vernideas.rpg.kingscourier.data.SaveKey;

/** Main game state */
public class Running extends ScreenAdapter {
	private static Screen defaultInstance = null;

	public static Screen instance(BaseGame game) {
		if( null == defaultInstance ) {
			defaultInstance = new Running(game);
		}
		return defaultInstance;
	}

	private final BaseGame game;
	private final Stage stage;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private final Vector2 direction = new Vector2();
	private final Vector2 norDirection = new Vector2();
	private final Vector2 moveDirection = new Vector2();
	private float speed = 1.2f;
	private int mapWidth;
	private int mapHeight;
	private float minX;
	private float minY;
	private float maxX;
	private float maxY;

	private Running(BaseGame game) {
		this.game = game;
		stage = new Stage();
	}

	@Override public void render(float delta) {
		norDirection.set(direction).nor().scl(speed);
		moveDirection.lerp(norDirection, delta * 10f);
		camera.translate(moveDirection);
		if( camera.position.x < minX ) {
			camera.position.x = minX;
		}
		if( camera.position.y < minY ) {
			camera.position.y = minY;
		}
		if( camera.position.x > maxX ) {
			camera.position.x = maxX;
		}
		if( camera.position.y > maxY ) {
			camera.position.y = maxY;
		}
		camera.update();
		renderer.setView(camera);

		Gdx.gl.glClearColor(0.5f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// stage.act(delta);
		// stage.draw();
		renderer.render();
	}

	@Override public void resize(int width, int height) {
		camera.setToOrtho(false, width / 32f, height / 32f);
		minX = width / 64f;
		minY = height / 64f;
		maxX = mapWidth * 2f - width / 64f;
		maxY = mapHeight * 2f - height / 64f;
		renderer.setView(camera);
	}

	@Override public void show() {
		Gdx.input.setInputProcessor(new RunningInputProcessor(this));
		setMap(game.saveData().getString(SaveKey.CURRENT_MAP, "town8"));
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		renderer.setView(camera);
		direction.set(0, 0);
	}

	@Override public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override public void dispose() {
		stage.dispose();
		renderer.dispose();
	}

	private void setMap(String mapName) {
		map = game.assets().map(mapName);
		mapWidth = (Integer) map.getProperties().get("width");
		mapHeight = (Integer) map.getProperties().get("height");
	}

	private static class RunningInputProcessor extends InputAdapter {
		private final Running state;

		public RunningInputProcessor(Running state) {
			this.state = state;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.badlogic.gdx.InputAdapter#keyDown(int)
		 */
		@Override public boolean keyDown(int keycode) {
			if( keycode == Input.Keys.LEFT ) {
				state.direction.x -= 1;
			}
			if( keycode == Input.Keys.RIGHT ) {
				state.direction.x += 1;
			}
			if( keycode == Input.Keys.DOWN ) {
				state.direction.y -= 1;
			}
			if( keycode == Input.Keys.UP ) {
				state.direction.y += 1;
			}
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.badlogic.gdx.InputAdapter#keyUp(int)
		 */
		@Override public boolean keyUp(int keycode) {
			if( keycode == Input.Keys.ESCAPE ) {
				state.game.setScreen(Paused.instance(state.game));
			}
			if( keycode == Input.Keys.LEFT ) {
				state.direction.x += 1;
			}
			if( keycode == Input.Keys.RIGHT ) {
				state.direction.x -= 1;
			}
			if( keycode == Input.Keys.DOWN ) {
				state.direction.y += 1;
			}
			if( keycode == Input.Keys.UP ) {
				state.direction.y -= 1;
			}
			return true;
		}
	}
}
