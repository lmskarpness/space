package org.space.main;

import org.space.core.Star;
import org.space.factories.StarFactory;
import org.space.physics.Vec2;

public class Main {
    public static void main(String[] args) {
        StarFactory sun = new StarFactory("Red Star");
        Star centralStar = (Star) sun.createCelestialBody("Sol", 50000, 1000000, new Vec2(0.0f, 0.0f));
    }
}
