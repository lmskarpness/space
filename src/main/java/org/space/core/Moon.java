package org.space.core;

import org.space.physics.Vec2;

public class Moon extends CelestialBody {

    private Planet parentPlanet;

    public Moon(String name, double mass, double radius, Vec2 position, Planet parentPlanet) {
        super(name, mass, radius, position);
        this.parentPlanet = parentPlanet;
    }

    @Override
    public String getType() {
        return "Moon Orbits [" + parentPlanet.getName() + "]";
    }
}
