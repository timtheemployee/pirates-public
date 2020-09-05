package com.wxxtfxrmx.pirates.screen.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.uikit.UiButton;

public class LevelHud extends Group {

    private final UiButton menu;

    public LevelHud() {
        this.menu = new UiButton();
        setSize(Gdx.graphics.getWidth(), 64);
        menu.setPosition(getWidth() - 64f, getHeight() - menu.getHeight() / 2);
    }
}
