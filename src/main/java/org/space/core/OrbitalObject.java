package org.space.core;

import org.space.physics.OrbitalData;
import org.space.physics.OrbitalPhysics;
import org.space.physics.Vec2;

import java.util.ArrayList;
import java.util.List;

public abstract class OrbitalObject {
    protected String name;
    protected Vec2 position;
    protected double mass;      // For display data, not used in calculations
    private double spriteRadius;

    protected OrbitalData orbitalData;
    private OrbitalPhysics orbitalPhysics;
    protected OrbitalObject parent; // For position of orbital origin
    protected List<OrbitalObject> orbitingObjects;

    public OrbitalObject(String name, OrbitalData orbitalData, OrbitalObject parent) {
        this.name = name;
        this.orbitalData = orbitalData;
        this.parent = parent;
        this.position = new Vec2(0, 0);
        this.mass = mass;
        this.orbitingObjects = new ArrayList<>();
        spriteRadius = 0;

        orbitalPhysics = (parent == null) ? null : new OrbitalPhysics(parent);
    }

    public void updatePosition(double dt) {
        // Update self and orbiting objects
        orbitalPhysics.updateObjectPosition(dt, orbitalData, this);

        if (orbitingObjects == null) return;

        for (OrbitalObject object : orbitingObjects) {
            object.updatePosition(dt);
        }
    }

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
