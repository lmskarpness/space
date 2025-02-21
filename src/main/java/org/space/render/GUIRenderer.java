package org.space.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import org.space.cameras.GameCamera;
import org.space.core.OrbitalObject;
import org.space.main.Space;

public class GUIRenderer {
    private Stage stage;
    private Table table;
    private Skin skin;
    private Label name;
    private Label position;
    private OrbitalObject object;

    public GUIRenderer() {
        Batch batch = new SpriteBatch();
        object = null;
        stage = new Stage(new ExtendViewport(Space.SCREEN_WIDTH, Space.SCREEN_HEIGHT), batch);
        skin = new Skin(Gdx.files.internal("src/main/assets/gui_skins/Commodore_64_UI_Skin/commodore64ui/uiskin.json"));

        // table for UI layout
        table = new Table(skin);
        table.top().left();
        // UI Elements
        name = new Label("", skin);
        position = new Label("", skin);
        table.add(name).pad(10);
        table.row();
        table.add(position).pad(10);

        table.setPosition(0, stage.getHeight() - table.getHeight());
        table.row();

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

//    // Method to update the resource label
//    public void updateResources(int amount) {
//        resourceLabel.setText("Resources: " + amount);
//    }

    public void render() {
        if (object != null) {
            position.setText(object.getPosition().toString());
        }

        table.setPosition(0, stage.getHeight() - table.getHeight());
        stage.act(Gdx.graphics.getDeltaTime()); // Update UI
        stage.draw(); // Draw UI on top of the game
    }

    public void displayInfoHUD(OrbitalObject obj) {
        name.setText(obj.getName());
        position.setText(obj.getPosition().toString());
    }

    public void hideInfoHUD() {
        object = null;
        name.setText("");
        position.setText("");
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

    public void setFocusedObject(OrbitalObject obj) {
        this.object = obj;
        displayInfoHUD(object);
    }
}

