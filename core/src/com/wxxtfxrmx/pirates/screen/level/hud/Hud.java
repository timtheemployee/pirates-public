package com.wxxtfxrmx.pirates.screen.level.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.wxxtfxrmx.pirates.uikit.TimerUiLabel;
import com.wxxtfxrmx.pirates.uikit.UiButton;

public final class Hud extends Group {

    private final UiButton menu;
    private final TimerUiLabel timer;

    public Hud() {
        this.menu = new UiButton();
        this.timer = new TimerUiLabel("00");

        setSize(Gdx.graphics.getWidth(), 64);
        setPosition(0, Gdx.graphics.getHeight() - 64);
        setBounds(getX(Align.left), getY(Align.bottom), getWidth(), getHeight());

        menu.setWidth(56);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        menu.setSize(menu.getWidth(), 56);
        menu.setPosition(getWidth() - menu.getWidth() - 16, getHeight());
        timer.setSize(timer.getWidth(), 56);
        timer.setPosition(getWidth() / 2 - timer.getWidth() / 2, getHeight());
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        stage.addActor(menu);
        stage.addActor(timer);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        menu.draw(batch, parentAlpha);
        timer.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        menu.act(delta);
        timer.act(delta);
    }
}
