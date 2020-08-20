package com.wxxtfxrmx.pirates.screen.level;

import com.wxxtfxrmx.pirates.entity.factory.TextureFactory;
import com.wxxtfxrmx.pirates.entity.factory.TileFactory;

public final class LevelScreenAssembly {

    private final TileFactory factory;
    private final TextureFactory images;

    public LevelScreenAssembly(TileFactory factory, TextureFactory images) {
        this.factory = factory;
        this.images = images;
    }

    public TileFactory tiles() {
        return factory;
    }

    public TextureFactory images() {
        return images;
    }
}
