package org.space.cameras;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import org.space.core.OrbitalObject;
import org.space.physics.SolarSystem;
import org.space.render.GUIRenderer;

import java.util.ArrayList;
import java.util.List;

// GameCamera extends gdx.InputAdaptor, which handles screen input.
// Overwriting methods is used for customizing input handling.
public class GameCamera extends InputAdapter {
    private OrthographicCamera camera;
    private Vector3 lastTouch = new Vector3();
    private List<OrbitalObject> objects;
    private OrbitalObject focusedObject;
    private GUIRenderer renderer;

    public GameCamera(float viewportWidth, float viewportHeight, GUIRenderer renderer) {
        camera = new OrthographicCamera(viewportWidth, viewportHeight);
        camera.position.set(0, 0, 0);
        camera.update();
        this.renderer = renderer;
        objects = new ArrayList<>();
        focusedObject = null;
    }

    public void addVisibleObject(OrbitalObject object) {
        objects.add(object);
    }

    // Needs visible objects to interact with
    public void addSolarSystem(SolarSystem solarSystem) {
        objects.add(solarSystem.getStar());
        for (OrbitalObject object : solarSystem.getStar().getOrbitingObjects()) {
            recursiveAdd(object);
        }
    }

    private void recursiveAdd(OrbitalObject object) {
        objects.add(object);
        if (object.getOrbitingObjects() == null) return;

        for (OrbitalObject child : object.getOrbitingObjects()) {
            recursiveAdd(child);
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        focusedObject = null;
        renderer.hideInfoHUD();
        lastTouch.set(screenX, screenY, 0);

        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
        handleClick(worldCoords.x, worldCoords.y);

        return true;
    }

    private void handleClick(float x, float y) {
        if (objects == null || objects.isEmpty()) {
            System.out.println(this.getClass() + " - checkClick(): No objects in camera's selection array.");
            return;
        }
        for (OrbitalObject obj : objects) {
            if (isOrbitalObjectClicked(obj, x, y)) {
                System.out.println("Clicked on: " + obj.getName());
                setFocusedObject(obj);
                renderer.setFocusedObject(focusedObject);
                updateFocus();
                break;
            }
        }
    }

    private boolean isOrbitalObjectClicked(OrbitalObject obj, float x, float y) {
        float objX = obj.getPosition().getX();
        float objY = obj.getPosition().getY();
        float radius = (float) obj.getSpriteRadius();
        float distance = (float) Math.sqrt(Math.pow(x - objX, 2) + Math.pow(y - objY, 2));

        return distance <= radius;
    }

    private void setFocusedObject(OrbitalObject orb) {
        camera.zoom = (float) (0.008f * orb.getSpriteRadius()); // Initial zoom
        camera.position.set(orb.getPosition().getX(), orb.getPosition().getY(), 0);
        focusedObject = orb;
    }

    // Control events during focus mode
    public void updateFocus() {
        if (focusedObject != null) {
            camera.position.set(focusedObject.getPosition().getX(), focusedObject.getPosition().getY(), 0);
            renderer.displayInfoHUD(focusedObject);
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        focusedObject = null;
        Vector3 newTouch = new Vector3(screenX, screenY, 0);
        camera.unproject(newTouch);
        Vector3 lastWorld = camera.unproject(lastTouch);

        float zoomScale = camera.zoom;

        move((lastWorld.x - newTouch.x) / zoomScale, (lastWorld.y - newTouch.y) / zoomScale);
        lastTouch.set(screenX, screenY, 0);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        float zoomScale = amountY > 0 ? 1.1f : 0.9f;
        zoom(zoomScale);
        return true;
    }

    public void move(float dx, float dy) {
        // Multiplying the camera's zoom level equalizes the panning distance across zoom levels.
        camera.position.add(dx * camera.zoom, dy * camera.zoom, 0);
        camera.update();
    }

    public void zoom(float factor) {
        camera.zoom *= factor;
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 10f);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
