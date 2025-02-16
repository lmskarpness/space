package org.space.core;

import org.space.physics.OrbitalData;
import org.space.physics.Vec2;

import java.util.ArrayList;
import java.util.List;

public abstract class OrbitalObject {
    protected String name;
    protected Vec2 position;
    protected double mass;      // For display data, not used in calculations
    private double spriteRadius;

    protected OrbitalData orbitalData;
    protected OrbitalObject parent; // For position of orbital origin
    protected List<OrbitalObject> orbitingObjects;
    private double trueAnomaly; // True anomaly (angle in the orbit)

    public OrbitalObject(String name, OrbitalData orbitalData) {
        this(name, orbitalData, 0);
    }

    public OrbitalObject(String name, OrbitalData orbitalData, double mass) {
        this.name = name;
        this.orbitalData = orbitalData;
        this.position = new Vec2(0, 0);
        this.orbitingObjects = new ArrayList<>();
        this.mass = mass;
        spriteRadius = 0;
    }

    public abstract void updatePosition(double dt);

    public void addSatellite(OrbitalObject satellite) {
        satellite.setParent(this);
        orbitingObjects.add(satellite);
    }

    public void addMoon(Moon moon) {
        addSatellite(moon);
    }

    // Get a list of all orbiting objects down the hierarchy of this object.
    public List<OrbitalObject> getAllOrbitingObjects() {
        List<OrbitalObject> allObjects = new ArrayList<>();
        collectOrbitingObjects(this, allObjects);
        return allObjects;
    }

    private void collectOrbitingObjects(OrbitalObject obj, List<OrbitalObject> list) {
        for (OrbitalObject child : obj.getOrbitingObjects()) {
            list.add(child);
            collectOrbitingObjects(child, list); // Recursive call for deeper levels
        }
    }

    public void setTrueAnomaly(double trueAnomaly) {
        this.trueAnomaly = trueAnomaly;
    }

    public void setParent(OrbitalObject parent) {
        this.parent = parent;
    }

    public void setPosition(Vec2 position) {
        this.position = position;
    }

    public void setSpriteRadius(double spriteRadius) {
        this.spriteRadius = spriteRadius;
    }

    public double getSpriteRadius() {
        return spriteRadius;
    }

    public String getName() {
        return name;
    }

    public Vec2 getPosition() {
        return position;
    }

    public OrbitalData getOrbitalData() {
        return orbitalData;
    }

    public List<OrbitalObject> getOrbitingObjects() {
        return orbitingObjects;
    }
}
