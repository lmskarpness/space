package org.space.core;

import org.space.physics.OrbitalData;

public class Planet extends OrbitalObject {

    public Planet(String name, OrbitalData orbitalData, OrbitalObject parent) {
        super(name, orbitalData, parent);
    }
}
