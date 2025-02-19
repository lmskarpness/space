package org.space.physics;

public record OrbitalData(double period, double eccentricity, double semiMajorAxis, double initialAnomaly) {
    public OrbitalData(double period, double eccentricity, double semiMajorAxis) {
        this(period, eccentricity, semiMajorAxis, 1);
    }
}