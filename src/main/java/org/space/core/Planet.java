package org.space.core;

import org.space.physics.OrbitalData;
import org.space.physics.OrbitalPhysics;

public class Planet extends OrbitalObject {
    private OrbitalPhysics orbitalPhysics;

    public Planet(String name, OrbitalData orbitalData, OrbitalObject parent) {
        super(name, orbitalData);
        this.parent = parent;
        orbitalPhysics = new OrbitalPhysics(parent);
    }

    @Override
    public void updatePosition(double dt) {
        // Update self and orbiting objects
        orbitalPhysics.updateObjectPosition(dt, orbitalData, this);

        if (orbitingObjects == null) {
            return;
        }

        for (OrbitalObject object : orbitingObjects) {
            object.updatePosition(dt);
        }
    }
}
