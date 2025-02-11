package org.space.core;

import org.space.physics.Vec2;

public abstract class CelestialBody {
    protected String name;
    protected double mass;
    protected double radius;
     protected Vec2 position;

    public CelestialBody(String name, double mass, double radius, Vec2 position) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public Vec2 getPosition() {
        return position;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "CelestialBody{" +
                "name='" + name + '\'' +
                ", mass=" + mass +
                ", radius=" + radius +
                ", position=" + position +
                '}';
    }
}
