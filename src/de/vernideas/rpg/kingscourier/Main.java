package de.vernideas.rpg.kingscourier;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("The-Kings-Courier");
		cfg.setWindowedMode(800, 600);

		new Lwjgl3Application(new MainGame(), cfg);
	}
}
