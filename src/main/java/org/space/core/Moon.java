package org.space.core;

import org.space.physics.OrbitalData;
import org.space.physics.OrbitalPhysics;

public class Moon extends OrbitalObject {
    private OrbitalPhysics orbitalPhysics;

    public Moon(String name, OrbitalData orbitalData, OrbitalObject parent) {
        super(name, orbitalData);
        this.parent = parent;
        orbitalPhysics = new OrbitalPhysics(parent);
    }

    @Override
    public void updatePosition(double dt) {
        // Only update self
        orbitalPhysics.updateObjectPosition(dt, orbitalData, this);
    }

}
