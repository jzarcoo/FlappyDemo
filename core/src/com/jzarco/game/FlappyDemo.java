package com.jzarco.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jzarco.game.states.GameStateManager;
import com.jzarco.game.states.MenuState;

public class FlappyDemo extends ApplicationAdapter {

	/* Screen width. */
	public static final int WIDTH = 480;
	/* Screen height. */
	public static final int HEIGHT = 800;
	/* Screen title. */
	public static final String TITLE = "Flappy Bird";

	/* Manage game states. */
	private GameStateManager gsm;
	/* SpriteBatch is used to render sprites efficiently.
	It is a very heavy file. */
	private SpriteBatch batch;
	/* Music of the FlappyDemo. */
	private Music music;

	// SpriteBatch batch;
	// Texture img;

	/**
	 * Creates.
	 */
	@Override
	public void create () {
		// img = new Texture("badlogic.jpg");
		batch = new SpriteBatch();
		gsm = new GameStateManager();

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();

		ScreenUtils.clear(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	/**
	 * Renders.
	 */
	@Override
	public void render () {
		// batch.begin();
		// batch.draw(img, 0, 0);
		// batch.end();

		// Clear color buffer before rendering new game frame.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	/**
	 * Disposes.
	 */
	@Override
	public void dispose () {
		// img.dispose();
		super.dispose();
		batch.dispose();
		music.dispose();
	}
}
