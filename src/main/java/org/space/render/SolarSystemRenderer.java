package org.space.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.space.physics.SolarSystem;

public class SolarSystemRenderer {

    private SolarSystem solarSystem;

    public SolarSystemRenderer(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

    public void update(float dt) {
        for (Renderer renderer : solarSystem.getRenderers()) {
            renderer.update(dt);
        }
    }

    public void render(SpriteBatch batch) {
        for (Renderer renderer : solarSystem.getRenderers()) {
            renderer.render(batch);
        }
    }
}
