package de.vernideas.libgdx;

import java.util.Stack;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import de.vernideas.rpg.kingscourier.data.Assets;
import de.vernideas.rpg.kingscourier.data.Save;

public abstract class BaseGame implements ApplicationListener {
	protected Screen screen;
	protected Stack<Screen> previousScreens = new Stack<Screen>();

	@Override public void resize(int width, int height) {
		if( null != screen ) {
			screen.resize(width, height);
		}
	}

	@Override public void render() {
		if( null != screen ) {
			screen.render(Gdx.graphics.getDeltaTime());
		}
	}

	@Override public void pause() {
		if( null != screen ) {
			screen.pause();
		}
	}

	@Override public void resume() {
		if( null != screen ) {
			screen.resume();
		}
	}

	@Override public void dispose() {
		if( null != screen ) {
			screen.hide();
		}
	}

	public abstract Assets assets();

	public abstract Save saveData();

	public abstract void saveData(Save saveData);

	/** Show new {@link Screen}. */
	public void setScreen(Screen newScreen) {
		if( null != screen ) {
			screen.hide();
			previousScreens.push(screen);
		}
		screen = newScreen;
		if( null != screen ) {
			screen.show();
			screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	/**
	 * Close the current {@link Screen}, and return to the previous one if there
	 * are any.
	 */
	public void closeScreen() {
		if( null != screen ) {
			screen.hide();
		}
		if( previousScreens.size() > 0 ) {
			screen = previousScreens.pop();
			if( null != screen ) {
				screen.show();
				screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			}
		} else {
			screen = null;
		}
	}

	/** @return the currently active {@link Screen}. */
	public Screen getScreen() {
		return screen;
	}

}
