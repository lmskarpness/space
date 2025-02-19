package org.space.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.space.physics.SolarSystem;

public class SolarSystemRenderer {

    private SolarSystem solarSystem;

    public SolarSystemRenderer(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

    public void update(float dt) {
        for (OrbitalRenderer orbitalRenderer : solarSystem.getRenderers()) {
            orbitalRenderer.update(dt);
        }
    }

    public void render(SpriteBatch batch) {
        for (OrbitalRenderer orbitalRenderer : solarSystem.getRenderers()) {
            orbitalRenderer.render(batch);
        }
    }
}
