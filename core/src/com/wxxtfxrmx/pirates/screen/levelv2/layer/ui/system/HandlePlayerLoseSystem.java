package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.component.PlayerLoseComponent;
import com.wxxtfxrmx.pirates.uikit.dialog.GameOverDialog;

public class HandlePlayerLoseSystem extends IteratingSystem {

    private final GameOverDialog dialog;
    private final Stage stage;

    public HandlePlayerLoseSystem(GameOverDialog dialog, Stage stage) {
        super(Family.all(
                PlayerLoseComponent.class
        ).get());

        this.dialog = dialog;
        this.stage = stage;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        dialog.show(stage);
        getEngine().removeEntity(entity);
    }
}
