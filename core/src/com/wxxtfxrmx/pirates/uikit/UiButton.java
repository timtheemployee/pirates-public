package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class UiButton extends ImageButton {

    private final static String DEFAULT_STYLE = "default";
    private final TextureAtlas iconsAtlas = new TextureAtlas("ui/icon/icon-pack.atlas");

    public UiButton() {
        super(new Skin(Gdx.files.internal("ui/uikit.json")), DEFAULT_STYLE);
    }

    public UiButton(float x, float y, float width, float height) {
        super(new Skin(Gdx.files.internal("ui/uikit.json")), DEFAULT_STYLE);
        applyParameters(x, y, width, height);
    }

    //TODO Maybe rename
    public void startMargin(HorizontalMargin margin) {
        float x = getX(Align.left);
        x += margin.getValue();
        setX(x, Align.left);
    }

    //TODO Maybe rename
    public void endMargin(HorizontalMargin margin) {
        float x = getX(Align.left);
        x -= margin.getValue();
        setX(x, Align.left);
    }

    public void setIcon(Icon icon) {
        TextureAtlas.AtlasRegion iconRegion = iconsAtlas.findRegion(icon.getValue());
        getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(iconRegion, 0, 0, icon.size(), icon.size()));
    }

    private void applyParameters(float x, float y, float width, float height) {
        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);
    }
}
