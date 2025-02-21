package org.space.physics;

import org.space.core.OrbitalObject;
import org.space.render.OrbitalRenderer;

import java.util.List;

public class SolarSystem {

    private String name;
    private OrbitalObject star;
    private List<OrbitalObject> objects;
    private List<OrbitalRenderer> orbitalRenderers;

    public SolarSystem(OrbitalObject star, List<OrbitalRenderer> orbitalRenderers) {
        this.star = star;
        this.orbitalRenderers = orbitalRenderers;
        this.name = "Unnamed";
    }

    public SolarSystem(OrbitalObject star, List<OrbitalRenderer> orbitalRenderers, String name) {
        this(star, orbitalRenderers);
        this.name = name;
    }

    public List<OrbitalRenderer> getRenderers() {
        return orbitalRenderers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrbitalObject getStar() {
        return star;
    }

    public void addOrbitalObject(OrbitalObject object) {
        objects.add(object);
    }

    public void updateSystem(double dt) {
        for (OrbitalObject orb : star.getAllOrbitingObjects()) {
            orb.updatePosition(dt);
        }
    }
}
