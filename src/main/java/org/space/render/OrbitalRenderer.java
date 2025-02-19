package org.space.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.space.core.OrbitalObject;

public class OrbitalRenderer {
    private Texture texture;
    private Animation animation;
    private OrbitalObject cb;
    private float frameWidth;
    private float frameHeight;
    private float scale;

    public OrbitalRenderer(OrbitalObject cb, Texture texture, int frameCount, float cycleTime, float scale) {
        this(cb, texture, frameCount, cycleTime);
        this.scale = scale;
        cb.setSpriteRadius((Math.min(frameWidth, frameHeight) / 2) * scale);
    }

    public OrbitalRenderer(OrbitalObject cb, Texture texture, int frameCount, float cycleTime) {
        this.cb = cb;
        this.texture = texture;
        animation = new Animation(texture, frameCount, cycleTime);
        frameWidth = (float) texture.getWidth() / frameCount;
        frameHeight = (float) texture.getHeight();
        scale = 1;
    }

    public void update(float deltaTime) {
        animation.update(deltaTime);
    }

    public void render(SpriteBatch batch) {
        float x = (float) cb.getPosition().getX();
        float y = (float) cb.getPosition().getY();

        // Center the texture at (x, y)
        batch.draw(animation.getFrame(),
                x - (frameWidth / 2) * scale,   // centers the texture for x, y
                y - (frameHeight / 2) * scale,     // with scale to adjust drift
                frameWidth * scale,                // scales width, height
                frameHeight * scale);
    }

    public void dispose() {
        texture.dispose();
    }
}
