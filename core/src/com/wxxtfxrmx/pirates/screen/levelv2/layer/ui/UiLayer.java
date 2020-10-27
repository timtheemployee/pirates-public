package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.Layer;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.system.RenderRemainingTimeSystem;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiClickListener;
import com.wxxtfxrmx.pirates.uikit.UiLabel;
import com.wxxtfxrmx.pirates.uikit.dialog.PauseDialog;
import com.wxxtfxrmx.pirates.uikit.dialog.UiDialogSkin;

import java.util.ArrayList;
import java.util.List;

public class UiLayer implements Layer {

    private final Stage stage;
    private final List<Actor> actors;
    private final UiDialogSkin dialogSkin = new UiDialogSkin();
    private final PooledEngine engine;
    private RenderRemainingTimeSystem renderRemainingTimeSystem;

    public UiLayer(Stage stage, PooledEngine engine) {
        this.stage = stage;
        this.engine = engine;
        actors = new ArrayList<>();
        dialogSkin.addRegions(new TextureAtlas(Gdx.files.internal("ui/icon/icon-pack.atlas")));
    }

    @Override
    public void create() {
        UiButton pause = getPauseButton();
        UiLabel label = getTimeLabel();

        stage.addActor(pause);
        stage.addActor(label);
        actors.add(pause);
        actors.add(label);


        renderRemainingTimeSystem = new RenderRemainingTimeSystem(label);
        engine.addSystem(renderRemainingTimeSystem);
    }

    private UiLabel getTimeLabel() {
        float x = (Constants.WIDTH / 2f) * Constants.UNIT;
        float y = (Constants.HEIGHT - 1) * Constants.UNIT;
        UiLabel time = new UiLabel("09", x, y, Constants.UNIT, Constants.UNIT);
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
        PauseDialog pauseDialog = new PauseDialog(dialogSkin);
        pauseDialog.show(stage);
    }

    @Override
    public void setEnabled(boolean enabled) {
        renderRemainingTimeSystem.setProcessing(enabled);

        if (enabled) {
            actors.forEach(stage::addActor);
        } else {
            actors.forEach(Actor::remove);
        }
    }
}
