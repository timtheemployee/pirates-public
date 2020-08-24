package com.wxxtfxrmx.pirates.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public final class ParallaxBackground extends Actor {

    private final Texture frontClouds;
    private final Texture backClouds;
    private final Texture water;
    private int speed = 0;

    public ParallaxBackground(final Texture frontClouds,
                              final Texture backClouds,
                              final Texture water) {
        this.frontClouds = frontClouds;
        this.backClouds = backClouds;
        this.water = water;
        frontClouds.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        backClouds.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        water.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        renderAt(batch, Gdx.graphics.getHeight() - 1.6f * backClouds.getHeight(), backClouds, true, 2f);
        renderAt(batch, Gdx.graphics.getHeight() - frontClouds.getHeight(), frontClouds, false, 2f);
        renderAt(batch, Gdx.graphics.getHeight() / 2f, water, false, 1.5f);
        speed += 1;
    }

    private void renderAt(Batch batch, float y, Texture clouds, boolean reverse, float scale) {
        batch.draw(clouds,
                0,
                y,
                0,
                0,
                clouds.getWidth(),
                clouds.getHeight(),
                scale,
                scale,
                getRotation(),
                reverse ? speed : -speed,
                0,
                clouds.getWidth(),
                clouds.getHeight(),
                false,
                false
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
