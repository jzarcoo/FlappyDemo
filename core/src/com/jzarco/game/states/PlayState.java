package com.jzarco.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jzarco.game.FlappyDemo;
import com.jzarco.game.sprites.Bird;
import com.jzarco.game.sprites.Tube;

/**
 * Class to represent a PlayState. A PlayState has a background,
 * a ground, which has two position, a bird and an array of tubes.
 */
public class PlayState extends State {

    /* Space between tubes, including one of the Tubes themselves. */
    private static final int TUBE_SPACING = 125;
    /* Total number of Tubes. */
    private static final int TUBE_COUNT = 4;
    /* Ground y offset. */
    private static final int GROUND_Y_OFFSET = -50;

    /* Background of the PlayState. */
    private final Texture bg;
    /* Ground of the PlayState. */
    private final Texture ground;
    /* Position one of the Ground. */
    private final Vector2 groundPos1;
    /* Position two of the Ground. */
    private final Vector2 groundPos2;
    /* Bird of the PlayState. */
    private final Bird bird;
    /* Current game Tubes of the PlayState. */
    private final Array<Tube> tubes;

    /**
     * Defines the initial state of a PlayState.
     * @param gsm the game state manager.
     */
    public PlayState (GameStateManager gsm) {
        super(gsm);
        // Camera to set a viewport.
        cam.setToOrtho(false,
                FlappyDemo.WIDTH / 2.0f,
                FlappyDemo.HEIGHT / 2.0f);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(getLeftSide(), GROUND_Y_OFFSET);
        groundPos2 = new Vector2(getLeftSide() + ground.getWidth(), GROUND_Y_OFFSET);
        bird = new Bird(50, 300);
        tubes = new Array<Tube>();
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    /**
     * Returns the coordinate on the x-axis from the left side of our camera.
     * @return the coordinate on the x-axis from the left side of our camera.
     */
    private float getLeftSide () {
        return cam.position.x - cam.viewportWidth / 2;
    }

    /**
     * Updates the ground when it is off to the left of the screen.
     */
    private void updateGround () {
        if (getLeftSide() > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (getLeftSide() > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }

    /**
     * Games over.
     */
    private void gameOver () {
        gsm.set(new PlayState(gsm));
    }

    /**
     * Handles input.
     */
    @Override
    protected void handleInput () {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    /**
     * Game logic.
     * Updates.
     * @param dt the delta time.
     */
    @Override
    public void update (float dt) {
        handleInput();
        updateGround();

        // Bird.
        bird.update(dt);

        // Camera position based on where the Bird is.
        cam.position.x = bird.getPosition().x + 80; // +80 camera in front of the Bird.

        // Tubes.
        for (Tube tube : tubes) {
            // Repositions a Tube when it is off to the left of the screen.
            if (getLeftSide() > (tube.getPosTopTube().x + tube.getTopTubeTexture().getWidth())) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if (tube.collides(bird.getBounds())) {
                gameOver();
                // After collision check gsm set method uses the same
                // iterator to iterate over the same tubes to dispose
                // textures without finishing the first loop,
                // so exception is triggered.
                break;
            }
        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gameOver();
        }

        // Because the camera was repositioned.
        cam.update();
    }

    /**
     * Draws on the screen.
     * Renders.
     * @param sb the sprite batch.
     */
    @Override
    public void render (SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        // Background.
        sb.draw(bg, getLeftSide(), 0);

        // Bird.
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        // Tubes.
        for (Tube tube : tubes) {
            sb.draw(tube.getTopTubeTexture(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTubeTexture(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        // Ground.
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        sb.end();
    }

    /**
     * Disposes.
     */
    @Override
    public void dispose() {
        bg.dispose();
        ground.dispose();
        bird.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        System.out.println("Play State Disposed.");
    }
}
