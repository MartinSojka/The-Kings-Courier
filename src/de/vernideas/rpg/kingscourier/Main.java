package de.vernideas.rpg.kingscourier;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "The-Kings-Courier";
		cfg.width = 800;
		cfg.height = 600;
		cfg.samples = 4;

		new LwjglApplication(new MainGame(), cfg);
	}
}
