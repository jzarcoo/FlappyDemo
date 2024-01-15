package com.jzarco.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Class to represent a Tube. A Tube has a top Tube, a bottom Tube,
 * a position of the top Tube, a position of the bottom Tube, a
 * invisible rectangle for the top Tube and a invisible rectangle
 * for the bottom tube.
 */
public class Tube {

    /* Width of a Tube. */
    public static final int TUBE_WIDTH = 52;

    /* Random position generator. */
    private static final Random rand;

    /* Build an Obstacle. */
    /* Range for Tube position. */
    private static final int FLUCTUATION = 130;
    /* Differences between the openings in the Tube. */
    private static final int TUBE_GAP = 100;
    /* Lowest opening for the top of the bottom Tube
    (can't be below the area of the screen). */
    private static final int LOWEST_OPENING = 120;

    /* Collision Detection. */
    /* Invisible rectangle for topTube. */
    private final Rectangle boundsTop;
    /* Invisible rectangle for bottomTube. */
    private final Rectangle boundsBot;

    /* Top tube. */
    private final Texture topTube;
    /* Bottom tube. */
    private final Texture bottomTube;
    /* Position of the top Tube. */
    private final Vector2 posTopTube;
    /* Position of the bottom Tube. */
    private final Vector2 posBotTube;

    /**
     * Defines the initial state of a Tube.
     * @param x the coordinate on the x-axis where the Tube begins.
     */
    public Tube (float x) {
        // TODO Reuse static textures
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");

        posTopTube = new Vector2(x, genYTopTube());
        posBotTube = new Vector2(x, genYBotTube());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    /**
     * Generates the y coordinate of the top Tube.
     * @return the y coordinate of the top Tube.
     */
    private Integer genYTopTube () {
        return rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING;
    }

    /**
     * Generates the y coordinate of the bottom Tube.
     * @return the y coordinate of the bottom Tube.
     */
    private Float genYBotTube () {
        return posTopTube.y - TUBE_GAP - bottomTube.getHeight();
    }

    /**
     * Returns the texture of the top Tube.
     * @return the texture of the top Tube.
     */
    public Texture getTopTubeTexture() {
        return topTube;
    }

    /**
     * Returns the texture of the bottom Tube.
     * @return the texture of the bottom Tube.
     */
    public Texture getBottomTubeTexture() {
        return bottomTube;
    }

    /**
     * Returns the position of the top Tube.
     * @return the position of the top Tube.
     */
    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    /**
     * Returns the position of the bottom Tube.
     * @return the position of the bottom Tube.
     */
    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    /**
     * Repositions a Tube when it is off to the left of the screen.
     * @param x the new coordinate on the x-axis of the Tube.
     */
    public void reposition (float x) {
        posTopTube.set(x, genYTopTube());
        posBotTube.set(x, genYBotTube());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    /**
     * Check if the player collides with an obstacle.
     * @param player the player.
     * @return <code>true</code> if  the player hits an obstacle,
     *          <code>false</code> otherwise.
     */
    public boolean collides (Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    /**
     * Disposes.
     */
    public void dispose() {
        topTube.dispose();
        bottomTube.dispose();
    }

    /* Initialize the random position generator. */
    static {
        rand = new Random();
    }
}
