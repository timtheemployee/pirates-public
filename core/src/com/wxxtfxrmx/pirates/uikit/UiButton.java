package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UiButton extends ImageButton {

    private final static String DEFAULT_STYLE = "default";

    public UiButton() {
        super(new Skin(Gdx.files.internal("ui/uikit.json")), DEFAULT_STYLE);
        setSize(getStyle().up.getMinHeight(), getStyle().up.getMinHeight());
        setTouchable(Touchable.enabled);
    }
}
