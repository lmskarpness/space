package org.space.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.space.cameras.GameCamera;
import org.space.core.SolarSystemGenerator;
import org.space.main.Space;
import org.space.physics.SolarSystem;
import org.space.render.GUIRenderer;
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
    private GUIRenderer gui;
    InputMultiplexer multiplexer;

    public GameScreen(Space space) {
        this.space = space;

        gameCamera = new GameCamera(1280,720);
        this.camera = gameCamera.getCamera();

        camera.zoom = savedZoom;
        camera.update();
        viewport = new ExtendViewport(Space.SCREEN_WIDTH, Space.SCREEN_HEIGHT, camera);

        ssg = new SolarSystemGenerator(12033450);
        solarSystem = ssg.generateSystem(); // Generates renderers as well
        ssr = new SolarSystemRenderer(solarSystem);
        gameCamera.addSolarSystem(solarSystem);

        gui = new GUIRenderer(space, solarSystem);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gui.getStage()); // UI Input
        multiplexer.addProcessor(gameCamera);     // Game input
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void show() {
        // Restore previous zoom when resuming. This is run when a setScreen() is called.
        camera.zoom = savedZoom;
        camera.update();
        Gdx.input.setInputProcessor(multiplexer); // reset input processor from pause
    }

    @Override
    public void render(float v) {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            savedZoom = camera.zoom;
            Gdx.input.setInputProcessor(null);  // disable input
            space.setScreen(new PauseScreen(space, this)); // switch to pause screen
        }

        update();
        space.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clear screen
        draw();
    }

    private void update() {
        float dt = Gdx.graphics.getDeltaTime();

        // update camera transform for zoom/panning
        gameCamera.updateFocus();
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

        gui.render();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
        gui.updateViewport(width, height);
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
