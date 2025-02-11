package org.space.factories;

import org.space.core.CelestialBody;
import org.space.core.Star;
import org.space.physics.Vec2;

public class StarFactory implements CelestialBodyFactory {

    private String starType;

    public StarFactory(String starType) {
        this.starType = starType;
    }

    @Override
    public CelestialBody createCelestialBody(String name, double mass, double radius, Vec2 position) {
        return new Star(name, mass, radius, position, starType);
    }
}
