package com.jzarco.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Class to represent an Animation. An Animation has frames,
 * a maxFrameTime, a currentFrameTime, a frameCount and a frame.
 */
public class Animation {

    /* Stores all frames of the Animation. */
    private final Array<TextureRegion> frames;
    /* Time that a frame must remain in view. */
    private final float maxFrameTime;
    /* Time that the Animation has been on the current frame. */
    private float currentFrameTime;
    /* Number of frames in the Animation. */
    private final int frameCount;
    /* Current frame of the Animation. */
    private int frame;

    /**
     * Defines the initial state of an Animation.
     * @param region all of the frames combined into one image.
     * @param frameCount the number of frames in the image.
     * @param cycleTime the time to cycle through the entire animation.
     */
    public Animation (TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    /**
     * Returns the frame where the Animation is currently located.
     * @return the frame where the Animation is currently located.
     */
    public TextureRegion getFrame () {
        return frames.get(frame);
    }

    /**
     * Updates.
     * @param dt the delta time, change in time between render cycles.
     */
    public void update (float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount) {
            frame = 0;
        }
    }

}
