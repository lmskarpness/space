package org.space.core;

import com.badlogic.gdx.graphics.Texture;
import org.space.physics.OrbitalData;
import org.space.physics.SolarSystem;
import org.space.render.OrbitalRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SolarSystemGenerator {

    private Random rand;
    private List<OrbitalRenderer> orbitalRenderers;
    private List<OrbitalObject> bodies;
    private final Texture defaultWorldTexture;
    private final Texture defaultMoonTexture;
    private final Texture defaultStarTexture;
    private final Texture dot;

    public SolarSystemGenerator(int seed) {
        this.rand = new Random(seed);
        defaultWorldTexture = new Texture("src/main/assets/anim_wetplanet80.png");
        defaultMoonTexture = new Texture("src/main/assets/anim_moon80.png");
        defaultStarTexture = new Texture("src/main/assets/anim_redstar80.png");
        dot = new Texture("src/main/assets/spr_dot16.png");
    }

    public SolarSystem generateSystem() {
        orbitalRenderers = new ArrayList<>();
        bodies = new ArrayList<>();

        Star star = generateStar();
        generatePlanets(star);
        return new SolarSystem(star, orbitalRenderers);
    }

    private Star generateStar() {
        Star star = new Star("Unnamed", new OrbitalData(0,0,0), null);
        orbitalRenderers.add(new OrbitalRenderer(star, defaultStarTexture, 80, 12, 1.75f));
        return star;
    }

    private void generatePlanets(Star star) {
        // 1-9 Planets per solar system
        // Planets have longer orbital periods the further out they go, scale of 1.6 to 2.5 previous distance
        float period = rand.nextFloat(180,365); // base period
        float sma = 1000; // base semi-major axis

        int numPlanets = rand.nextInt(1,10);
        for (int i = 0; i < numPlanets; i++) {
            float eccentricity = rand.nextFloat(0.01f, 0.3f);

            Planet planet = new Planet("Planet " + i, new OrbitalData(period, eccentricity, sma, rand.nextDouble(0, 2 * Math.PI)), star);
            generateMoons(planet);
            generateSatellites(planet);

            orbitalRenderers.add(new OrbitalRenderer(planet, defaultWorldTexture, 80, 12, 0.1f));
            bodies.add(planet);
            star.addSatellite(planet);

            period *= rand.nextFloat(1.6f, 2.5f);
            sma *= 1.4f;
        }
    }

    private void generateMoons(OrbitalObject planet) {
        // 0-4 Moons per planets with spriteRadius >= 4
        int numMoons = rand.nextInt(0,5);

        for (int i = 0; i < numMoons; i++) {
            Moon moon = new Moon(
                    "Moon " + i + " of " + planet.getName(),
                    new OrbitalData(
                            rand.nextFloat(14,60),
                            rand.nextFloat(0.005f, 0.01f),
                            rand.nextFloat(30, 100)),
                    planet);
            orbitalRenderers.add(new OrbitalRenderer(moon, defaultMoonTexture, 80, 12, 0.04f));
            planet.addMoon(moon);
        }
    }

    private void generateSatellites(OrbitalObject parent) {
        Satellite satellite = new Satellite(parent);
        orbitalRenderers.add(new OrbitalRenderer(satellite, dot, 1, 1, 0.015f));
        parent.addSatellite(satellite);
    }

    public List<OrbitalRenderer> getRenderers() {
        return orbitalRenderers;
    }
}
