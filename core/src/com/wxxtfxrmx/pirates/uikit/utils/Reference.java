package com.wxxtfxrmx.pirates.uikit.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Reference {
    private final TextureRegion texture;
    private final Rectangle bounds;

    private final List<Constraint> constraints;

    public Reference(TextureRegion texture, float x, float y) {
        this.texture = texture;
        bounds = new Rectangle();
        bounds.setX(x);
        bounds.setY(y);
        bounds.setWidth(texture.getRegionWidth());
        bounds.setHeight(texture.getRegionHeight());
        constraints = new ArrayList<Constraint>();
    }

    public void translateX(float x) {
        bounds.setX(bounds.x + x);
        updateConstraints();
    }

    public void translateY(float y) {
        bounds.setY(bounds.y + y);
        updateConstraints();
    }

    private void updateConstraints() {
        for (Constraint constraint: constraints) {
            constraint.update();
        }
    }

    public void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
