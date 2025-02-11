package org.space.core;

import org.space.physics.Vec2;

public class Star extends CelestialBody {

    private String starType;

    public Star(String name, double mass, double radius, Vec2 position, String starType) {
        super(name, mass, radius, position);
        this.starType = starType;
    }

    public String getStarType() { return starType; }

    @Override
    public String getType() {
        return "Star [" + starType + "]";
    }
}
