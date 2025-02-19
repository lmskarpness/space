package org.space.physics;

import com.badlogic.gdx.math.Vector2;
import org.space.core.OrbitalObject;

import java.util.List;

public class OrbitalPhysics {
    private OrbitalObject center;
    // convert to map:
    private List<OrbitalObject> orbiters;
    private double meanAnomaly; // Tracks time in orbit
    private Vec2 pos;

    public OrbitalPhysics(OrbitalObject center) {
        this.center = center;
        this.orbiters = center.getOrbitingObjects();
        this.meanAnomaly = 0;
        pos = new Vec2(0, 0);
    }

    // Update the planet's position based on Kepler's laws
    // 'orb' is the object orbiting around 'center'
    public void updateObjectPosition(double deltaTime, OrbitalData od, OrbitalObject orb) {

        double meanMotion = 2 * Math.PI / od.period(); // Mean motion n = 2π / T
        meanAnomaly += meanMotion * deltaTime;

        // Solve Kepler's equation for eccentric anomaly E
        double eccentricAnomaly = solveKeplersEquation(meanAnomaly +  + od.initialAnomaly(), od.eccentricity());

        // Compute true anomaly
        double trueAnomaly = 2 * Math.atan2(
                Math.sqrt(1 + od.eccentricity()) * Math.sin(eccentricAnomaly / 2),
                Math.sqrt(1 - od.eccentricity()) * Math.cos(eccentricAnomaly / 2)
        );

        // Compute orbital radius
        double r = od.semiMajorAxis() * (1 - Math.pow(od.eccentricity(), 2)) /
                (1 + od.eccentricity() * Math.cos(trueAnomaly));

        // Convert to Cartesian coordinates
        double x = r * Math.cos(trueAnomaly);
        double y = r * Math.sin(trueAnomaly);

        // Update planet's position
        pos.setX((float) x + center.getPosition().getX());
        pos.setY((float) y + center.getPosition().getY());
        orb.setPosition(pos);
    }

    // Solves Kepler's equation numerically using Newton's method
    // E_n+1 = E_n - f(E_n)/f'(E_n) = E_n - (E_n - esin(E_n) - M(t)) / (1 - ecos(E_n))
    // E and M are units of radians
    // Orbits with e <= 0.8, initial value E_0 = M(t) = M suffices
    // Orbits with e > 0.8, uses E_0 = pi.
    private double solveKeplersEquation(double M, double e) {
        double E = (e <= 0.8) ? M : Math.PI; // Initial guess
        final double EPSILON = 1e-6; // Convergence threshold

        for (int i = 0; i < 10; i++) {
            double f = E - e * Math.sin(E) - M;
            double fPrime = 1 - e * Math.cos(E);

            double deltaE = -f / fPrime;
            E += deltaE;

            if (Math.abs(deltaE) < EPSILON) {
                break; // Converged
            }
        }
        return E;
    }
}





















