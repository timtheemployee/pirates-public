package com.wxxtfxrmx.pirates.entity.factory;

import com.badlogic.gdx.graphics.Texture;
import com.wxxtfxrmx.pirates.system.AssetsSystem;

public class TextureFactory {

    private final AssetsSystem graphics;

    public TextureFactory(final AssetsSystem graphics) {
        this.graphics = graphics;
    }

    public Texture getTexture(String name) {
        return graphics.getTexture(name);
    }
}
