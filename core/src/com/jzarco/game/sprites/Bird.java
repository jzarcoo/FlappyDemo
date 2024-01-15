package com.jzarco.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Class to represent a Bird. A Bird has a position, a texture,
 * a velocity, a invisible rectangle for the bird, an
 * animation and a sound.
 */
public class Bird {

    /* Gravity of the Bird. */
    private static final int GRAVITY = -15;
    /* Horizontal movement of the Bird. */
    private static final int MOVEMENT = 100;

    /* Collision Detection. */
    /* Invisible rectangle for the Bird. */
    private final Rectangle bounds;

    /* Position of the Bird. */
    private final Vector3 position;
    /* Velocity (Direction) of the Bird. */
    private final Vector3 velocity;
    /* Texture of the Bird. */
    private final Texture texture;
    /* Animation of the Bird. */
    private final Animation birdAnimation;
    /* Sound of the Bird. */
    private final Sound flap;

    /**
     * Defines the initial state of a Bird.
     * @param x the coordinate on the x-axis of the Bird.
     * @param y the coordinate on the y-axis of the Bird.
     */
    public Bird (int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3.0f, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    /**
     * Returns the position of the Bird.
     * @return the position of the Bird.
     */
    public Vector3 getPosition() {
        return position;
    }

    /**
     * Returns the texture of the Bird.
     * @return the texture of the Bird.
     */
    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    /**
     * Returns the invisible rectangle for the Bird.
     * @return the invisible rectangle for the Bird.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Bird logic.
     * Resets the position of the Bird, doing the maths.
     * @param dt the delta time.
     */
    public void update (float dt) {
        birdAnimation.update(dt);

        if (position. y > 0) {
            // Add the gravity component to the velocity.
            velocity.add(0, GRAVITY, 0);
        }
        // Ensure that changes in position and speed are proportional to time.
        velocity.scl(dt);
        // Update the position.
        position.add(MOVEMENT * dt, velocity.y, 0);
        // Can't go down further than the bottom screen.
        if (position.y < 0) {
            position.y = 0;
        }
        // Restores the speed to its original value.
        velocity.scl(1 / dt);

        bounds.setPosition(position.x, position.y);
    }

    /**
     * Hop up the Bird in the y-axis.
     */
    public void jump () {
        velocity.y = 250;

        flap.play(0.5f);
    }

    /**
     * Disposes.
     */
    public void dispose() {
        texture.dispose();
        flap.dispose();
    }
}
