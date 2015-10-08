package de.vernideas.rpg.kingscourier.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Assets {
	public final AssetManager manager = new AssetManager();
	private Skin menuSkin;

	// TmxMapLoader.Parameters
	private final TmxMapLoader.Parameters mapParams;

	// In here we'll put everything that needs to be loaded in this format:
	// manager.load("file location in assets", fileType.class);
	//
	// libGDX AssetManager currently supports: Pixmap, Texture, BitmapFont,
	// TextureAtlas, TiledAtlas, TiledMapRenderer, Music and Sound.
	public void queueLoading() {
		// manager.load("skins/menuSkin.pack", TextureAtlas.class);
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("map/town8.tmx", TiledMap.class, mapParams);
	}

	// In here we'll create our skin, so we only have to create it once.
	/*
	 * public static void setMenuSkin() {
	 * 
	 * if (menuSkin == null) menuSkin = new
	 * Skin(Gdx.files.internal("skins/menuSkin.json"),
	 * manager.get("skins/menuSkin.pack", TextureAtlas.class)); }
	 */

	public Skin menuSkin() {
		if( null == menuSkin ) {
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/alpine.ttf"));
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = 24;
			BitmapFont font = generator.generateFont(parameter);
			font.setUseIntegerPositions(false);
			parameter.size = 36;
			BitmapFont titleFont = generator.generateFont(parameter);
			titleFont.setUseIntegerPositions(false);
			generator.dispose();

			menuSkin = new Skin();
			menuSkin.add("default", font);

			// Create a texture
			Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
			pixmap.setColor(Color.WHITE);
			pixmap.fill();
			menuSkin.add("background", new Texture(pixmap));

			// Create a button style
			TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
			textButtonStyle.up = menuSkin.newDrawable("background", Color.GRAY);
			textButtonStyle.down = menuSkin.newDrawable("background", Color.DARK_GRAY);
			textButtonStyle.checked = menuSkin.newDrawable("background", Color.DARK_GRAY);
			textButtonStyle.over = menuSkin.newDrawable("background", Color.LIGHT_GRAY);
			textButtonStyle.font = menuSkin.getFont("default");
			menuSkin.add("default", textButtonStyle);

			// And a label style
			Label.LabelStyle labelStyle = new Label.LabelStyle();
			labelStyle.font = titleFont;
			menuSkin.add("default", labelStyle);

		}
		return menuSkin;
	}

	public TiledMap map(String name) {
		String mapFile = "map/" + name + ".tmx";
		manager.load(mapFile, TiledMap.class, mapParams);
		manager.finishLoadingAsset(mapFile);
		return manager.get(mapFile);
	}

	// This function gets called every render() and the AssetManager pauses the
	// loading each frame
	// so we can still run menus and loading screens smoothly
	public boolean update() {
		return manager.update();
	}

	public Assets() {
		mapParams = new TmxMapLoader.Parameters();
		mapParams.textureMinFilter = TextureFilter.Linear;
		mapParams.textureMagFilter = TextureFilter.Nearest;
	}
}
