package org.space.main;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Launcher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // Get display resolution for full-screen mode
//        Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
//        config.setFullscreenMode(displayMode); // Fullscreen by default

        config.setTitle("Space Explorer");
        config.setResizable(true);
        config.setWindowedMode(1280, 720);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new Space(), config);
    }

}
