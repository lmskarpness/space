package org.space.physics;

import org.space.core.OrbitalObject;
import org.space.render.Renderer;

import java.util.List;

public class SolarSystem {

    private String name;
    private OrbitalObject star;
    private List<OrbitalObject> objects;
    private List<Renderer> renderers;

    public SolarSystem(OrbitalObject star, List<Renderer> renderers) {
        this.star = star;
        this.renderers = renderers;
        this.name = "Unnamed";
    }

    public SolarSystem(OrbitalObject star, List<Renderer> renderers, String name) {
        this(star, renderers);
        this.name = name;
    }

    public List<Renderer> getRenderers() {
        return renderers;
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
        star.updatePosition(dt);
        for (OrbitalObject orb : star.getAllOrbitingObjects()) {
            orb.updatePosition(dt);
        }
    }
}
