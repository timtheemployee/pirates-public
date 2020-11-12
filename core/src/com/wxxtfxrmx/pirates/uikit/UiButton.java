package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class UiButton extends ImageButton {

    public UiButton() {
        super(new DefaultSkin());
    }

    public UiButton(float x, float y, float width, float height) {
        super(new DefaultSkin());
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
        TextureRegion region = getSkin().getRegion(icon.getValue());
        getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(region, 0, 0, icon.size(), icon.size()));
    }

    private void applyParameters(float x, float y, float width, float height) {
        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);
    }
}
