package com.wxxtfxrmx.pirates.screen.levelv2.layer.battle.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShipTextureLoader {
    private final TextureAtlas shipAtlas = new TextureAtlas(Gdx.files.internal("sprite/ship_sprite.atlas"));

    public TextureRegion load(ShipTexture texture, boolean flipped) {
        TextureAtlas.AtlasRegion region = shipAtlas.findRegion(texture.getTextureName());
        region.flip(flipped, false);

        return new TextureRegion(region);
    }
}
