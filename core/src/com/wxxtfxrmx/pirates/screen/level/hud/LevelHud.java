package com.wxxtfxrmx.pirates.screen.level.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wxxtfxrmx.pirates.uikit.TimerUiLabel;
import com.wxxtfxrmx.pirates.uikit.UiButton;

public class LevelHud extends Group {

    private final static int BUTTON_SIZE = 56;
    private final static int MARGIN_SIZE = 8;

    private final TimerUiLabel timerLabel;
    private final UiButton menuButton;

    public LevelHud() {
        super();
        timerLabel = new TimerUiLabel("00");
        menuButton = new UiButton();
        menuButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
        addActor(timerLabel);
        addActor(menuButton);
        setDebug(true, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        timerLabel.draw(batch, parentAlpha);
        menuButton.draw(batch, parentAlpha);
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        stage.addActor(menuButton);
        stage.addActor(timerLabel);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        timerLabel.act(delta);
        menuButton.act(delta);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, y, width, height);
        timerLabel.setPosition((x + width) * 0.5f - timerLabel.getWidth() * 0.5f, y);
        menuButton.setPosition(x + width - menuButton.getWidth() - MARGIN_SIZE, y + MARGIN_SIZE * 0.5f);
    }
}
