package com.wxxtfxrmx.pirates.uikit.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Reference {
    private final TextureRegion texture;
    private final Rectangle bounds;

    public Reference(TextureRegion texture, float x, float y) {
        this.texture = texture;
        bounds = new Rectangle();
        bounds.setX(x);
        bounds.setY(y);
        bounds.setWidth(texture.getRegionWidth());
        bounds.setHeight(texture.getRegionHeight());
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
