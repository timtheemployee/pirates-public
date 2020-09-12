package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UiLabel extends Label {

    private static final String DEFAULT_STYLE = "default";

    public UiLabel(CharSequence text) {
        super(text, new Skin(Gdx.files.internal("ui/uikit.json")), DEFAULT_STYLE);
    }
}
