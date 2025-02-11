package org.space.factories;

import org.space.core.CelestialBody;
import org.space.core.Planet;
import org.space.physics.Vec2;

public class PlanetFactory implements CelestialBodyFactory {

    private String planetType;

    public PlanetFactory(String planetType) {
        this.planetType = planetType;
    }

    @Override
    public CelestialBody createCelestialBody(String name, double mass, double radius, Vec2 position) {
        return new Planet(name, mass, radius, position, planetType);
    }
}
