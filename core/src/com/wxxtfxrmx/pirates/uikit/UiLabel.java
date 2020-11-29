package com.wxxtfxrmx.pirates.uikit;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class UiLabel extends Label {

    private final GlyphLayout glyphLayout = new GlyphLayout();

    public UiLabel(CharSequence text) {
        super(text, new DefaultSkin());
    }

    public UiLabel(CharSequence text, float x, float y, float width, float height) {
        super(text, new DefaultSkin());
        getStyle().background = null;
        getStyle().font.getData().setScale(1.5f);
        glyphLayout.setText(getStyle().font, text);
        float optimalWidth = Math.max(glyphLayout.width, width);
        float optimalHeight = Math.max(glyphLayout.height, height);

        applyParameters(x, y, optimalWidth, optimalHeight);
    }

    private void applyParameters(float x, float y, float width, float height) {
        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);
    }

    public void startMargin(HorizontalMargin margin) {
        float x = getX(Align.left);
        x += margin.getValue();
        setX(x, Align.left);
    }

    public void endMargin(HorizontalMargin margin) {
        float x = getX(Align.left);
        x -= margin.getValue();
        setX(x, Align.left);
    }
}
