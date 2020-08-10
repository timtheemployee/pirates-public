package com.wxxtfxrmx.pirates.screen.level;

import com.wxxtfxrmx.pirates.entity.factory.TileFactory;

public final class LevelScreenAssembly {

    private final TileFactory factory;

    public LevelScreenAssembly(TileFactory factory) {
        this.factory = factory;
    }

    public TileFactory tiles() {
        return factory;
    }
}
