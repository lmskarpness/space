package org.space.factories;

import org.space.core.CelestialBody;
import org.space.core.Moon;
import org.space.core.Planet;
import org.space.physics.Vec2;

public class MoonFactory implements CelestialBodyFactory {

    private Planet parentPlanet;

    @Override
    public CelestialBody createCelestialBody(String name, double mass, double radius, Vec2 position) {
        return new Moon(name, mass, radius, position, parentPlanet);
    }
}
