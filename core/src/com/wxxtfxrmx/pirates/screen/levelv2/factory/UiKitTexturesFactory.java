package com.wxxtfxrmx.pirates.screen.levelv2.factory;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.wxxtfxrmx.pirates.screen.levelv2.layer.ui.world.UiButtonType;

public final class UiKitTexturesFactory {
    private final TextureAtlas uiKitAtlas = new TextureAtlas("uikit/uikit.atlas");

    public TextureRegion getIdleTexture(UiButtonType uiButtonType) {
        TextureAtlas.AtlasRegion region = uiKitAtlas.findRegion(uiButtonType.getIdle());

        return new TextureRegion(region, 0, 0, region.packedWidth, region.packedHeight);
    }

    public TextureRegion getPickedTexture(UiButtonType uiButtonType) {
        TextureAtlas.AtlasRegion region = uiKitAtlas.findRegion(uiButtonType.getPicked());

        return new TextureRegion(region, 0, 0, region.packedWidth, region.packedHeight);
    }

    public TextureRegion getPaneTexture() {
        TextureAtlas.AtlasRegion region = uiKitAtlas.findRegion("pane-background");

        return new TextureRegion(region, 0, 0, region.packedWidth, region.packedHeight);
    }
}
