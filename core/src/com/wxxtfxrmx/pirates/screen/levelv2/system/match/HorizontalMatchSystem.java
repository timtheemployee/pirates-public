package com.wxxtfxrmx.pirates.screen.levelv2.system.match;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.component.BoundsComponent;

public class HorizontalMatchSystem extends MatchSystem {

    @Override
    Array<Entity> findNeighbors(float x, float y, Iterable<Entity> entities) {
        float left = x - Constants.UNIT;
        float right = x + Constants.UNIT;
        Array<Entity> neighbors = new Array<>();

        for (Entity entity: entities) {
            if (isNeighbor(y, left, right, entity)) {
                neighbors.add(entity);
            }
        }

        return neighbors;
    }

    private boolean isNeighbor(float y, float left, float right, Entity entity) {
        Rectangle bounds = this.bounds.get(entity).bounds;

        if (bounds.y != y) return false;

        return bounds.x == left || bounds.x == right;
    }


    @Override
    boolean isOnBorder(BoundsComponent component) {
        return isOnLeft(component.bounds.x) || isOnRight(component.bounds.x);
    }

    private boolean isOnLeft(float x) {
        return x == 0;
    }

    private boolean isOnRight(float x) {
        return x == (Constants.WIDTH - 1) * Constants.UNIT;
    }
}
