package de.vernideas.rpg.kingscourier.gamestate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import de.vernideas.libgdx.BaseGame;

public class MainMenu extends ScreenAdapter {
	private static Screen defaultInstance = null;

	public static Screen instance(BaseGame game) {
		if( null == defaultInstance ) {
			defaultInstance = new MainMenu(game);
		}
		return defaultInstance;
	}

	private final BaseGame game;
	private final Stage stage;
	private Skin skin;
	private Table table;
	private Label title;
	private TextButton newGameButton;
	private TextButton loadGameButton;
	private TextButton exitButton;

	private MainMenu(final BaseGame game) {
		this.game = game;
		stage = new Stage(new ScreenViewport());
		table = new Table();
		skin = game.assets().menuSkin();
		title = new Label("The King's Courier", skin);
		newGameButton = new TextButton("New game", skin);
		loadGameButton = new TextButton("Load game", skin);
		exitButton = new TextButton("Exit", skin);

		table.add(title).padBottom(40).row();
		table.add(newGameButton).size(150, 60).padBottom(20).row();
		table.add(loadGameButton).size(150, 60).padBottom(20).row();
		table.add(exitButton).size(150, 60).padBottom(20).row();

		table.setFillParent(true);
		stage.addActor(table);

		newGameButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				Gdx.input.setInputProcessor(null);
				table.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
					@Override public void run() {
						game.setScreen(Running.instance(game));
					}
				})));
			}
		});
		loadGameButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				Gdx.input.setInputProcessor(null);
				table.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
					@Override public void run() {
						game.setScreen(Loading.instance(game));
					}
				})));
			}
		});
		exitButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				Gdx.input.setInputProcessor(null);
				table.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(new Runnable() {
					@Override public void run() {
						Gdx.app.exit();
					}
				})));
			}
		});
	}

	@Override public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override public void show() {
		Gdx.input.setInputProcessor(stage); // Make the stage consume events
	}

	@Override public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override public void dispose() {
		stage.dispose();
		skin.dispose();
	}
}
