package com.wxxtfxrmx.pirates.screen.levelv2.factory;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

public class TileTexturesFactory {
    private final TextureAtlas tilesAtlas = new TextureAtlas("sprite/tiles-sheet.atlas");

    public TextureRegion getLeadingTexture(TileType type) {
        String path = type.getAtlasPath();
        TextureAtlas.AtlasRegion region = tilesAtlas.findRegion(path);

        return new TextureRegion(region, 0, 0, Constants.UNIT, Constants.UNIT);
    }

    public Animation<TextureRegion> getAnimation(TileType type, float frameTime) {
        String path = type.getAtlasPath();
        TextureAtlas.AtlasRegion region = tilesAtlas.findRegion(path);


        return new Animation<>(frameTime, getAnimationFrames(region));
    }

    private Array<TextureRegion> getAnimationFrames(TextureAtlas.AtlasRegion region) {
        Array<TextureRegion> frames = new Array<>();

        for (int x = 0; x < region.originalWidth; x += Constants.UNIT) {
            frames.add(new TextureRegion(region, x, 0, Constants.UNIT, Constants.UNIT));
        }

        return frames;
    }

    public TextureRegion getTileBoundsTexture() {
        TextureAtlas.AtlasRegion region = tilesAtlas.findRegion("picked_border");

        return new TextureRegion(region, 0, 0, Constants.UNIT, Constants.UNIT);
    }
}
