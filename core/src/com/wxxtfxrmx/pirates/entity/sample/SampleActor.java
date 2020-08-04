package com.wxxtfxrmx.pirates.entity.sample;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public final class SampleActor extends Actor {
    private final Animation<TextureRegion> animation;

    public SampleActor(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(animation.getKeyFrame(0), getX(), getY());
    }
}
