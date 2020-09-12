package com.wxxtfxrmx.pirates.screen.level.battlefield;

import com.wxxtfxrmx.pirates.screen.level.board.TileType;

public final class Chain {
    private final TileType type;
    private final int tilesCount;

    public Chain(TileType type, int tilesCount) {
        this.type = type;
        this.tilesCount = tilesCount;
    }

    public TileType getType() {
        return type;
    }

    public int getTilesCount() {
        return tilesCount;
    }
}
