package org.space.cameras;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import org.space.core.OrbitalObject;
import org.space.physics.SolarSystem;

import java.util.ArrayList;
import java.util.List;

// GameCamera extends gdx.InputAdaptor, which handles screen input.
// Overwriting methods is used for customizing input handling.
public class GameCamera extends InputAdapter {
    private OrthographicCamera camera;
    private Vector3 lastTouch = new Vector3();
    private List<OrbitalObject> objects;

    public GameCamera(float viewportWidth, float viewportHeight) {
        camera = new OrthographicCamera(viewportWidth, viewportHeight);
        camera.position.set(0, 0, 0);
        camera.update();
        objects = new ArrayList<>();
    }

    public void addVisibleObject(OrbitalObject object) {
        objects.add(object);
    }

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
        lastTouch.set(screenX, screenY, 0);

        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
        System.out.printf("worldCoords: (%f, %f)\n", worldCoords.x, worldCoords.y);
        checkClick(worldCoords.x, worldCoords.y);
        return true;
    }

    private void checkClick(float x, float y) {
        if (objects == null || objects.isEmpty()) {
            System.out.println(this.getClass() + " - checkClick(): No objects in camera's selection array.");
            return;
        }
        for (OrbitalObject obj : objects) {
            if (isClicked(obj, x, y)) {
                System.out.println("Clicked on: " + obj.getName());
                break;
            }
        }
    }

    private boolean isClicked(OrbitalObject obj, float x, float y) {
        float objX = obj.getPosition().getX();
        float objY = obj.getPosition().getY();
        float radius = (float) obj.getSpriteRadius();
        float distance = (float) Math.sqrt(Math.pow(x - objX, 2) + Math.pow(y - objY, 2));

        return distance <= radius;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
