package org.space.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import org.space.core.OrbitalObject;
import org.space.main.Space;
import org.space.physics.SolarSystem;

import java.util.List;

public class GUIRenderer {
    private Stage stage;
    private Table table;
    private Skin skin;
    SelectBox selectBox;
    private Batch batch;
    private SolarSystem solarSystem;

    public GUIRenderer(Space space, SolarSystem solarSystem) {
        Batch batch = new SpriteBatch();
        this.solarSystem = solarSystem;
        stage = new Stage(new ExtendViewport(Space.SCREEN_WIDTH, Space.SCREEN_HEIGHT), batch);
        skin = new Skin(Gdx.files.internal("src/main/assets/gui_skins/Commodore_64_UI_Skin/commodore64ui/uiskin.json"));
        selectBox = new SelectBox<>(skin);

        // table for UI layout
        table = new Table(skin);
        table.top().left();
        // UI Elements
        selectBox.setItems(getStringsForDropdown("[PLANETS]"));

        table.setPosition(0, stage.getHeight() - table.getHeight());
        table.row();
        table.add(selectBox).pad(10).growX();

        // add table to stage
        stage.addActor(table);

        // Button Click Listener (Example Action)
//        collectButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
//                System.out.println("clicked");
//                updateResources(10); // Increase resources by 10
//            }
//        });
    }

    private String[] getStringsForDropdown(String title) {
        List<OrbitalObject> objs = solarSystem.getStar().getOrbitingObjects(); // del
        String[] ret = new String[objs.size() + 1];
        ret[0] = title;
        for (int i = 0; i < objs.size(); i++) {
            ret[i + 1] = objs.get(i).getName();
        }
        return ret;
    }

//    // Method to update the resource label
//    public void updateResources(int amount) {
//        resourceLabel.setText("Resources: " + amount);
//    }

    public void render() {
        table.setPosition(0, stage.getHeight() - table.getHeight());
        stage.act(Gdx.graphics.getDeltaTime()); // Update UI
        stage.draw(); // Draw UI on top of the game
    }

    public void updateViewport(int width, int height) {
        getStage().getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    // Handle input separately from game logic
    public Stage getStage() {
        return stage;
    }
}

