package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wxxtfxrmx.pirates.navigation.Destination;
import com.wxxtfxrmx.pirates.navigation.Navigator;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.Layer;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system.HandlePlayerLoseSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system.RenderRemainingTimeSystem;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;
import com.wxxtfxrmx.pirates.uikit.UiImageLabel;
import com.wxxtfxrmx.pirates.uikit.UiLabel;
import com.wxxtfxrmx.pirates.uikit.dialog.GameOverDialog;
import com.wxxtfxrmx.pirates.uikit.dialog.PauseDialog;
import com.wxxtfxrmx.pirates.uikit.slot.UiSlotMachine;

import java.util.ArrayList;
import java.util.List;

public class UiLayer implements Layer {

    private final Stage stage;
    private final List<Actor> actors;
    private final PauseDialog pauseDialog;
    private final GameOverDialog gameOverDialog;
    private final Navigator navigator;
    private final PooledEngine engine;
    private final UiSlotMachine slotMachine = new UiSlotMachine();

    private RenderRemainingTimeSystem renderRemainingTimeSystem;
    private HandlePlayerLoseSystem handlePlayerLoseSystem;

    public UiLayer(Stage stage, Navigator navigator, PooledEngine engine) {
        this.stage = stage;
        this.navigator = navigator;
        this.engine = engine;
        actors = new ArrayList<>();
        pauseDialog = new PauseDialog();
        gameOverDialog = new GameOverDialog();
    }

    @Override
    public void create() {
        pauseDialog.setHideListener(this::resumeGameSystems);
        pauseDialog.setShowListener(this::pauseGameSystems);

        gameOverDialog.setHideListener(this::openStartScreen);
        gameOverDialog.setShowListener(this::pauseGameSystems);

        UiButton pause = getPauseButton();
        UiLabel label = getTimeLabel();
        UiImageLabel aiCoinsLabel = getCoinsLabel(false);
        UiImageLabel playerCoinsLabel = getCoinsLabel(true);
        prepareSlotMachine();

        stage.addActor(pause);
        stage.addActor(label);
        stage.addActor(aiCoinsLabel);
        stage.addActor(playerCoinsLabel);

        actors.add(pause);
        actors.add(label);
        actors.add(aiCoinsLabel);
        actors.add(playerCoinsLabel);

        renderRemainingTimeSystem = new RenderRemainingTimeSystem(label);
        handlePlayerLoseSystem = new HandlePlayerLoseSystem(gameOverDialog, stage);
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

    private UiImageLabel getCoinsLabel(Boolean forPlayer) {
        float y = (Constants.HEIGHT - 2) * Constants.UNIT;
        float x = forPlayer ? Constants.WIDTH * Constants.UNIT : 0;

        UiImageLabel label = new UiImageLabel();
        label.setPosition(x, y);
        label.setText("0");
        label.setFlip(forPlayer);
        label.setDrawable(TileType.COIN);

        return label;
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

    private void openStartScreen() {
        navigator.open(Destination.START);
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
