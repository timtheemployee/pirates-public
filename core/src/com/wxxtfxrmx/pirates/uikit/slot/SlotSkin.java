package com.wxxtfxrmx.pirates.uikit.slot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.wxxtfxrmx.pirates.screen.levelv2.Constants;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.board.world.TileType;

public class SlotSkin extends Skin {
    private final TextureAtlas uiAtlas = new TextureAtlas(
            Gdx.files.internal("uikit/uikit.atlas")
    );

    private final TextureAtlas tilesAtlas = new TextureAtlas(
            Gdx.files.internal("sprite/tiles-sheet.atlas")
    );

    public SlotSkin() {
        addRegions(uiAtlas);
    }

    public Drawable getBackground() {
        NinePatch patch = getPatch("pane-background");
        return new NinePatchDrawable(patch);
    }

    public Drawable getIcon(TileType icon) {
        TextureAtlas.AtlasRegion atlasRegion = tilesAtlas.findRegion(icon.getAtlasPath());
        TextureRegion region = new TextureRegion(atlasRegion, 0, 0, Constants.UNIT, Constants.UNIT);
        return new TextureRegionDrawable(region);
    }
}
