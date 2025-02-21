package org.space.core;

import org.space.physics.OrbitalData;

public class Satellite extends OrbitalObject {

    public Satellite(String name, OrbitalData orbitalData, OrbitalObject parent) {
        super(name, orbitalData, parent);
    }

    public  Satellite(OrbitalObject parent) {
        super("Unnamed", new OrbitalData(100, 0, 10), parent);
    }
}
