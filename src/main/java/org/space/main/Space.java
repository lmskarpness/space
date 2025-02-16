package org.space.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.space.main.screens.MenuScreen;

public class Space extends Game {
    public SpriteBatch batch;

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this)); // start on main menu
    }

    @Override
    public void render() {
        super.render(); // active screen handles rendering
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

