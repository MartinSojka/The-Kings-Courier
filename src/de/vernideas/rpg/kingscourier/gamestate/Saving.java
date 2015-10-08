package de.vernideas.rpg.kingscourier.gamestate;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

import de.vernideas.libgdx.BaseGame;

/** Saving state */
public class Saving extends ScreenAdapter {
	private static Screen defaultInstance = null;

	public static Screen instance(BaseGame game) {
		if( null == defaultInstance ) {
			defaultInstance = new Saving(game);
		}
		return defaultInstance;
	}

	private final BaseGame game;

	private Saving(BaseGame game) {
		this.game = game;
	}

}
