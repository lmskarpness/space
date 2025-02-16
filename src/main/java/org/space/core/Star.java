package org.space.core;

import org.space.physics.OrbitalData;

public class Star extends OrbitalObject {

    public Star(String name, OrbitalData orbitalData) {
        super(name, orbitalData);
    }

    @Override
    public void updatePosition(double dt) {
//
//        if (orbitingObjects == null) {
//            return;
//        }
//
////        // Only update orbiting objects
////        for (OrbitalObject object : orbitingObjects) {
////            object.updatePosition(dt);
////        }
    }
}
