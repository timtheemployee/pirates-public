package com.wxxtfxrmx.pirates.system.board.pick;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;

public final class PickTileSystem {

    private final Group parent;

    public PickTileSystem(Group parent) {
        this.parent = parent;
    }

    public boolean onTouchDown(float x, float y) {
        Actor actor = parent.hit(x, y, false);

        if (actor != null && actor.isDescendantOf(parent)) {
            if (actor instanceof Tile) {
                Tile tile = (Tile) actor;

                parent.fire(new PickTile(tile));
                return true;
            }
        }

        return false;
    }

}
