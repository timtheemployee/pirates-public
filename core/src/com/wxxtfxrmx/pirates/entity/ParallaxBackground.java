package com.wxxtfxrmx.pirates.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public final class ParallaxBackground extends Actor {

    private final Texture background;
    private float scaledWidth;
    private float scaledHeight;
    private int srcX = 1;

    public ParallaxBackground(final Texture background) {
        this.background = background;
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        int ratio = Gdx.graphics.getHeight() / background.getHeight();

        scaledHeight = background.getHeight() * ratio;
        scaledWidth = background.getWidth() * ratio;
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
                scaledWidth,
                scaledHeight,
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

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        int ratio = Gdx.graphics.getHeight() / background.getHeight();

        scaledHeight = background.getHeight() * ratio;
        scaledWidth = background.getWidth() * ratio;
    }
}
