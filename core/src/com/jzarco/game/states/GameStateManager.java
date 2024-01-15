package com.jzarco.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Class to represent a GameStateManager, which is capable of
 * managing different game states efficiently. e.g. put it on
 * top, pause state, pop up ahead, ...
 */
public class GameStateManager {

    /* It works with a stack. */
    private Stack<State> states;

    /**
     * Defines the initial state of a GameStateManager.
     */
    public GameStateManager () {
        states = new Stack<State>();
    }

    /**
     * Adds a new state to the top of the stack.
     * @param state the state to add.
     */
    public void push (State state) {
        states.push(state);
    }

    /**
     * Removes the state at the top of the stack.
     */
    public void pop () {
        states.pop().dispose();
    }

    /**
     * Removes the state and add a new one at the
     * top of the stack.
     * @param state the state to add.
     */
    public void set (State state) {
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Updates.
     * @param dt the delta time
     */
    public void update (float dt) {
        states.peek().update(dt);
    }

    /**
     * Renders.
     * @param sb the sprite batch
     */
    public void render (SpriteBatch sb) {
        states.peek().render(sb);
    }
}
