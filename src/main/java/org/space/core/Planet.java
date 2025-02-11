package org.space.core;

import org.space.physics.Vec2;

public class Planet extends CelestialBody {
    private String planetType;

    public Planet(String name, double mass, double radius, Vec2 position, String planetType) {
        super(name, mass, radius, position);
        this.planetType = planetType;
    }

    public String getPlanetType() { return planetType; }


    @Override
    public String getType() {
        return "Planet [" + planetType + "]";
    }
}
