package de.vernideas.rpg.kingscourier.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.vernideas.libgdx.BaseGame;

/** Paused game state */
public class Paused extends ScreenAdapter {
	private static Screen defaultInstance = null;

	public static Screen instance(BaseGame game) {
		if( null == defaultInstance ) {
			defaultInstance = new Paused(game);
		}
		return defaultInstance;
	}

	private final BaseGame game;
	private final Stage stage;

	private Paused(BaseGame game) {
		this.game = game;
		stage = new Stage();
	}

	@Override public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.6f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override public void show() {
		Gdx.input.setInputProcessor(new PausedInputProcessor(this));
	}

	@Override public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override public void dispose() {
		stage.dispose();
	}

	private static class PausedInputProcessor extends InputAdapter {
		private final Paused state;

		public PausedInputProcessor(Paused state) {
			this.state = state;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.badlogic.gdx.InputAdapter#keyUp(int)
		 */
		@Override public boolean keyUp(int keycode) {
			if( keycode == Input.Keys.ESCAPE ) {
				state.game.closeScreen();
			}
			return true;
		}
	}

}
