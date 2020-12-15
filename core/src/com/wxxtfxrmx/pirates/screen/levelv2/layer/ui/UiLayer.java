package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wxxtfxrmx.pirates.navigation.Destination;
import com.wxxtfxrmx.pirates.navigation.Navigator;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.Layer;
import com.wxxtfxrmx.pirates.screen.levelv2.UnstoppableSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.component.SlotMachineMatchedComponent;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system.HandleCoinsCountSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system.HandlePlayerLoseSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system.RenderRemainingTimeSystem;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.world.UiWorld;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;
import com.wxxtfxrmx.pirates.uikit.UiImageLabel;
import com.wxxtfxrmx.pirates.uikit.UiLabel;
import com.wxxtfxrmx.pirates.uikit.dialog.GameOverDialog;
import com.wxxtfxrmx.pirates.uikit.dialog.PauseDialog;
import com.wxxtfxrmx.pirates.uikit.dialog.UiDialog;
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
    private final UiWorld uiWorld;

    private RenderRemainingTimeSystem renderRemainingTimeSystem;
    private HandlePlayerLoseSystem handlePlayerLoseSystem;
    private HandleCoinsCountSystem handleCoinsCountSystem;

    public UiLayer(Stage stage, Navigator navigator, PooledEngine engine) {
        this.stage = stage;
        this.navigator = navigator;
        this.engine = engine;
        actors = new ArrayList<Actor>();
        pauseDialog = new PauseDialog();
        gameOverDialog = new GameOverDialog();
        uiWorld = new UiWorld(engine);
    }

    @Override
    public void create() {
        uiWorld.create();

        configListeners();

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
        handleCoinsCountSystem = new HandleCoinsCountSystem(playerCoinsLabel, aiCoinsLabel, slotMachine, stage);
        engine.addSystem(renderRemainingTimeSystem);
        engine.addSystem(handlePlayerLoseSystem);
        engine.addSystem(handleCoinsCountSystem);
    }

    private void configListeners() {
        UiDialog.OnDialogHideListener hidePauseDialogListener = new UiDialog.OnDialogHideListener() {
            @Override
            public void onHide() {
                resumeGameSystems();
            }
        };

        UiDialog.OnDialogShowListener showListener = new UiDialog.OnDialogShowListener() {
            @Override
            public void onShow() {
                pauseGameSystems();
            }
        };

        UiDialog.OnDialogHideListener hideGameOverListener = new UiDialog.OnDialogHideListener() {
            @Override
            public void onHide() {
                openStartScreen();
            }
        };

        pauseDialog.setHideListener(hidePauseDialogListener);
        pauseDialog.setShowListener(showListener);


        gameOverDialog.setHideListener(hideGameOverListener);
        gameOverDialog.setShowListener(showListener);
    }

    private void prepareSlotMachine() {
        slotMachine.setListener(new UiSlotMachine.OnSpinCompleteListener() {
            @Override
            public void onMatchSuccess(TileType matchedType) {
                SlotMachineMatchedComponent slotMachineMatchedComponent = engine.createComponent(SlotMachineMatchedComponent.class);
                slotMachineMatchedComponent.tileType = matchedType;
                slotMachineMatchedComponent.count = 3;

                Entity entity = engine.createEntity();
                entity.add(slotMachineMatchedComponent);

                engine.addEntity(entity);

                slotMachine.hide();
            }

            @Override
            public void onMatchFailure() {
                slotMachine.hide();
            }
        });
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

        UiClickListener.OnClickListener listener = new UiClickListener.OnClickListener() {
            @Override
            public void onClick() {
                openPauseDialog();
            }
        };

        pause.addListener(new UiClickListener(listener));

        return pause;
    }

    private void openPauseDialog() {
        pauseDialog.show(stage);
    }

    private void openStartScreen() {
        navigator.open(Destination.START);
    }

    private void pauseGameSystems() {
        for (EntitySystem system : engine.getSystems()) {
            if (system instanceof UnstoppableSystem) {
                continue;
            }
            system.setProcessing(false);
        }
    }

    private void resumeGameSystems() {
        for (EntitySystem system : engine.getSystems()) {
            system.setProcessing(true);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        renderRemainingTimeSystem.setProcessing(enabled);
        handlePlayerLoseSystem.setProcessing(enabled);
        handleCoinsCountSystem.setProcessing(enabled);
        if (enabled) {
            addActors();
        } else {
            removeActors();
        }
    }

    private void addActors() {
        for (Actor actor : actors) {
            stage.addActor(actor);
        }
    }

    private void removeActors() {
        for (Actor actor : actors) {
            actor.remove();
        }
    }
}
