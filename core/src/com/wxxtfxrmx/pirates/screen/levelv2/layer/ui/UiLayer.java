package com.wxxtfxrmx.pirates.screen.levelv2.layer.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.Layer;
import com.wxxtfxrmx.pirates.uikit.HorizontalMargin;
import com.wxxtfxrmx.pirates.uikit.Icon;
import com.wxxtfxrmx.pirates.uikit.UiButton;
import com.wxxtfxrmx.pirates.uikit.UiLabel;

import java.util.ArrayList;
import java.util.List;

public class UiLayer implements Layer {

    private final Stage stage;
    private final List<Actor> actors;

    public UiLayer(Stage stage) {
        this.stage = stage;
        actors = new ArrayList<>();
    }

    @Override
    public void create() {
        UiButton pause = getPauseButton();
        UiButton settings = getSettingsButton();
        UiLabel label = getTimeLabel();

        stage.addActor(pause);
        stage.addActor(label);
        stage.addActor(settings);
        actors.add(pause);
        actors.add(label);
        actors.add(settings);
    }

    private UiButton getSettingsButton() {
        float x = 0;
        float y = (Constants.HEIGHT - 1) * Constants.UNIT;
        UiButton settings = new UiButton(x, y, Constants.UNIT, Constants.UNIT);
        settings.startMargin(HorizontalMargin.TINY);
        settings.setIcon(Icon.GEAR);

        return settings;
    }
    
    private UiLabel getTimeLabel() {
        float x = (Constants.WIDTH / 2f) * Constants.UNIT;
        float y = (Constants.HEIGHT - 1) * Constants.UNIT;
        UiLabel time = new UiLabel("00", x, y, Constants.UNIT, Constants.UNIT);
        time.endMargin(HorizontalMargin.MEDIUM);

        return time;
    }

    private UiButton getPauseButton() {
        float x = (Constants.WIDTH - 1) * Constants.UNIT;
        float y = (Constants.HEIGHT - 1) * Constants.UNIT;
        UiButton pause = new UiButton(x, y, Constants.UNIT, Constants.UNIT);
        pause.endMargin(HorizontalMargin.TINY);
        pause.setIcon(Icon.PAUSE);

        return pause;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            actors.forEach(stage::addActor);
        } else {
            actors.forEach(Actor::remove);
        }
    }
}
