package com.jzarco.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jzarco.game.FlappyDemo;

/**
 * Class to represent a MenuState. A MenuState has a background
 * and a play button.
 */
public class MenuState extends State {

    /* Background of the MenuState. */
    private final Texture bg;
    /* Play button of the MenuState. */
    private final Texture playbtn;

    /**
     * Defines the initial state of a MenuState.
     * @param gsm the game state manager.
     */
    public MenuState (GameStateManager gsm) {
        super(gsm);
        // Camera to set a viewport.
        cam.setToOrtho(false,
                FlappyDemo.WIDTH / 2.0f,
                FlappyDemo.HEIGHT / 2.0f);
        bg = new Texture("bg.png");
        playbtn = new Texture("playbtn.png");
    }

    /**
     * Handles Input.
     */
    @Override
    public void handleInput () {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    /**
     * Updates.
     * @param dt the delta time.
     */
    @Override
    public void update (float dt) {
        handleInput();
    }

    /**
     * Renders.
     * @param sb the sprinte batch.
     */
    @Override
    public void render (SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        sb.begin();
        sb.draw(bg, 0, 0);
        sb.draw(playbtn, cam.position.x - (playbtn.getWidth() / 2.0f), cam.position.y);
        sb.end();
    }

    /**
     * Disposes.
     */
    @Override
    public void dispose () {
        bg.dispose();
        playbtn.dispose();
        System.out.println("Menu State Disposed.");
    }
}
