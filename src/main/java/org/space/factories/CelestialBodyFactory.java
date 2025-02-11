package org.space.factories;

import org.space.core.CelestialBody;
import org.space.physics.Vec2;

interface CelestialBodyFactory {
    CelestialBody createCelestialBody(String name, double mass, double radius, Vec2 position);
}
