package com.wxxtfxrmx.pirates.system.board.pick;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.wxxtfxrmx.pirates.screen.level.board.Tile;

public class PickTile extends Event {
    private final Tile tile;

    public PickTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}
