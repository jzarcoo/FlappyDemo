package com.jzarco.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Class to represent a State. A state has a camera, a mouse and a
 * game state manager.
 *
 * Classes that extend state must implement the method {@link
 * #handleInput()}, to handle input, {@link #update(float)}, to
 * update and {@link #render(SpriteBatch)}, to render.
 */
public abstract class State {

    /* Camera to locate a position in the state. */
    protected OrthographicCamera cam;
    /* Pointer with x, y and z coordinates. */
    protected Vector3 mouse;
    /* Manage game states. */
    protected GameStateManager gsm;

    /**
     * Defines the initial state of a state.
     * @param gsm the state's game state manager.
     */
    protected State (GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    /**
     * Handles input.
     */
    protected abstract void handleInput ();

    /**
     * Updates.
     * @param dt the delta time, which is the difference between one
     *           frame rendered and the next one.
     */
    public abstract void update (float dt);

    /**
     * Renders.
     * @param sb the sprite batch, which is a container for everything
     *           that we need to render to the screen. It needs to open
     *           and close.
     */
    public abstract void render (SpriteBatch sb);

    /**
     * Disposes.
     */
    public abstract void dispose ();
}
