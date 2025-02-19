package org.space.main.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.space.main.Space;

public class PauseScreen implements Screen {

    private final Space space;
    private OrthographicCamera camera;
    private GameScreen gameScreen;
    private Viewport viewport;

    private final BitmapFont font;

    public PauseScreen(Space game, GameScreen gameScreen) {
        this.space = game;
        this.gameScreen = gameScreen;

        this.camera = new OrthographicCamera();
        viewport = new ExtendViewport(Space.SCREEN_WIDTH, Space.SCREEN_HEIGHT, camera);
        camera.update();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
    }

    @Override
    public void show() {
        // Runs when setScreen(pauseScreen) is called
        camera.zoom = 1f;
        camera.position.set(Space.SCREEN_WIDTH / 2f, Space.SCREEN_HEIGHT / 2f, 0);
        camera.update();

        viewport.apply();
    }

    @Override
    public void render(float v) {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            // Engine auto-calls 'Show()' of GameScreen, which sets the savedZoom variable.
            space.setScreen(gameScreen); // Resume Game
        }
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.Q)) {
            space.setScreen(new MenuScreen(space)); // Return to Main Menu
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        draw();
    }

    private void draw() {
        camera.update();
        space.batch.setProjectionMatrix(camera.combined);

        space.batch.begin();
        GlyphLayout layout = new GlyphLayout(font, "PAUSED\nPress ESC to Resume\nPress Q to Quit");
        font.draw(space.batch, layout,
                this.camera.position.x - layout.width / 2,
                this.camera.position.y + layout.height / 2
        );
        space.batch.end();
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
