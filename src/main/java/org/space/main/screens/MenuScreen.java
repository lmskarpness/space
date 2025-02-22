package org.space.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.space.main.Space;
import org.space.render.Animation;

public class MenuScreen implements Screen {
    private Space space;
    private final SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private SelectBox selectBox;
    private Table table;
    private final BitmapFont font;
    private final Texture title;
    private final Animation animation;
    private Vector2 titleSize;

    // Uses standard camera as opposed to GameScreen, which uses a GameCamera
    private OrthographicCamera camera;
    private Viewport viewport;

    public MenuScreen(Space space) {
        this.space = space;
        batch = space.batch;
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()), batch);
        skin = new Skin(Gdx.files.internal("src/main/assets/gui_skins/Commodore_64_UI_Skin/commodore64ui/uiskin.json"));
        selectBox = new SelectBox<>(skin);

        table = new Table(skin);
        table.center();

        this.camera = new OrthographicCamera();
        viewport = new ExtendViewport(Space.SCREEN_WIDTH, Space.SCREEN_HEIGHT, camera);

        TextButton play = new TextButton("PLAY", skin);
        table.setPosition(viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2);
        table.add(play);
        stage.addActor(table);

        font = new BitmapFont();
        title = new Texture("src/main/assets/anim_kepler17.png");
        animation = new Animation(title, 17, 1);
        titleSize = new Vector2(animation.getFrameWidth(), title.getHeight());

    }

    @Override
    public void show() {
        camera.update();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime()); // Update UI
        stage.draw(); // Draw UI on top of the game

        animation.update(dt);

        draw();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {

            space.setScreen(new GameScreen(space)); // Switch to game screen
        }
    }

    private void draw() {
        GlyphLayout layout = new GlyphLayout(font, "Press ENTER to Start");

        float centerX = viewport.getWorldWidth() / 2;
        float centerY = viewport.getWorldHeight() / 2;

        batch.begin();
        batch.draw(animation.getFrame(), centerX - (titleSize.x / 2f), centerY + 50);
        font.draw(batch, layout, centerX - (layout.width / 2), centerY - (layout.height / 2));
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime()); // Update buttons
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.update();
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
        batch.dispose();
    }
}
