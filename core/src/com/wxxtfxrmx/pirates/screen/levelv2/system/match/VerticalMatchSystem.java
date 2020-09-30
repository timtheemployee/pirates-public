package com.wxxtfxrmx.pirates.screen.levelv2.system.match;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;

public class VerticalMatchSystem extends MatchSystem {

    @Override
    Array<Entity> findNeighbors(float x, float y, Iterable<Entity> entities) {
        float top = y + Constants.UNIT;
        float bottom = y - Constants.UNIT;
        Array<Entity> neighbors = new Array<>();

        for (Entity entity : entities) {
            if (isNeighbor(x, top, bottom, entity)) {
                neighbors.add(entity);
            }
        }

        return neighbors;
    }

    private boolean isNeighbor(float x, float topY, float bottomY, Entity entity) {
        Rectangle bounds = this.bounds.get(entity).bounds;

        if (bounds.x != x) return false;

        return bounds.y == topY || bounds.y == bottomY;
    }

    @Override
    boolean isOnBorder(BoundsComponent component) {
        return isOnTop(component.bounds.y) || isOnBottom(component.bounds.y);
    }

    private boolean isOnTop(float y) {
        return y == (Constants.MIDDLE_ROUNDED_HEIGHT - 1) * Constants.UNIT;
    }

    private boolean isOnBottom(float y) {
        return y == 0;
    }
}
