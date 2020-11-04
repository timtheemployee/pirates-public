package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.Layer;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system.HandlePlayerLoseSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system.RenderRemainingTimeSystem;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;
import com.wxxtfxrmx.pirates.uikit.UiLabel;
import com.wxxtfxrmx.pirates.uikit.dialog.PauseDialog;
import com.wxxtfxrmx.pirates.uikit.dialog.UiDialogSkin;
import com.wxxtfxrmx.pirates.uikit.slot.UiSlotMachine;

import java.util.ArrayList;
import java.util.List;

public class UiLayer implements Layer {

    private final Stage stage;
    private final List<Actor> actors;
    private final PauseDialog pauseDialog;
    private final PooledEngine engine;
    private final UiSlotMachine slotMachine = new UiSlotMachine();

    private RenderRemainingTimeSystem renderRemainingTimeSystem;
    private HandlePlayerLoseSystem handlePlayerLoseSystem;

    public UiLayer(Stage stage, PooledEngine engine) {
        this.stage = stage;
        this.engine = engine;
        actors = new ArrayList<>();
        UiDialogSkin dialogSkin = new UiDialogSkin();
        dialogSkin.addRegions(new TextureAtlas(Gdx.files.internal("ui/icon/icon-pack.atlas")));
        pauseDialog = new PauseDialog(dialogSkin);
    }

    @Override
    public void create() {
        pauseDialog.setOnHideListener(this::resumeGameSystems);
        pauseDialog.setOnShowListener(this::pauseGameSystems);

        UiButton pause = getPauseButton();
        UiLabel label = getTimeLabel();
        prepareSlotMachine();

        stage.addActor(pause);
        stage.addActor(label);

        actors.add(pause);
        actors.add(label);

        renderRemainingTimeSystem = new RenderRemainingTimeSystem(label);
        handlePlayerLoseSystem = new HandlePlayerLoseSystem(pauseDialog, stage);
        engine.addSystem(renderRemainingTimeSystem);
        engine.addSystem(handlePlayerLoseSystem);
    }

    private void prepareSlotMachine() {
        float x = (Constants.WIDTH / 2f) * Constants.UNIT;
        float y = (Constants.HEIGHT - 3) * Constants.UNIT;
        slotMachine.setPosition(x, y);
    }

    private UiLabel getTimeLabel() {
        float x = (Constants.WIDTH / 2f) * Constants.UNIT;
        float y = (Constants.HEIGHT - 1) * Constants.UNIT;
        UiLabel time = new UiLabel("10", x, y, Constants.UNIT, Constants.UNIT);
        time.endMargin(HorizontalMargin.MEDIUM);

        return time;
    }

    private UiButton getPauseButton() {
        float x = (Constants.WIDTH - 1) * Constants.UNIT;
        float y = (Constants.HEIGHT - 1) * Constants.UNIT;
        UiButton pause = new UiButton(x, y, Constants.UNIT, Constants.UNIT);
        pause.endMargin(HorizontalMargin.TINY);
        pause.setIcon(Icon.PAUSE);
        pause.addListener(new UiClickListener(this::openPauseDialog));

        return pause;
    }

    private void openPauseDialog() {
        pauseDialog.show(stage);
    }

    private void pauseGameSystems() {
        engine.getSystems().forEach(system -> system.setProcessing(false));
    }

    private void resumeGameSystems() {
        engine.getSystems().forEach(systems -> systems.setProcessing(true));
    }

    @Override
    public void setEnabled(boolean enabled) {
        renderRemainingTimeSystem.setProcessing(enabled);
        handlePlayerLoseSystem.setProcessing(enabled);

        if (enabled) {
            actors.forEach(stage::addActor);
        } else {
            actors.forEach(Actor::remove);
        }
    }
}
