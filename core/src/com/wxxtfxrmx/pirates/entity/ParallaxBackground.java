package com.wxxtfxrmx.pirates.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public final class ParallaxBackground extends Actor {

    private final Texture background;
    private int srcX = 1;

    public ParallaxBackground(final Texture background) {
        this.background = background;
        background.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(
                background,
                0,
                0,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                1,
                1,
                0,
                srcX,
                0,
                background.getWidth(),
                background.getHeight(),
                false,
                false
        );

        srcX += 1;
    }
}
