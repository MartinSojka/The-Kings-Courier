package de.vernideas.rpg.kingscourier.gamestate;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import de.vernideas.libgdx.BaseGame;
import de.vernideas.rpg.kingscourier.data.Save;

/** Loading a save */
public class Loading extends ScreenAdapter {
	private static Screen defaultInstance = null;

	public static Screen instance(BaseGame game) {
		if( null == defaultInstance ) {
			defaultInstance = new Loading(game);
		}
		return defaultInstance;
	}

	private final BaseGame game;
	private final Stage stage;

	private Loading(BaseGame game) {
		this.game = game;
		stage = new Stage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#show()
	 */
	@Override public void show() {
		stage.addAction(Actions.sequence(Actions.delay(0.5f), Actions.run(new Runnable() {
			@Override public void run() {
				game.saveData(Save.load());
			}
		}), Actions.delay(0.5f), Actions.run(new Runnable() {
			@Override public void run() {
				game.setScreen(Running.instance(game));
			}
		})));
	}

	@Override public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override public void dispose() {
		stage.dispose();
	}
}
