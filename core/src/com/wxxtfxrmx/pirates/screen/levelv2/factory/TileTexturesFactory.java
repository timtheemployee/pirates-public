package com.wxxtfxrmx.pirates.screen.levelv2.factory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.world.TileType;

public class TileTexturesFactory {
    private final TextureAtlas tilesAtlas = new TextureAtlas("sprite/tiles-sheet.atlas");

    public TextureRegion getLeadingTexture(TileType type) {
        String path = type.getAtlasPath();
        TextureAtlas.AtlasRegion region = tilesAtlas.findRegion(path);

        return new TextureRegion(region, 0, 0, Constants.UNIT, Constants.UNIT);
    }

    public TextureRegion getTileBoundsTexture() {
        TextureAtlas.AtlasRegion region = tilesAtlas.findRegion("picked_border");

        return new TextureRegion(region, 0, 0, Constants.UNIT, Constants.UNIT);
    }
}
