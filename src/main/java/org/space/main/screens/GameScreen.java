package org.space.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.space.cameras.GameCamera;
import org.space.core.SolarSystemGenerator;
import org.space.main.Space;
import org.space.physics.SolarSystem;
import org.space.render.SolarSystemRenderer;

public class GameScreen implements Screen {
    private Space space;
    private GameCamera gameCamera;
    private OrthographicCamera camera;
    private Viewport viewport;
    public float savedZoom = 1.0f;  // for transferring between pause and play states

    private SolarSystem solarSystem;
    private SolarSystemGenerator ssg;
    private SolarSystemRenderer ssr;

    public GameScreen(Space space) {
        this.space = space;

        gameCamera = new GameCamera(1280,720);
        this.camera = gameCamera.getCamera();

        camera.zoom = savedZoom;
        camera.update();
        viewport = new FitViewport(Space.SCREEN_WIDTH, Space.SCREEN_HEIGHT, camera);

        ssg = new SolarSystemGenerator(1234);
        solarSystem = ssg.generateSystem(); // Generates renderers as well
        ssr = new SolarSystemRenderer(solarSystem);

        gameCamera.addSolarSystem(solarSystem);
        Gdx.input.setInputProcessor(gameCamera);
    }

    @Override
    public void show() {
        // Restore previous zoom when resuming. This is run when a setScreen() is called.
        camera.zoom = savedZoom;
        camera.update();
        Gdx.input.setInputProcessor(gameCamera); // restore input
    }

    @Override
    public void render(float v) {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            savedZoom = camera.zoom;
            Gdx.input.setInputProcessor(null);  // disable input
            space.setScreen(new PauseScreen(space, this)); // switch to pause screen
        }

        update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear screen
        space.batch.setProjectionMatrix(camera.combined);
        draw();
    }

    private void update() {
        float dt = Gdx.graphics.getDeltaTime();

        // update camera transform for zoom/panning
        camera.update();

        // update solar system
        solarSystem.updateSystem(dt);

        // update renderers
        ssr.update(dt);
    }

    private void draw() {
        space.batch.begin();
        ssr.render(space.batch);
        space.batch.end();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
