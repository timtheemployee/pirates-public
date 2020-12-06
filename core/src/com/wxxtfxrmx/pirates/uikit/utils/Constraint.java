package com.wxxtfxrmx.pirates.uikit.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Constraint {
    private final Reference reference;
    private final TextureRegion texture;
    private final Rectangle bounds;
    private float verticalBias = 0;
    private float horizontalBias = 0;

    public Constraint(Reference reference, TextureRegion texture) {
        this.reference = reference;
        this.texture = texture;
        bounds = new Rectangle();
        bounds.setX(reference.getBounds().x);
        bounds.setY(reference.getBounds().y);
        bounds.setWidth(texture.getRegionWidth());
        bounds.setHeight(texture.getRegionHeight());
    }

    public void update() {
        updateBounds(verticalBias, horizontalBias);
    }

    public void setVerticalBias(float verticalBias) {
        this.verticalBias = verticalBias;
        updateBounds(verticalBias, horizontalBias);
    }

    public void setHorizontalBias(float horizontalBias) {
        this.horizontalBias = horizontalBias;
        updateBounds(verticalBias, horizontalBias);
    }

    private void updateBounds(float verticalBias, float horizontalBias) {
        bounds.setY(reference.getBounds().y + verticalBias * reference.getBounds().getHeight());
        bounds.setX(reference.getBounds().x + horizontalBias * reference.getBounds().getWidth());
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
